package com.liwenhou.Academy.controller;

import com.liwenhou.Academy.model.Courses;
import com.liwenhou.Academy.model.Person;
import com.liwenhou.Academy.repository.CoursesRepository;
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
    ClassesRepository classesRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    CoursesRepository coursesRepository;

    @RequestMapping(value = "/displayClasses", method = GET)
    public String displayClasses(Model model){
        List<Classes> classes = classesRepository.findAll();
        model.addAttribute("classes",classes);
        model.addAttribute("class", new Classes());
        model.addAttribute("classEdit", new Classes());
        return "classes.html";
    }

    @RequestMapping(value = "/addNewClass", method = POST)
    public String addNewClass(Model model, @ModelAttribute("classes") Classes classes){
        classesRepository.save(classes);
        return "redirect:/admin/displayClasses";
    }

    @RequestMapping(value = "/editClass")
    public String editClass(@ModelAttribute("classEdit") Classes classes, Model model){
        classesRepository.save(classes);
        return "redirect:/admin/displayClasses";
    }

    @RequestMapping(value = "/deleteClass")
    public String deleteClass(@RequestParam int classId, Model model){
        Optional<Classes> classes = classesRepository.findById(classId);
        for (Person person : classes.get().getPersons()){
            person.setClasses(null);
            personRepository.save(person);
        }
        classesRepository.deleteById(classId);
        return "redirect:/admin/displayClasses";
    }

    @GetMapping(value = "/displayStudents")
    public String displayStudents(@RequestParam int classId, Model model, HttpSession session,
                                  @RequestParam(required = false) String error, @RequestParam(required = false) String otherId){
        String errorMessage = null;
        Optional<Classes> classes = classesRepository.findById(classId);
        model.addAttribute("classes",classes.get());
        model.addAttribute("person",new Person());
        session.setAttribute("class",classes.get());
        if (error != null){
            if (error.equals("true")){
                errorMessage = "Invalid Email entered!";
                model.addAttribute("errorMessage",errorMessage);
            }
            else if (error.equals("exist")) {
                errorMessage = "Student is existed in this class";
                model.addAttribute("errorMessage",errorMessage);
            }
            else if (error.equals("existInOther")) {
                errorMessage = "Student is existed in class - ";
                String className = classesRepository.findById(Integer.parseInt(otherId)).get().getName();
                model.addAttribute("errorMessage",errorMessage+className);
            }
        }

        return "students.html";
    }

    @PostMapping(value = "/addStudent")
    public String addStudent(@ModelAttribute("person") Person person, Model model, HttpSession session){
        Classes classes = (Classes) session.getAttribute("class");
        model.addAttribute("person",new Person());
        Person personEntity = personRepository.findByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId()>0)){
            return "redirect:/admin/displayStudents?classId="+classes.getClassId()+"&error=true";
        } else if (personEntity.getClasses() != null && personEntity.getClasses().getClassId() == classes.getClassId()) {
            return "redirect:/admin/displayStudents?classId="+classes.getClassId()+"&error=exist";
        } else if (personEntity.getClasses() !=null && personEntity.getClasses().getClassId() != classes.getClassId()) {
            return "redirect:/admin/displayStudents?classId="+classes.getClassId()+"&error=existInOther"+"&otherId="+personEntity.getClasses().getClassId();
        }
        personEntity.setClasses(classes);
        classes.getPersons().add(personEntity);
        classesRepository.save(classes);
        session.setAttribute("class", classes);
        return "redirect:/admin/displayStudents?classId="+classes.getClassId();
    }

    @RequestMapping(value = "/deleteStudent")
    public String deleteStudent(@RequestParam int personId, Model model, HttpSession session){
        Classes classes = (Classes) session.getAttribute("class");
        Optional<Person> person = personRepository.findById(personId);
        person.get().setClasses(null);
        classes.getPersons().remove(person.get());
        classes = classesRepository.save(classes);
        session.setAttribute("class",classes);
        return "redirect:/admin/displayStudents?classId="+classes.getClassId();
    }

    @GetMapping(value = "/displayCourses")
    public String displayCourses(Model model){
        List<Courses> courses = coursesRepository.findAll();
        model.addAttribute("courses", courses);
        model.addAttribute("course", new Courses());
        return "courses_secure.html";
    }

    @PostMapping(value = "/addNewCourse")
    public String addNewCourse(@ModelAttribute("course") Courses courses, Model model){
        coursesRepository.save(courses);
        return "redirect:/admin/displayCourses";
    }
    @RequestMapping(value = "/editCourse")
    public String editCourse(@RequestParam int courseId, @ModelAttribute("editCourse") Courses courses, Model model){
        return "redirect:/admin/displayCourses";
    }

    @RequestMapping(value = "/deleteCourse")
    public String deleteCourse(@RequestParam int courseId, Model model){
        Optional<Courses> courses = coursesRepository.findById(courseId);
        coursesRepository.save(courses.get());
        return "redirect:/admin/displayCourses";
    }

    @GetMapping(value = "/viewStudentsInCourse")
    public String viewStudentsInCourse(@RequestParam int courseId, @RequestParam(required = false) String error,
                                       Model model, HttpSession session){
        String errorMessage = null;
        Optional<Courses> courses = coursesRepository.findById(courseId);
        model.addAttribute("courses",courses.get());
        model.addAttribute("person",new Person());
        session.setAttribute("courses",courses.get());
        if (error != null) {
            errorMessage = "Invalid student email";
            model.addAttribute("errorMessage",errorMessage);
        }
        return "course_students.html";
    }

    @PostMapping(value = "/addStudentToCourse")
    public String addStudentToCourse(@ModelAttribute("person") Person person, Model model, HttpSession session){
        Courses courses = (Courses) session.getAttribute("courses");
        model.addAttribute("person",new Person());
        Person personEntity = personRepository.findByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId()>0)){
            return "redirect:/admin/viewStudentsInCourse?courseId="+courses.getCourseId()+"&error=true";
        }
        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);
//        personRepository.save(personEntity);
        coursesRepository.save(courses);
        session.setAttribute("courses",courses);
        return "redirect:/admin/viewStudentsInCourse?courseId="+courses.getCourseId();
    }

    @RequestMapping(value = "/deleteStudentFromCourse")
    public String deleteStudentFromCourse(@RequestParam int personId, Model model, HttpSession session){
        Courses courses = (Courses) session.getAttribute("courses");
        Optional<Person> person = personRepository.findById(personId);
        person.get().getCourses().remove(courses);
        courses.getPersons().remove(person.get());
        coursesRepository.save(courses);
        session.setAttribute("courses",courses);
        return "redirect:/admin/viewStudentsInCourse?courseId="+courses.getCourseId();
    }
}