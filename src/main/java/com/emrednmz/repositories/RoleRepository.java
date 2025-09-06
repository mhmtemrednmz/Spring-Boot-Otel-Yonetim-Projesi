package com.emrednmz.repositories;

import com.emrednmz.enums.ERole;
import com.emrednmz.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

    List<Role> findByNameIn(List<ERole> names);

    boolean existsByName(ERole name);
}
