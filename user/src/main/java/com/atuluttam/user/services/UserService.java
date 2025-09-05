package com.atuluttam.user.services;


import com.atuluttam.user.model.Address;
import com.atuluttam.user.dto.AddressDTO;
import com.atuluttam.user.dto.UserRequest;
import com.atuluttam.user.dto.UserResponse;
import com.atuluttam.user.model.User;
import com.atuluttam.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    public List<UserResponse> fetchAlluser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserResponse).collect(Collectors.toList());
    }
    public void addUser(UserRequest userRequest) {
        User user = new User();
        user.setFname(userRequest.getFname());
        user.setLname(userRequest.getLname());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setAddress(userRequest.getAddress());
        userRepository.save(user);
    }

    public Boolean updateUser(String id, UserRequest updatedUserRequest) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return false;
        }
        User user = userOptional.get();
        user.setFname(updatedUserRequest.getFname());
        user.setLname(updatedUserRequest.getLname());
        user.setEmail(updatedUserRequest.getEmail());
        user.setPhone(updatedUserRequest.getPhone());
        user.setAddress(updatedUserRequest.getAddress());
        userRepository.save(user);
        return true;
    }

    public Optional<UserResponse> fetchOneUser(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::mapToUserResponse);
    }
    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFname(user.getFname());
        response.setLname(user.getLname());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        AddressDTO addressDTO = new AddressDTO();
        if (user.getAddress() != null) {
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setPincode(user.getAddress().getPincode());
        }
        response.setAddressDTO(addressDTO);
        return response;
    }

}
