package com.liwenhou.Academy.controller;

import com.liwenhou.Academy.model.Courses;
import com.liwenhou.Academy.model.Person;
import com.liwenhou.Academy.repository.CoursesRepository;
import com.liwenhou.Academy.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class CourseController {
    @Autowired
    CoursesRepository coursesRepository;
    @Autowired
    PersonRepository personRepository;
    @GetMapping(value = "/courses")
    public String displayCourses(Model model){
        List<Courses> courses = coursesRepository.findAll();
        model.addAttribute("courses",courses);
        return "courses.html";
    }

    @GetMapping(value = "/courses/{courseId}")
    public String displayCourses(@PathVariable(name = "courseId") int courseId, Model model, Authentication authentication, HttpSession session){
        Optional<Courses> courses = coursesRepository.findById(courseId);
        model.addAttribute("courses",courses.get());
        session.setAttribute("course_detail",courses.get());

        Person student = personRepository.findByEmail(authentication.getName());

        Set<Courses> coursesAdded = student.getCourses();
        boolean cartAdded = coursesAdded.contains(courses.get());
        Set<Courses> coursesPurchased = student.getCourses();
        boolean purchased = coursesAdded.contains(courses.get());

        if (cartAdded) model.addAttribute("status", "cartAdded");
        else if (purchased) model.addAttribute("status", "purchased");
        else model.addAttribute("status", "available");

        return "course_detail.html";
    }


    @RequestMapping(value = "/addCourseToCart")
    public String addCourseToCart(Model model, HttpSession session) {

        Courses courses = (Courses) session.getAttribute("course_detail");
        Person student = (Person) session.getAttribute("loggedInPerson");

        List<Courses> coursesAdded = student.getCoursesCart().stream().toList();

        boolean exist = coursesAdded.contains(courses);

        model.addAttribute("cartAdded", exist);

        if (!exist) {
            student.getCoursesCart().add(courses);
            courses.getPersonsCart().add(student);
            personRepository.save(student);
        }
            return "redirect:/courses/"+courses.getCourseId();
    }

    @RequestMapping(value = "/addStudentToCourse")
    public String addStudentToCourse(@RequestParam int courseId, Model model, HttpSession session) {
        Courses courses = (Courses) session.getAttribute("course_detail");
        Person student = (Person) session.getAttribute("loggedInPerson");
        List<Courses> coursesAdded = student.getCourses().stream().toList();
        boolean exist = coursesAdded.contains(courses);

        model.addAttribute("cartAdded", exist);

        if (!exist) {
            student.getCourses().add(courses);
            courses.getPersons().add(student);
            personRepository.save(student);
        }
        return "redirect:/courses/"+courses.getCourseId();
    }
}
