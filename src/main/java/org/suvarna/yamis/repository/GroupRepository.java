package org.suvarna.yamis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.suvarna.yamis.entity.Group;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByGroupId(UUID groupId);

    Optional<Group> findByGroupName(String groupName);

    boolean existsByGroupName(String groupName);

    @EntityGraph(attributePaths = {"roles"})
    Optional<Group> findWithRolesByGroupId(UUID groupId);

    List<Group> findByGroupIdIn(Collection<UUID> groupIds);

    Page<Group> findAll(Pageable pageable);
}

