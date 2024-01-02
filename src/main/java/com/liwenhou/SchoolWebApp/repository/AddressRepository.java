package com.liwenhou.SchoolWebApp.repository;

import com.liwenhou.SchoolWebApp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
