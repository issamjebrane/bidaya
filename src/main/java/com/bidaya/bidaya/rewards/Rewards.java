package com.bidaya.bidaya.rewards;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

}
