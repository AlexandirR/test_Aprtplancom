package test.artplancom.TestTask.model.payload;

import lombok.Data;

@Data
public class AuthRequest {

    private String username;
    private String password;
}
