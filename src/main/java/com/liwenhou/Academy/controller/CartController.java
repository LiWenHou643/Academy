package com.liwenhou.Academy.controller;

import com.liwenhou.Academy.model.Courses;
import com.liwenhou.Academy.model.Person;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CartController {
    @GetMapping(value = "/cart")
    public String displayCart(Model model, HttpSession session){
        Person student = (Person) session.getAttribute("loggedInPerson");
        List<Courses> courses = student.getCourses().stream().toList();
        for (Courses c : courses) System.out.println(c.getName());
        return "cart.html";
    }
}
