package com.liwenhou.Academy.controller;

import com.liwenhou.Academy.model.Contact;
import com.liwenhou.Academy.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@Controller
public class ContactController {

    @RequestMapping(value = {"/contact"})
    public String displayContactPage(Contact contact,Model model) {
        model.addAttribute("contact", contact);
        return "contact.html";
    }

    private final ContactService contactService;
    @Autowired
    public ContactController(ContactService contactService){
        this.contactService = contactService;
    }

    @RequestMapping(value = "/saveMsg", method = POST)
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors) {
        if (errors.hasErrors()) {
            log.error("Contact form validation failed due to: " + errors.toString());
            return "contact.html";
        }
        contactService.saveMessageDetails(contact);
        return "redirect:/contact";
    }

    @RequestMapping(value = "/displayMessages/page/{pageNum}", method = GET)
    public String displayMessages(@PathVariable int pageNum, @RequestParam String sortField,
                                  @RequestParam String sortDir, Model model ){
        Page<Contact> msgPage = contactService.findMsgsWithOpenStatus(pageNum,sortField,sortDir);
        List<Contact> contactMsgs = msgPage.getContent();
        model.addAttribute("currentPage",pageNum);
        model.addAttribute("totalPages",msgPage.getTotalPages());
        model.addAttribute("totalMsgs",msgPage.getTotalElements());
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("contactMsgs", contactMsgs);
        return "messages.html";
    }
    @RequestMapping(value = "/closeMsg", method = GET)
    public String closeMessage(@RequestParam(name = "currentPage") int currentPage,
                               @RequestParam(name = "id") int id){
        contactService.updateMsgStatus(id);
        return "redirect:/displayMessages/page/"+currentPage+"?sortField=name&sortDir=asc";
    }
}
