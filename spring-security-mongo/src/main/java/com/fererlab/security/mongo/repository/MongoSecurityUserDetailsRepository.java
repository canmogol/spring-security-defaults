package com.fererlab.security.mongo.repository;


import com.fererlab.security.mongo.entity.MongoSecurityUserRoles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MongoSecurityUserDetailsRepository extends MongoRepository<MongoSecurityUserRoles, String> {

    @Query("{$and:[{'username':?0},{'password':?1}]}")
    MongoSecurityUserRoles findByUsernameAndPassword(String username, String password);


}
