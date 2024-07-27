package com.bidaya.bidaya.admin;

import com.bidaya.bidaya.dto.Basics;
import com.bidaya.bidaya.dto.ProjectDto;
import com.bidaya.bidaya.dto.UserDto;
import com.bidaya.bidaya.projects.Project;
import com.bidaya.bidaya.projects.ProjectService;
import com.bidaya.bidaya.projects.projectRepositories.ProjectRepository;
import com.bidaya.bidaya.users.Role;
import com.bidaya.bidaya.users.User;
import com.bidaya.bidaya.users.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    @Transactional
    public User changeRoles(Long id, Role role) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(role);
       return userRepository.save(user);
    }

    @Transactional
    public boolean deleteUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return true;
        } else {
            return false;
        }
    }
    @Transactional
    public User updateUser(Long id, User user) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User updatedUser = userOptional.get();
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setRole(user.getRole());
            return userRepository.save(updatedUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }
    @Transactional
    public List<ProjectDto> findProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projects =  projectRepository.findAll(pageable);
        return projects.stream()
                .map(project -> projectService.getProject(project.getId())).toList();

    }

    public List<User> getUsersPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);
        return users.stream().map(
                user -> User.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .build())
                .toList();
    }

    public UserDto getUserById(Long id) {
        Optional<User> user= userRepository.findById(id);
        return user.map(value -> UserDto.builder()
                .id(value.getId())
                .firstName(value.getFirstName())
                .lastName(value.getLastName())
                .email(value.getEmail())
                .role(value.getRole())
                .build()).orElse(null);
    }
}


