package com.diplomatura.fintech.repository;

import com.diplomatura.fintech.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT user FROM IndividualUser user")
    List<User> findAllIndividualUsers();

    @Query("SELECT user FROM LegalEntityUser user")
    List<User> findAllLegalEntityUsers();
}
