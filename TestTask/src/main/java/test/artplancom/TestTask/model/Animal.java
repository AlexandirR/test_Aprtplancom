package test.artplancom.TestTask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import test.artplancom.TestTask.model.enums.Gender;
import test.artplancom.TestTask.model.enums.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "animals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;
    
    @Column(name = "user_id")
    private Long userId;
    
    public Animal(String name, Gender gender, LocalDate birthday, Type type, Long userId) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.type = type;
        this.userId = userId;
    }
}
