package com.fererlab.security.mongo.entity;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "JPA_SECURITY_ROLE")
public class JpaSecurityRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "JSR_ID", unique = true, updatable = false)
    private Long id;

    @Column(name = "JSR_NAME", unique = true, updatable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<JpaSecurityUser> users;

    public JpaSecurityRole() {
    }

    public JpaSecurityRole(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<JpaSecurityUser> getUsers() {
        return users;
    }

    public void setUsers(Set<JpaSecurityUser> users) {
        this.users = users;
    }
}
