package com.trafficassistant.model.wrapper;

import com.trafficassistant.model.Ban;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BanList
{
    List<Ban> bans;
}
