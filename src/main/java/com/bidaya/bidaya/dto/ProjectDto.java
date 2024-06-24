package com.bidaya.bidaya.dto;

import com.bidaya.bidaya.projects.Rewards;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {
    private StoryDto story;
    private List<Rewards> rewards;
    private Basics basics;
    private String userId;
}
