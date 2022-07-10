package test.artplancom.TestTask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.artplancom.TestTask.model.User;
import test.artplancom.TestTask.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class UserService {
    
    public static final int MAX_FAILED_ATTEMPTS = 10;
    
    private static final int DAYS_LOCKED = 1;
    
    @Autowired
    private UserRepository userRepository;
    
    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        userRepository.updateFailedAttempts(newFailAttempts, user.getUsername());
    }
    
    public void resetFailedAttempts(String username) {
        userRepository.updateFailedAttempts(0, username);
    }
    
    public void lock(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(LocalDateTime.now());
        
        userRepository.save(user);
    }
    
    public boolean unlockWhenTimeExpired(User user) {
        if (user.getLockTime().plusDays(DAYS_LOCKED).isAfter(LocalDateTime.now())) {
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            user.setFailedAttempt(0);
            
            userRepository.save(user);
            
            return true;
        }
        
        return false;
    }
}
