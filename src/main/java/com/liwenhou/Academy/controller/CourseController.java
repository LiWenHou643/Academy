package com.liwenhou.Academy.controller;

import com.liwenhou.Academy.model.Courses;
import com.liwenhou.Academy.repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CourseController {
    @Autowired
    CoursesRepository coursesRepository;

    @GetMapping(value = "/courses")
    public String displayCourses(Model model){
        List<Courses> courses = coursesRepository.findAll();
        model.addAttribute("courses",courses);
        return "courses.html";
    }
}
