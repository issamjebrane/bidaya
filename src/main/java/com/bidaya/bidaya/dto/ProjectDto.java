package com.bidaya.bidaya.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {
    private StoryDto story;
    private List<RewardsDto> rewards;
    private Basics basics;
    private UserDto userId;
    private LocalDate creationDate;
}
