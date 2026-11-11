package org.suvarna.yamis.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "yamis_base_groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk")
    private Long pk;

    @Column(name = "group_id", nullable = false, unique = true, updatable = false)
    private UUID groupId;

    @Column(name = "group_name", nullable = false, unique = true, length = 150)
    private String groupName;

    @Column(name = "is_super_admin")
    private Boolean isSuperAdmin = false;

    @ManyToMany(mappedBy = "groups")
    private Set<User> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "yamis_base_group_roles",
            joinColumns = @JoinColumn(name = "group_pk", referencedColumnName = "pk"),
            inverseJoinColumns = @JoinColumn(name = "role_pk", referencedColumnName = "pk")
    )
    private Set<Role> roles = new HashSet<>();

    public Group() {
    }

    public Group(String groupName, Boolean isSuperAdmin) {
        this.groupId = UUID.randomUUID();
        this.groupName = groupName;
        this.isSuperAdmin = isSuperAdmin != null ? isSuperAdmin : false;
    }

    @PrePersist
    public void ensureGroupId() {
        if (this.groupId == null) {
            this.groupId = UUID.randomUUID();
        }
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.getGroups().add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getGroups().remove(this);
    }

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Boolean getSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(Boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group group)) return false;
        return Objects.equals(groupId, group.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId);
    }
}

