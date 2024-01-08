package com.liwenhou.SchoolWebApp.repository;

import com.liwenhou.SchoolWebApp.model.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, Integer> {

}
