package com.bidaya.bidaya.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RewardsDto {
    private String title;
    private String contributionLevel;
    private String description;
    private String estimatedDeliveryDate;
    private String fileUrl;
    private byte[] imageData;
}
