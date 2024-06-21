package com.bidaya.bidaya.projects;

import com.bidaya.bidaya.users.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity(name = "bidaya_rewards")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Rewards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String contributionLevel;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String estimatedDeliveryDate;
    private String fileUrl;


    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
