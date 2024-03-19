package fr.vitalitte.vitalittebackend.user.usecase;

import fr.vitalitte.vitalittebackend.user.exception.UserNotFoundException;
import fr.vitalitte.vitalittebackend.user.models.User;
import fr.vitalitte.vitalittebackend.user.persistence.UserRepository;
import fr.vitalitte.vitalittebackend.user.rest.UserDto;
import org.springframework.stereotype.Service;
import static fr.vitalitte.vitalittebackend.common.utils.ListMapperUtil.mapList;


import java.util.List;

@Service
public class TransformUser {

    UserRepository userRepository;

    public UserDto userToUserDTO(User user){

        return UserDto.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }

    public List<UserDto> usersToUsersDto(List<User> users){
        return mapList(this::userToUserDTO, users);
    }

    public User userDtoToUser(UserDto userDto){
        return this.userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(UserNotFoundException::new);
    }

    public List<User> usersDtoToUsers(List<UserDto> usersDto){
        return mapList(this::userDtoToUser, usersDto);
    }
}
