package test.artplancom.TestTask.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import test.artplancom.TestTask.model.enums.ERole;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

}
