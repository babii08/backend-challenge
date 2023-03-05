package com.mvpmatch.backend.adapter.database.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDatabaseRepository extends JpaRepository<UserDBO, Long> {

    Optional<UserDBO> findByUserName(String userName);

}
