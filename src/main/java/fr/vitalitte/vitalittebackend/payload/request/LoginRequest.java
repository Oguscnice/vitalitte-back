package fr.vitalitte.vitalittebackend.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {
    @NotBlank
    @Size(max = 255)
    private String email;
    @NotBlank
    @Size(max = 255)
    private String password;
    public LoginRequest() {
    }
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
