package test.artplancom.TestTask.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthResponse {

    private String token;
    private String type = "Bearer";

    public AuthResponse(String token) {
        this.token = token;
    }
}
