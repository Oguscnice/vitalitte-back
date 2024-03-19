package fr.vitalitte.vitalittebackend.user.usecase;


import fr.vitalitte.vitalittebackend.user.exception.UserNotFoundException;
import fr.vitalitte.vitalittebackend.user.models.User;
import fr.vitalitte.vitalittebackend.user.persistence.UserRepository;
import fr.vitalitte.vitalittebackend.user.rest.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    TransformUser transformUser;

    public UserServiceImpl(UserRepository userRepository, TransformUser transformUser) {
        this.userRepository = userRepository;
        this.transformUser = transformUser;
    }

    @Override
    public User createUser(User user) {
        User newUser = User.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
        return this.userRepository.save(newUser);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = this.userRepository.findAll();
        return this.transformUser.usersToUsersDto(users);
    }

    @Override
    public UserDto findByEmail(String email) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return this.transformUser.userToUserDTO(user);
    }

    @Override
    public void updateRoles(UserDto userDto) {
        User userToUpdate = this.userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        userToUpdate.setRoles(userDto.getRoles());

        this.userRepository.save(userToUpdate);
    }

    @Override
    public void update(UserDto userDto) {
        User userToUpdate = this.userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setLastname(userDto.getLastname());
        userToUpdate.setFirstname(userDto.getFirstname());

        this.userRepository.save(userToUpdate);
    }

    @Override
    public void deleteByEmail(String email) {
        this.userRepository.deleteByEmail(email);
    }
}
