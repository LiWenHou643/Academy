package com.liwenhou.SchoolWebApp.repository;

import com.liwenhou.SchoolWebApp.model.Contact;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContactRepository extends ListCrudRepository<Contact, Integer> {

    List<Contact> findByStatus(String status);
}
