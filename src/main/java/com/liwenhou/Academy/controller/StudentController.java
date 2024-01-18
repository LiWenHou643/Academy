package com.liwenhou.Academy.controller;

import com.liwenhou.Academy.model.Courses;
import com.liwenhou.Academy.model.Person;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("student")
public class StudentController {

    @GetMapping(value = "/displayCourses")
    public String displayCourses(Model model, HttpSession session){
        Person person = (Person) session.getAttribute("loggedInPerson");
        model.addAttribute("person",person);
        model.addAttribute("courses",person.getCourses());
        return "courses_enrolled.html";
    }
}
