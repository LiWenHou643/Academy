package com.liwenhou.Academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Chapters extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native")
    private int ChapterId;

    @Min(value = 1, message = "Value must be greater than 0")
    private int no;

    @NotBlank(message = "This field is required!")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "course_id", referencedColumnName = "courseId", nullable = false)
    private Courses courses;

    @OneToMany(mappedBy = "chapters", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, targetEntity = Lessons.class)
    private Set<Lessons> lessons = new HashSet<>();
}
