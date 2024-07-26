package com.bidaya.bidaya.admin;


import com.bidaya.bidaya.users.Role;
import com.bidaya.bidaya.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

}
