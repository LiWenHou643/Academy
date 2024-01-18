package com.liwenhou.Academy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Lessons extends BaseEntity{
    @Id
    private int lessonId;

    private String name;

    private String video;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "course_id", referencedColumnName = "courseId", nullable = false)
    private Courses courses;
}
