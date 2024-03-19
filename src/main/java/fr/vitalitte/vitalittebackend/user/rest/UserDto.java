package fr.vitalitte.vitalittebackend.user.rest;

import fr.vitalitte.vitalittebackend.role.models.ERole;

import java.util.Set;

public class UserDto {

    private String firstname;
    private String lastname;
    private String email;
    private Set<ERole> roles;
    public UserDto(){}

    public UserDto(String firstname, String lastname, String email, Set<ERole> roles) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.roles = roles;
    }

    public String getFirstname() {return this.firstname;}
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

    public Set<ERole> getRoles() {
        return roles;
    }

    public void setRoles(Set<ERole> roles) {
        this.roles = roles;
    }

    public static UserDtoBuilder builder(){return new UserDtoBuilder();}
    public static class UserDtoBuilder{
        private String firstname;
        private String lastname;
        private String email;
        private Set<ERole> roles;
        public UserDtoBuilder firstname(String firstname){
            this.firstname = firstname;
            return this;
        }
        public UserDtoBuilder lastname(String lastname){
            this.lastname = lastname;
            return this;
        }
        public UserDtoBuilder email(String email){
            this.email = email;
            return this;
        }
        public UserDtoBuilder roles(Set<ERole> roles){
            this.roles = roles;
            return this;
        }
        public UserDto build(){return new UserDto(this.firstname, this.lastname, this.email, this.roles);}
    }
}
