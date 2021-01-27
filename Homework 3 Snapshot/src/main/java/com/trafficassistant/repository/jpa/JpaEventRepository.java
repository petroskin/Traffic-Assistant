package com.trafficassistant.repository.jpa;

import com.trafficassistant.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEventRepository extends JpaRepository<Event, Long>
{
}
