package com.bidaya.bidaya.projects;

import com.bidaya.bidaya.users.User;
import jakarta.persistence.*;

@Entity
@Table( name = "bidaya_projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String fundingGoal;
    private int durationDays;

    public Project() {

    }

    public  Project(Long id,String title, String description, String fundingGoal, int durationDays, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.fundingGoal = fundingGoal;
        this.durationDays = durationDays;
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFundingGoal() {
        return fundingGoal;
    }

    public void setFundingGoal(String fundingGoal) {
        this.fundingGoal = fundingGoal;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }
}
