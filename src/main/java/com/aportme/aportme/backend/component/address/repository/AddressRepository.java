package com.aportme.aportme.backend.component.address.repository;

import com.aportme.aportme.backend.component.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
