package com.trafficassistant.userservice.repository;

import com.trafficassistant.userservice.model.Report;
import com.trafficassistant.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>
{
    List<Report> getByReportedUser(User user);
    List<Report> getByDate(LocalDateTime date);
    List<Report> removeById(Long Id);
}
