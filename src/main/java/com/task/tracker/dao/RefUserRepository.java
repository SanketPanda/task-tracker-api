package com.task.tracker.dao;

import com.task.tracker.model.RefUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefUserRepository extends JpaRepository<RefUser, String> {

    @Query("SELECT u FROM RefUser u WHERE u.userName LIKE ?1")
    List<RefUser> findByUserName(final String userName);
}
