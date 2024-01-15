package com.liwenhou.Academy.controller;

import com.liwenhou.Academy.model.Person;
import com.liwenhou.Academy.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {
    @Autowired
    PersonRepository personRepository;
    @RequestMapping(value = "/dashboard")
    public String displayDashboard(Model model, Authentication authentication, HttpSession session){
        Person person = personRepository.findByEmail(authentication.getName());
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        if (person.getClasses() != null && person.getClasses().getName() != null) {
            model.addAttribute("enrolledClass", person.getClasses().getName());
        }
        session.setAttribute("loggedInPerson",person);
        return "dashboard.html";
    }
}
