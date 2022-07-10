package test.artplancom.TestTask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    @Column(name = "account_non_locked", columnDefinition = "boolean default true")
    private boolean accountNonLocked;
    
    @Column(name = "failed_attempt", columnDefinition = "int default 0")
    private int failedAttempt;
    
    @Column(name = "lock_time", columnDefinition = "date default null")
    private LocalDateTime lockTime;
    @OneToMany()
    @JoinColumn(name = "user_id")
    private List<Animal> animals;

    public User(String username, String password, List<Animal> animals) {
        this.username = username;
        this.password = password;
        this.animals = animals;
    }
}
