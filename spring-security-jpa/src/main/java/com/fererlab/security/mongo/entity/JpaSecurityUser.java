package com.fererlab.security.mongo.entity;



import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "JPA_SECURITY_USER")
public class JpaSecurityUser {

    @Id
    @Column(name = "JSU_ID", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "JSU_USERNAME", unique = true, updatable = false)
    private String username;

    @Column(name = "JSU_PASSWORD", unique = true, updatable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "JPA_SECURITY_USER_ROLE", joinColumns = @JoinColumn(name = "JSU_ID"), inverseJoinColumns = @JoinColumn(name = "JSR_ID"))
    private Set<JpaSecurityRole> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<JpaSecurityRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<JpaSecurityRole> roles) {
        this.roles = roles;
    }
}
