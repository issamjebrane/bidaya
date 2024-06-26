package com.bidaya.bidaya.projects;

import com.bidaya.bidaya.dto.*;
import com.bidaya.bidaya.projects.projectRepositories.ProjectRepository;
import com.bidaya.bidaya.projects.projectRepositories.QuestionsRepository;
import com.bidaya.bidaya.projects.projectRepositories.RewardsRepository;
import com.bidaya.bidaya.projects.projectRepositories.StoryRepository;
import com.bidaya.bidaya.users.User;
import com.bidaya.bidaya.users.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final StoryRepository storyRepository;
    private final QuestionsRepository questionsRepository;
    private final RewardsRepository rewardsRepository;

    @Transactional
    public Project createProject(ProjectDto projectData, User user) {

        // Validate project data
        if (projectData == null || projectData.getBasics() == null) {
            throw new IllegalArgumentException("Project data is missing.");
        }

        // Create a new project
        Project project = Project.builder()
                .title(projectData.getBasics().getTitle())
                .subtitle(projectData.getBasics().getSubtitle())
                .goal(projectData.getBasics().getGoal())
                .duration(projectData.getBasics().getDuration())
                .cardImage(projectData.getBasics().getCardImage())
                .location(projectData.getBasics().getLocation())
                .subCategory(projectData.getBasics().getSubCategory())
                .category(Category.valueOf(projectData.getBasics().getCategory())) // Ensure enum value is valid
                .user(user)
                .creationDate(LocalDate.now())
                .build();

        project = projectRepository.save(project);

        // Create a new story
        Story story = Story.builder()
                .editorContent(projectData.getStory().getEditorContent())
                .fileUrl(projectData.getStory().getFileUrl())
                .videoUrl(projectData.getStory().getVideoUrl())
                .project(project)
                .build();

        storyRepository.save(story);

        // Create new questions
        List<Question> question = projectData.getStory().getQuestion();
        if (question != null) {
            question.forEach(questionDto -> {
                Questions questions = Questions.builder()
                        .question(questionDto.getQuestion())
                        .answer(questionDto.getAnswer())
                        .story(story)
                        .build();
                questionsRepository.save(questions);
            });
        }

        // Create new rewards
        List<Rewards> rewards = project.getRewards();
        Project finalProject = project;
        if (rewards != null) {
            rewards.forEach(rewardDto -> {
                Rewards reward = Rewards.builder()
                        .title(rewardDto.getTitle())
                        .description(rewardDto.getDescription())
                        .contributionLevel(rewardDto.getContributionLevel())
                        .estimatedDeliveryDate(rewardDto.getEstimatedDeliveryDate())
                        .fileUrl(rewardDto.getFileUrl())
                        .project(finalProject)
                        .build();
                rewardsRepository.save(reward);
            });
        }

        return project;
    }

    @Transactional
    public ProjectDto getProject(Long id) {
        Project project =  this.projectRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Project not found")
        );
        Basics basics = Basics.builder()
                .title(project.getTitle())
                .subtitle(project.getSubtitle())
                .goal(project.getGoal())
                .duration(project.getDuration())
                .cardImage(project.getCardImage())
                .location(project.getLocation())
                .subCategory(project.getSubCategory())
                .category(project.getCategory().name())
                .build();
        Story story = this.storyRepository.findByProjectId(project.getId()).orElseThrow(
                () -> new IllegalArgumentException("Story not found")
        );
        List<Rewards> rewards = this.rewardsRepository.findByProjectId(project.getId());

        if (rewards == null) {
            // Initialize the list to an empty list if the repository returns null
            rewards = new ArrayList<>();
        }
        List<RewardsDto> rewardsDtos = rewards.stream()
                .map(reward -> RewardsDto.builder()
                        .fileUrl(reward.getFileUrl())
                        .estimatedDeliveryDate(reward.getEstimatedDeliveryDate())
                        .contributionLevel(reward.getContributionLevel())
                        .description(reward.getDescription())
                        .title(reward.getTitle())
                        .build()
                ).toList();
        List<Questions> questions = this.questionsRepository.findByStoryId(story.getId());
        if (questions == null || questions.isEmpty()) {
            // Handle the case where no questions are found
            // For example, you can initialize the list to an empty list
            questions = new ArrayList<>();
        }
        List<Question> questionDtos = questions.stream()
                .map(question -> Question.builder()
                        .question(question.getQuestion())
                        .answer(question.getAnswer())
                        .build())
                .toList();

        StoryDto storyDto = StoryDto.builder()
                .fileUrl(story.getFileUrl())
                .videoUrl(story.getVideoUrl())
                .editorContent(story.getEditorContent())
                .question(questionDtos)
                .build();
        UserDto userDto = UserDto.builder()
                .email(project.getUser().getEmail())
                .firstName(project.getUser().getFirstName())
                .lastName(project.getUser().getLastName())
                .build();

        return ProjectDto.builder()
                .story(storyDto)
                .rewards(rewardsDtos)
                .basics(basics)
                .userId(userDto.getEmail())
                .creationDate(project.getCreationDate())
                .build();
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }


}
