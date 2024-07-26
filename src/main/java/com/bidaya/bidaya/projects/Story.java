package com.bidaya.bidaya.projects;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileUrl;
    private String videoUrl;
    @Lob
    private String editorContent;
    private byte[] imageData;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Questions> questions = new ArrayList<>();

}
