package com.liwenhou.Academy.service;

import com.liwenhou.Academy.constants.SchoolConstants;
import com.liwenhou.Academy.model.Address;
import com.liwenhou.Academy.model.Person;
import com.liwenhou.Academy.model.Profile;
import com.liwenhou.Academy.model.Roles;
import com.liwenhou.Academy.repository.PersonRepository;
import com.liwenhou.Academy.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createNewPerson(Person person){
        boolean isSaved = false;

        Roles roles = rolesRepository.getByRoleName(SchoolConstants.STUDENT_ROLE);
        person.setRoles(roles);
        person.setPwd(passwordEncoder.encode(person.getPwd()));

        person = personRepository.save(person);
        if (person != null && person.getPersonId() > 0) isSaved = true;

        return isSaved;
    }

    public Person updateProfile(Person person, Profile profile){
        person.setName(profile.getName());
        person.setEmail(profile.getEmail());
        person.setMobileNumber(profile.getMobileNumber());
        if(person.getAddress() == null || !(person.getAddress().getAddressId()>0)){
            person.setAddress(new Address());
        }
        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setState(profile.getState());
        person.getAddress().setZipCode(profile.getZipCode());
        person = personRepository.save(person);
        return person;
    }
}
