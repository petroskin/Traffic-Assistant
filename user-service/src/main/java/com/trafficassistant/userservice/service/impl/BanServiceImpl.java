package com.trafficassistant.userservice.service.impl;

import com.trafficassistant.userservice.model.Ban;
import com.trafficassistant.userservice.model.User;
import com.trafficassistant.userservice.repository.BanRepository;
import com.trafficassistant.userservice.repository.UserRepository;
import com.trafficassistant.userservice.service.BanService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BanServiceImpl implements BanService
{
    private final BanRepository banRepository;
    private final UserRepository userRepository;

    public BanServiceImpl(BanRepository banRepository, UserRepository userRepository)
    {
        this.banRepository = banRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Ban> getByBannedUser(String username)
    {
        User user = userRepository.getByUsername(username);
        return banRepository.getByBannedUser(user);
    }

    @Override
    public Ban addBan(String bannedUserUN, String adminBanningUN, LocalDateTime date)
    {
        User bannedUser = userRepository.getByUsername(bannedUserUN);
        User adminBanning = userRepository.getByUsername(adminBanningUN);
        if (!adminBanning.getAdmin() || bannedUser.getAdmin())
            return null;
        return banRepository.save(new Ban(bannedUser, adminBanning, date));
    }
}
