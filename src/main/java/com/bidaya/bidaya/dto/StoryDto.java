package com.bidaya.bidaya.dto;

import com.bidaya.bidaya.projects.Questions;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryDto {
    private String fileUrl;
    private String videoUrl;
    @Lob
    private String editorContent;
    private List<Questions> questions;
}
