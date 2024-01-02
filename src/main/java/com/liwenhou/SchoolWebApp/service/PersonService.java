package com.liwenhou.SchoolWebApp.service;

import com.liwenhou.SchoolWebApp.constants.SchoolConstants;
import com.liwenhou.SchoolWebApp.model.Person;
import com.liwenhou.SchoolWebApp.model.Roles;
import com.liwenhou.SchoolWebApp.repository.PersonRepository;
import com.liwenhou.SchoolWebApp.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    PersonRepository personRepository;

    public boolean createNewPerson(Person person){
        boolean isSaved = false;
        Roles roles = rolesRepository.getByRoleName(SchoolConstants.STUDENT_ROLE);
        person.setRoles(roles);
        Person savedPerson = personRepository.save(person);
        if (savedPerson != null && savedPerson.getPersonId() > 0) isSaved = true;
        return isSaved;
    }
}
