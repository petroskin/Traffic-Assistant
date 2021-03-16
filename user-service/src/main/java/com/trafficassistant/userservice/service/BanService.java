package com.trafficassistant.userservice.service;

import com.trafficassistant.userservice.model.Ban;
import com.trafficassistant.userservice.model.User;
import com.trafficassistant.userservice.repository.BanRepository;
import com.trafficassistant.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class BanService
{
    private final BanRepository banRepository;
    private final UserRepository userRepository;

    public BanService(BanRepository banRepository, UserRepository userRepository)
    {
        this.banRepository = banRepository;
        this.userRepository = userRepository;
    }

    public List<Ban> getByBannedUser(String username)
    {
        User user = userRepository.getByUsername(username);
        return banRepository.getByBannedUser(user);
    }

    public Ban addBan(String bannedUserUN, String adminBanningUN, LocalDateTime date)
    {
        User bannedUser = userRepository.getByUsername(bannedUserUN);
        User adminBanning = userRepository.getByUsername(adminBanningUN);
        if (!adminBanning.getAdmin())
            return null;
        return banRepository.save(new Ban(bannedUser, adminBanning, date));
    }
}
