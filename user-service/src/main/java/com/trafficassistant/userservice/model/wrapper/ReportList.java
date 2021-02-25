package com.trafficassistant.userservice.model.wrapper;

import com.trafficassistant.userservice.model.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportList
{
    List<Report> reports;
}
