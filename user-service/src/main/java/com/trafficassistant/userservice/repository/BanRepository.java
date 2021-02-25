package com.trafficassistant.userservice.repository;

import com.trafficassistant.userservice.model.Ban;
import com.trafficassistant.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BanRepository extends JpaRepository<Ban, Long>
{
    List<Ban> getByBannedUser(User user);
}
