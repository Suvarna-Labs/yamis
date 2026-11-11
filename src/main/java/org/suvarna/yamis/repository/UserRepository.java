package org.suvarna.yamis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.suvarna.yamis.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUid(UUID uid);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = {"groups"})
    Optional<User> findWithGroupsByUid(UUID uid);

    @Query("SELECT DISTINCT u FROM User u " +
            "LEFT JOIN FETCH u.groups g " +
            "LEFT JOIN FETCH g.roles r " +
            "LEFT JOIN FETCH r.privileges " +
            "WHERE u.uid = :uid")
    Optional<User> findWithFullAuthoritiesByUid(@Param("uid") UUID uid);

    Page<User> findAll(Pageable pageable);
}

