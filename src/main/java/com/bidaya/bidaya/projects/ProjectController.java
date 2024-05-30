package com.bidaya.bidaya.projects;

import com.bidaya.bidaya.users.User;
import com.bidaya.bidaya.users.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    ProjectController(ProjectRepository projectRepository,UserRepository userRepository){
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public List<Project> getProjects(){
        return this.projectRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Project> getProject(@PathVariable Long id ){
        return this.projectRepository.findById(id);
    }

    @PostMapping("/add")
    public Project addProject(@RequestBody Project project, @RequestParam Long id){
        User user = this.userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return this.projectRepository.save(project);
    }
}
