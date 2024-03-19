package fr.vitalitte.vitalittebackend.payload.request;

import com.sun.istack.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class SignupRequest {
    @NotNull
    @Size(min = 1, max = 255)
    private final String firstname;
    @NotNull
    @Size(min = 1, max = 255)
    private final String lastname;
    @NotNull
    @Size(max = 255)
    @Email
    private final String email;
    @NotNull
    @Size(min = 8, max = 255)
    private final String password;

    public String getFirstname() {
        return this.firstname;
    }
    public String getLastname() {
        return this.lastname;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }

    public SignupRequest(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
}
