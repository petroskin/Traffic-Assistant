package com.trafficassistant.service;

import com.trafficassistant.model.Ban;
import com.trafficassistant.model.User;
import com.trafficassistant.model.exceptions.NoBanPrivilegeException;

import java.time.LocalDateTime;
import java.util.List;

public interface BanService
{
    public List<Ban> getByBannedUser(String username);

    public List<Ban> getByBannedUser(User user);

    public Ban addBan(Ban ban) throws NoBanPrivilegeException;

    public Ban addBan(String bannedUserUN, String adminBanningUN, LocalDateTime date) throws NoBanPrivilegeException;
}
