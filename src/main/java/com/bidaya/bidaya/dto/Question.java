package com.bidaya.bidaya.dto;

import com.bidaya.bidaya.projects.Story;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    private String question;
    private String answer;
    private Story story;
}
