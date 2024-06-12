package com.bidaya.bidaya.projects;

import com.bidaya.bidaya.users.User;
import com.bidaya.bidaya.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

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
    // upload image to backend using http://localhost:8080/api/v1/projects/upload endpoint
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String directory = "uploads";
        Path dirPath = Paths.get(directory);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = dirPath.resolve(filename);
        file.transferTo(filePath);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Image uploaded successfully");
        response.put("filename", filename);
        return ResponseEntity.ok(response);

    }
}
