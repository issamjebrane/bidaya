package com.bidaya.bidaya.projects;

import com.bidaya.bidaya.dto.Basics;
import com.bidaya.bidaya.dto.ProjectDto;
import com.bidaya.bidaya.projects.projectRepositories.*;
import com.bidaya.bidaya.users.User;
import com.bidaya.bidaya.users.UserRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
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
    private final ProjectService projectService;
    private final StoryRepository storyRepository;
    private final QuestionsRepository questionsRepository;
    private final RewardsRepository rewardsRepository;
    private final Path uploadDirectory = Paths.get("uploads");
    private final ImageRepository imageRepository;

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

        Project project = this.projectService.createProject(data,user.get());
        ProjectDto projectDto = projectService.getProject(project.getId());

        return ResponseEntity.ok(projectDto);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        //check if the directory exists
        if (!Files.exists(uploadDirectory)) {
            Files.createDirectories(uploadDirectory);
        }
        String fileNameMilli = System.currentTimeMillis() + file.getOriginalFilename();
        Path filePath = uploadDirectory.resolve(fileNameMilli);
        Files.copy(file.getInputStream(), filePath);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Image uploaded successfully");
        response.put("filename", fileNameMilli);
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

    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImages(@RequestParam("file") String file) throws IOException {

        Path filePath = uploadDirectory.resolve(file);

        Imagedata imagedata = Imagedata.builder()
                .imageName(file)
                .imageData(projectService.convertFileUrlToByte(file))
//                .imageType(file.getContentType())
                .build();
        imageRepository.save(imagedata);
        Map<String,String> response = new HashMap<>();
        response.put("message","Image uploaded successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getImage/{id}")
    public ResponseEntity<?> getImage(@PathVariable Long id){
        Project project = projectRepository.findById(id).orElseThrow();
        Basics basics = Basics.builder()
                .id(project.getId())
                .imageData(project.getImageData())
                .cardImage(project.getCardImage())
                .title(project.getTitle())
                .build();
        Map<String, Object> response = new HashMap<>();
        return ResponseEntity.ok()
                .body(basics);
    }

    //get projects depending on the category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProjectDto>> getProjectsByCategory(@PathVariable String category){
        List<ProjectDto> projects = this.projectService.getProjectsByCategory(category);
        if(projects.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.projectService.getProjectsByCategory(category));
    }

    //sort projects by crieteria
    @GetMapping("/sort/{criteria}")
    public ResponseEntity<List<ProjectDto>> sortProjects(@PathVariable String criteria){
        List<ProjectDto> projects = this.projectService.sortProjects(criteria);
        if(projects.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.projectService.sortProjects(criteria));
    }

//    search query
    @GetMapping("/search")
    public ResponseEntity<List<ProjectDto>> searchProjects(@RequestParam String query){
        List<ProjectDto> projects = this.projectService.searchProjects(query);
        if(projects.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.projectService.searchProjects(query));
    }
}
