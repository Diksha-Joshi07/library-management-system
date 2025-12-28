package com.diksha.library.service;


import com.diksha.library.dto.request.UserRequestDTO;
import com.diksha.library.dto.response.UserResponseDTO;
import com.diksha.library.entity.User;
import com.diksha.library.exception.ResourceNotFoundException;
import com.diksha.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public UserResponseDTO createUser(UserRequestDTO dto) {
        // Optional: check if email exists
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPassword(dto.getPassword());
        user.setUserRole(dto.getUserRole());
        userRepository.save(user);
        return mapToDTO(user);
    }

    public UserResponseDTO getUserByName(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToDTO(user);
    }

    public List<UserResponseDTO> searchUsers(String name) {
        return userRepository.findByNameContainingIgnoreCase(name)
                .stream().map(this::mapToDTO).toList();
    }

    // Helper: Map User -> UserResponseDTO
    private UserResponseDTO mapToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        user.setUserRole(dto.getUserRole());
        return dto;
    }
}