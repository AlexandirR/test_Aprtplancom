package test.artplancom.TestTask.model.payload;

import lombok.Data;
import test.artplancom.TestTask.model.enums.Gender;
import test.artplancom.TestTask.model.enums.Type;

import java.time.LocalDate;

@Data
public class NewAnimal {
    
    private String name;
    
    private Gender gender;
    
    private LocalDate birthday;
    
    private Type type;
    
}
