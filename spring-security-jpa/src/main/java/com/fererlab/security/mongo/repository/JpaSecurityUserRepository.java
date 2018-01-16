package com.fererlab.security.mongo.repository;


import com.fererlab.security.mongo.entity.JpaSecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSecurityUserRepository extends JpaRepository<JpaSecurityUser, Long> {

    JpaSecurityUser findByUsernameAndPassword(String username, String password);

}
