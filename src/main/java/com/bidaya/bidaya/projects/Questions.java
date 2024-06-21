package com.bidaya.bidaya.projects;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class Questions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    @Column(columnDefinition = "TEXT")
    private String answer;

    //todo 1.3: Add a Story field to the Questions entity.
    //link the questions to the story using a @ManyToOne relationship, with the story id?
    @ManyToOne
    @JoinColumn(name = "story_id")
    private Story story;

}
