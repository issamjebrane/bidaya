package com.bidaya.bidaya.dto;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Basics {
    private Long id;
    private String title;
    private String subtitle;
    private int goal;
    private int duration;
    private String cardImage;
    private byte[] imageData;
    private String location;
    private String subCategory;
    private String category;
}
