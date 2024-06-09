package com.bidaya.bidaya.projects;

import com.bidaya.bidaya.comments.Comments;
import com.bidaya.bidaya.rewards.Rewards;
import com.bidaya.bidaya.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
// todo 1.1: Create a Project entity with the following fields:
//  editor: to handle the story editor data.
@Entity
@Table( name = "bidaya_projects")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String fundingGoal;
    private int durationDays;

    @Enumerated(EnumType.STRING)
    private ProjectType projectType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rewards> rewards = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comments> comments = new ArrayList<>();
}
