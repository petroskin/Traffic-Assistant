package com.trafficassistant.service.impl;

import com.trafficassistant.model.Ban;
import com.trafficassistant.model.User;
import com.trafficassistant.model.exceptions.NoBanPrivilegeException;
import com.trafficassistant.model.wrapper.BanList;
import com.trafficassistant.service.BanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class BanServiceImpl implements BanService
{
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Ban> getByBannedUser(String username)
    {
        return Objects.requireNonNull(restTemplate.postForObject(
                "http://USER-SERVICE/bans-rest/get-by-banned-user",
                username,
                BanList.class)).getBans();
    }

    @Override
    public List<Ban> getByBannedUser(User user)
    {
        return getByBannedUser(user.getUsername());
    }

    @Override
    public Ban addBan(Ban ban) throws NoBanPrivilegeException
    {
        Ban ret = restTemplate.postForObject("http://USER-SERVICE/bans-rest/add-ban", ban, Ban.class);
        if (ret == null)
            throw new NoBanPrivilegeException();
        return ret;
    }

    @Override
    public Ban addBan(String bannedUserUN, String adminBanningUN, LocalDateTime date) throws NoBanPrivilegeException
    {
        User bannedUser = new User("", bannedUserUN, "", "");
        User adminBanning = new User("", adminBanningUN, "", "");
        return addBan(new Ban(bannedUser, adminBanning, date));
    }
}
