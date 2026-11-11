package org.suvarna.yamis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.suvarna.yamis.entity.Role;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String roleName);

    boolean existsByRoleName(String roleName);

    @EntityGraph(attributePaths = {"privileges"})
    Optional<Role> findWithPrivilegesByRoleName(String roleName);

    List<Role> findByRoleNameIn(Collection<String> roleNames);

    Page<Role> findAll(Pageable pageable);
}

