package com.bidaya.bidaya.projects;

import com.bidaya.bidaya.dto.Basics;
import com.bidaya.bidaya.dto.ProjectDto;
import com.bidaya.bidaya.dto.Question;
import com.bidaya.bidaya.dto.StoryDto;
import com.bidaya.bidaya.projects.projectRepositories.ProjectRepository;
import com.bidaya.bidaya.projects.projectRepositories.QuestionsRepository;
import com.bidaya.bidaya.projects.projectRepositories.RewardsRepository;
import com.bidaya.bidaya.projects.projectRepositories.StoryRepository;
import com.bidaya.bidaya.users.User;
import com.bidaya.bidaya.users.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        List<Questions> questions = projectData.getStory().getQuestions();
        if (questions != null) {
            questions.forEach(questionDto -> {
                Questions question = Questions.builder()
                        .question(questionDto.getQuestion())
                        .answer(questionDto.getAnswer())
                        .story(story)
                        .build();
                questionsRepository.save(question);
            });
        }

        // Create new rewards
        List<Rewards> rewards = projectData.getRewards();
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
    public StoryDto getProject(Long id) {
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
        List<Optional<Rewards>> rewards = this.rewardsRepository.findByProjectId(project.getId());
        List<Rewards> rewardsList = rewards.stream().map(Optional::get).toList();
        List<Optional<Questions>> questions = this.questionsRepository.findByStoryId(story.getId());
        List<Questions> questionsList = questions.stream().map(Optional::get).toList();
        StoryDto storyDto = StoryDto.builder()
                .fileUrl(story.getFileUrl())
                .videoUrl(story.getVideoUrl())
                .editorContent(story.getEditorContent())
                .questions(questionsList)
                .build();
        ProjectDto projectDto = ProjectDto.builder()
                .story(storyDto)
                .rewards(rewardsList)
                .basics(basics)
                .userId(project.getUser().getEmail())
                .build();

        return storyDto;
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }


}
