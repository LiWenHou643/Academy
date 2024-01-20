package com.liwenhou.Academy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Courses extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native")
    private int courseId;

    @NotBlank(message = "This field is required!")
    private String name;

    @NotBlank(message = "This field is required!")
    private String fees;

    @NotBlank(message = "This field is required!")
    private String instructor;

    @NotBlank(message = "This field is required!")
    private String description;

    @NotBlank(message = "This field is required!")
    private String image;

    @NotBlank(message = "This field is required!")
    private float rating;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Person> persons = new HashSet<>();

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Person> personsCart = new HashSet<>();

    @OneToMany(mappedBy = "courses", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, targetEntity = Chapters.class)
    private Set<Lessons> chapters = new HashSet<>();
}
