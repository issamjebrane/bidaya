package com.bidaya.bidaya.projects;

import com.bidaya.bidaya.users.User;
import jakarta.persistence.*;
import lombok.*;

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
}
