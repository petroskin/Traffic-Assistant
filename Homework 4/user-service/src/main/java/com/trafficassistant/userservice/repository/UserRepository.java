package com.trafficassistant.userservice.repository;

import com.trafficassistant.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>
{
    User getByUsernameAndPassword(String username, String password);
    User getByEmailAndPassword(String email, String password);
    User getByUsername(String username);
    User getByEmail(String email);
}
