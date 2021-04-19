package com.trafficassistant.userservice.service;

import com.trafficassistant.userservice.model.Ban;

import java.time.LocalDateTime;
import java.util.List;

public interface BanService
{
    public List<Ban> getByBannedUser(String username);

    public Ban addBan(String bannedUserUN, String adminBanningUN, LocalDateTime date);
}
