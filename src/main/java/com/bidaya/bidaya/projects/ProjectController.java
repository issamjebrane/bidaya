package com.bidaya.bidaya.projects;

import com.bidaya.bidaya.dto.ProjectDto;
import com.bidaya.bidaya.projects.projectRepositories.ProjectRepository;
import com.bidaya.bidaya.projects.projectRepositories.QuestionsRepository;
import com.bidaya.bidaya.projects.projectRepositories.RewardsRepository;
import com.bidaya.bidaya.projects.projectRepositories.StoryRepository;
import com.bidaya.bidaya.users.User;
import com.bidaya.bidaya.users.UserRepository;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectService projectService;
    private final StoryRepository storyRepository;
    private final QuestionsRepository questionsRepository;
    private final RewardsRepository rewardsRepository;
    private final Path uploadDirectory = Paths.get("uploads");


    @GetMapping("")
    public ResponseEntity<List<ProjectDto>> getProjects(){

        return ResponseEntity.ok(this.projectService.getProjects());
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<Object> getProject(@PathVariable Long id) {
        try {
            ProjectDto projectDto = projectService.getProject(id);

            return ResponseEntity.ok(projectDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


    @PostMapping("/add")
    public ResponseEntity<?> addProject(@RequestBody ProjectDto data) throws IOException {
        Optional<User> user = userRepository.findByEmail(data.getUserId().getEmail());

        if (user.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found");
            return ResponseEntity.badRequest().body(response);
        }

        this.projectService.createProject(data,user.get());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Project created successfully");
        return ResponseEntity.ok(response);
    }

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

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path file = uploadDirectory.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

}
