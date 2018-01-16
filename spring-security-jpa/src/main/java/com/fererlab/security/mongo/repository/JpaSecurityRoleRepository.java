package com.fererlab.security.mongo.repository;


import com.fererlab.security.mongo.entity.JpaSecurityRole;
import com.fererlab.security.mongo.entity.JpaSecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSecurityRoleRepository extends JpaRepository<JpaSecurityRole, Long> {
}
