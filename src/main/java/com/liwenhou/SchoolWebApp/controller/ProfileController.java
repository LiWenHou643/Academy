package com.liwenhou.SchoolWebApp.controller;

import com.liwenhou.SchoolWebApp.model.Address;
import com.liwenhou.SchoolWebApp.model.Person;
import com.liwenhou.SchoolWebApp.model.Profile;
import com.liwenhou.SchoolWebApp.repository.PersonRepository;
import com.liwenhou.SchoolWebApp.service.PersonService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {
    @Autowired
    PersonService personService;

    @RequestMapping(value = "/displayProfile", method = RequestMethod.GET)
    public String displayProfile(@RequestParam(required = false) String update,Model model, HttpSession session){
        Person person = (Person) session.getAttribute("loggedInPerson");
        Profile profile = new Profile();
        profile.setName(person.getName());
        profile.setEmail(person.getEmail());
        profile.setMobileNumber(person.getMobileNumber());
        if (person.getAddress() != null && person.getAddress().getAddressId() > 0){
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());
        }
        model.addAttribute("profile",profile);
        if (update != null) model.addAttribute("message","Updated successfully");
        return "profile.html";
    }

    @RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
    public String updateProfile(@Valid @ModelAttribute("profile") Profile profile, Errors errors,
                                HttpSession session)
    {
        if(errors.hasErrors()){
            return "profile.html";
        }
        Person person = (Person) session.getAttribute("loggedInPerson");
        person = personService.updateProfile(person, profile);
        session.setAttribute("loggedInPerson", person);
        return "redirect:/displayProfile?update=true";
    }
}
