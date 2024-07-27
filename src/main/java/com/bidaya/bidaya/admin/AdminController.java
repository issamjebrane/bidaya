package com.bidaya.bidaya.admin;


import com.bidaya.bidaya.dto.ProjectDto;
import com.bidaya.bidaya.dto.UserDto;
import com.bidaya.bidaya.users.Role;
import com.bidaya.bidaya.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    //change roles of users
    @PostMapping("/changeRoles/{id}")
    public ResponseEntity<Map<String, Role>> changeRoles(@PathVariable Long id, @RequestParam String newRole){
        //change roles of users
        Role role;
        try {
            role = Role.valueOf(newRole);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role value");
        }
        User user =  adminService.changeRoles(id,role);
        Map<String,Role> response = new HashMap<>();
        response.put("role",user.getRole());
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        boolean isDeleted = adminService.deleteUserById(id);
        if (isDeleted) {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        User updatedUser = adminService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDto>> getProjectsPagination(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
       List<ProjectDto> projects= adminService.findProjects(page, size);
       if (projects.isEmpty()) {
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
         return  ResponseEntity.ok(projects);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        List<User> users = adminService.getUsersPagination(page,size);

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        UserDto user = adminService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }
}
