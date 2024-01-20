package com.liwenhou.Academy.controller;

import com.liwenhou.Academy.model.Courses;
import com.liwenhou.Academy.model.Person;
import com.liwenhou.Academy.repository.CoursesRepository;
import com.liwenhou.Academy.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class CartController {
    @Autowired
    CoursesRepository coursesRepository;
    @Autowired
    PersonRepository personRepository;
    @GetMapping(value = "/cart")
    public String displayCart(Model model, HttpSession session){
        Person student = (Person) session.getAttribute("loggedInPerson");
        Set<Courses> courses = student.getCoursesCart();
        model.addAttribute("courses", courses);
        model.addAttribute("courses_number", courses.size());
        return "cart.html";
    }

    @RequestMapping(value = "/cart/delete")
    public String deleteCourseFromCart(@RequestParam int courseId, Model model, HttpSession session){
        Optional<Courses> courses = coursesRepository.findById(courseId);
        Person person = (Person) session.getAttribute("loggedInPerson");

        courses.ifPresent(course -> person.getCoursesCart().remove(course));
        courses.get().getPersonsCart().remove(person);
        personRepository.save(person);

        return "redirect:/cart";
    }
}
