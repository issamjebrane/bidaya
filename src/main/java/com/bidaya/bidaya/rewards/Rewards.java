package com.bidaya.bidaya.rewards;

import com.bidaya.bidaya.projects.Project;
import com.bidaya.bidaya.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity(name = "bidaya_rewards")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Rewards {
    @Id
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
