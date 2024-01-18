package com.liwenhou.Academy.controller;

import com.liwenhou.Academy.model.Courses;
import com.liwenhou.Academy.model.Lessons;
import com.liwenhou.Academy.model.Person;
import com.liwenhou.Academy.repository.CoursesRepository;
import com.liwenhou.Academy.repository.LessonsRepository;
import com.liwenhou.Academy.repository.PersonRepository;
import com.liwenhou.Academy.service.ContactService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    ContactService contactService;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    CoursesRepository coursesRepository;
    @Autowired
    LessonsRepository lessonsRepository;

    @GetMapping(value = "/displayCourses")
    public String displayCourses(Model model) {
        List<Courses> courses = coursesRepository.findAll();
        model.addAttribute("courses", courses);
        model.addAttribute("course", new Courses());
        return "courses_secure.html";
    }

    @PostMapping(value = "/addNewCourse")
    public String addNewCourse(@ModelAttribute("course") Courses courses, Model model) {
        coursesRepository.save(courses);
        return "redirect:/admin/displayCourses";
    }

    @RequestMapping(value = "/updateCourse")
    public String updateCourse(@RequestParam int courseId,
                               @RequestParam(required = false) String save,
                               @ModelAttribute("course") Courses course, Model model) {
        Optional<Courses> courses = coursesRepository.findById(courseId);
        Courses updateCourse = new Courses();
        updateCourse.setCourseId(courses.get().getCourseId());
        updateCourse.setName(courses.get().getName());
        updateCourse.setFees(courses.get().getFees());
        updateCourse.setInstructor(courses.get().getInstructor());
        updateCourse.setDescription(courses.get().getDescription());
        updateCourse.setImage(courses.get().getImage());
        updateCourse.setRating(courses.get().getRating());
        model.addAttribute("course", updateCourse);

        Lessons lesson = new Lessons();
        model.addAttribute("lesson", lesson);

        model.addAttribute("courses", courses.get());

        if (save != null) {
            coursesRepository.save(course);
            return "redirect:/admin/updateCourse?courseId=" + courses.get().getCourseId();
        }

        return "course_update.html";
    }

    @RequestMapping(value = "/deleteCourse")
    public String deleteCourse(@RequestParam int courseId, Model model) {
        coursesRepository.deleteById(courseId);
        return "redirect:/admin/displayCourses";
    }

    @GetMapping(value = "/viewStudentsInCourse")
    public String viewStudentsInCourse(@RequestParam int courseId,
                                       @RequestParam(required = false) String error,
                                       Model model, HttpSession session) {
        String errorMessage = null;
        Optional<Courses> courses = coursesRepository.findById(courseId);
        model.addAttribute("courses", courses.get());
        model.addAttribute("person", new Person());
        session.setAttribute("courses", courses.get());
        if (error != null) {
            errorMessage = "Invalid student email";
            model.addAttribute("errorMessage", errorMessage);
        }
        return "course_students.html";
    }

}
