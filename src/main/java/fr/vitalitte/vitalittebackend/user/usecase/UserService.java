package fr.vitalitte.vitalittebackend.user.usecase;

import fr.vitalitte.vitalittebackend.user.models.User;
import fr.vitalitte.vitalittebackend.user.rest.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User createUser(User article);
    List<UserDto> findAll();
    UserDto findByEmail(String email);
    void updateRoles(UserDto userDto);
    void update(UserDto userDto);
    void deleteByEmail(String email);
}
