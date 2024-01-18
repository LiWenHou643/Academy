package com.liwenhou.Academy.model;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int courseId;

    private String name;

    private String fees;

    private String instructor;

    private String description;

    private String image;

    private float rating;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Person> persons = new HashSet<>();

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Person> personsCart = new HashSet<>();

    @OneToMany(mappedBy = "courses", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, targetEntity = Lessons.class)
    private Set<Lessons> lessons = new HashSet<>();
}
