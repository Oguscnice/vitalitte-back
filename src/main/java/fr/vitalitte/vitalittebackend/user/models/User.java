package fr.vitalitte.vitalittebackend.user.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.vitalitte.vitalittebackend.role.models.ERole;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class User {
    @Id
    private UUID id;
    @NotNull
    @Size(max = 255)
    private String firstname;
    @NotNull
    @Size(max = 255)
    private String lastname;
    @NotBlank
    @Size(max = 255)
    private String email;
    @NotBlank
    @JsonIgnore
    @Size(max = 255)
    private String password;
    @NotNull
    private Set<ERole> roles;

    public User() {
    }

    public User(UUID id, String firstname, String lastname, String email, String password, Set<ERole> roles) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public Set<ERole> getRoles() {
        return roles;
    }

    public void setRoles(Set<ERole> roles) {
        this.roles = roles;
    }
    public static UserBuilder builder(){return new UserBuilder();}
    public static class UserBuilder{
        private final UUID id = UUID.randomUUID();
        private String firstname;
        private String lastname;
        private String email;
        private String password;
        private Set<ERole> roles;
        public UserBuilder firstname(String firstname){
            this.firstname = firstname;
            return this;
        }
        public UserBuilder lastname(String lastname){
            this.lastname = lastname;
            return this;
        }
        public UserBuilder email(String email){
            this.email = email;
            return this;
        }
        public UserBuilder password(String password){
            this.password = password;
            return this;
        }
        public UserBuilder roles(Set<ERole> roles){
            this.roles = roles;
            return this;
        }
        public User build(){return new User(this.id, this.firstname, this.lastname, this.email, this.password, this.roles);}
    }
}

