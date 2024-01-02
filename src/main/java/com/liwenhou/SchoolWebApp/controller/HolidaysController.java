package com.liwenhou.SchoolWebApp.controller;

import com.liwenhou.SchoolWebApp.model.Holiday;
import com.liwenhou.SchoolWebApp.repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HolidaysController {
    @Autowired
    private HolidaysRepository holidaysRepository;

    @GetMapping("/holidays/{display}")
    public String displayHolidays(@PathVariable(required = false) String display,Model model){

        if (display != null && display.equals("all")){
            model.addAttribute("festival",true);
            model.addAttribute("federal",true);
        } else if (display != null && display.equals("festival")){
            model.addAttribute("festival",true);
        } else if (display != null && display.equals("federal")){
            model.addAttribute("federal",true);
        }

        List<Holiday> holidays = holidaysRepository.findAll();
        Holiday.Type[] types = Holiday.Type.values();
        for (Holiday.Type type : types){
            model.addAttribute(type.toString(),
                    holidays.stream().filter(holiday -> holiday.getType().equals(type))
                            .collect(Collectors.toList()));
        }
        return "holidays.html";
    }
}
