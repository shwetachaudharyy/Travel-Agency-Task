package com.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.app.dto.UserDTO;
import com.app.entities.User;
import com.app.repository.UserRepository;
import com.app.service.Impl.UserServiceImpl;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
public class TestUserService {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Test
    public void testCreateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");

        when(userRepository.save(user)).thenReturn(user);
        UserDTO createdUser = userService.createUser(userDTO);

        assertNotNull(createdUser);
        assertEquals(userDTO.getEmail(), createdUser.getEmail());
        assertEquals(userDTO.getFirstName(), createdUser.getFirstName());
        assertEquals(userDTO.getLastName(), createdUser.getLastName());
    }

    @Test
    public void testUpdateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");

        User user = new User();
        user.setUserId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO updatedUserDTO = userService.updateUser(userDTO, 1L);

        assertNotNull(updatedUserDTO);
        assertEquals(userDTO.getEmail(), updatedUserDTO.getEmail());
        assertEquals(userDTO.getPassword(), updatedUserDTO.getPassword());
        assertEquals(userDTO.getFirstName(), updatedUserDTO.getFirstName());
        assertEquals(userDTO.getLastName(), updatedUserDTO.getLastName());
    }

    @Test
    public void testGetUserByEmailAndPassword() {
        User user = new User();
        user.setUserId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        when(userRepository.findByEmailAndPassword("test@example.com", "password")).thenReturn(Optional.of(user));

        UserDTO resultUserDTO = userService.getUserByEmailAndPassword("test@example.com", "password");
        assertEquals(userDTO.getUserId(), resultUserDTO.getUserId());
        assertEquals(userDTO.getEmail(), resultUserDTO.getEmail());
        assertEquals(userDTO.getPassword(), resultUserDTO.getPassword());
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setUserId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO resultUserDTO = userService.getUserById(1L);

        assertEquals(userDTO.getUserId(), resultUserDTO.getUserId());
        assertEquals(userDTO.getEmail(), resultUserDTO.getEmail());
        assertEquals(userDTO.getPassword(), resultUserDTO.getPassword());
    }

    @Test
    public void testDeleteUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        userService.deleteUserById(1L);
        verify(userRepository).deleteById(1L);
    }

}
