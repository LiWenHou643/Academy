package com.liwenhou.Academy.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity
public class Lessons extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native")
    private int id;

    @Min(value = 1, message = "Value must be greater than 0")
    private int no;

    @NotBlank(message = "This field is required!")
    private String name;

    @NotBlank(message = "This field is required!")
    private String video;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "chapter_id", referencedColumnName = "chapterId", nullable = false)
    private Chapters chapters;
}
