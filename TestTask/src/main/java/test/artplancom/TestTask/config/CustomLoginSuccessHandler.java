package test.artplancom.TestTask.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import test.artplancom.TestTask.model.User;
import test.artplancom.TestTask.service.UserService;
import test.artplancom.TestTask.service.security.UserDetailsImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    
    @Autowired
    private UserService userService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDetailsImpl userDetails =  (UserDetailsImpl) authentication.getPrincipal();
        if (userDetails.getFailedAttempt() > 0) {
            userService.resetFailedAttempts(userDetails.getUsername());
        }
        
        super.onAuthenticationSuccess(request, response, authentication);
    }
    
}
