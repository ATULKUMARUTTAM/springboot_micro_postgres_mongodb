package com.atuluttam.user.controller;
import com.atuluttam.user.dto.UserRequest;
import com.atuluttam.user.dto.UserResponse;
import com.atuluttam.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAlluser()
    {
        return ResponseEntity.ok(userService.fetchAlluser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id) {
        Optional<UserResponse> userResponse = userService.fetchOneUser(id); // Change to String
        if (userResponse.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userResponse.get());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest)
    {
        userService.addUser(userRequest);
        return ResponseEntity.ok("User Added Succesfully!!!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UserRequest updatedUserRequest) {
        Boolean updated = userService.updateUser(id, updatedUserRequest); // Change to String
        if (updated)
            return ResponseEntity.ok("User Updated Successfully!!");
        else {
            return ResponseEntity.notFound().build();
        }
    }

}
