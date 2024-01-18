package com.liwenhou.Academy.repository;

import com.liwenhou.Academy.model.Lessons;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonsRepository extends ListCrudRepository<Lessons, Integer> {

}
