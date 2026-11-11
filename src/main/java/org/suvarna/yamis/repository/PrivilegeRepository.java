package org.suvarna.yamis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.suvarna.yamis.entity.Privilege;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Optional<Privilege> findByPrivilegeName(String privilegeName);

    boolean existsByPrivilegeName(String privilegeName);

    List<Privilege> findByPrivilegeNameIn(Collection<String> privilegeNames);

    Page<Privilege> findAll(Pageable pageable);
}

