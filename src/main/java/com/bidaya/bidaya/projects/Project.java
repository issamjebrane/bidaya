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
@ToString
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String fundingGoal;
    private int durationDays;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
