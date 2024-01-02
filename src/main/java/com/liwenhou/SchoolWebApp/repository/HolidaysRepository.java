package com.liwenhou.SchoolWebApp.repository;

import com.liwenhou.SchoolWebApp.model.Holiday;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidaysRepository extends ListCrudRepository<Holiday, String> {

}
