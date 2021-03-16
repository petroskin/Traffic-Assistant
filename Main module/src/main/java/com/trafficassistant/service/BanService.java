package com.trafficassistant.service;

import com.trafficassistant.model.Ban;
import com.trafficassistant.model.User;
import com.trafficassistant.model.exceptions.NoBanPrivilegeException;
import com.trafficassistant.model.wrapper.BanList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class BanService
{
    @Autowired
    private RestTemplate restTemplate;

    public List<Ban> getByBannedUser(String username)
    {
        return Objects.requireNonNull(restTemplate.postForObject(
                "http://USER-SERVICE/bans-rest/get-by-banned-user",
                username,
                BanList.class)).getBans();
    }

    public List<Ban> getByBannedUser(User user)
    {
        return getByBannedUser(user.getUsername());
    }

    public Ban addBan(Ban ban) throws NoBanPrivilegeException
    {
        Ban ret = restTemplate.postForObject("http://USER-SERVICE/bans-rest/add-ban", ban, Ban.class);
        if (ret == null)
            throw new NoBanPrivilegeException();
        return ret;
    }

    public Ban addBan(String bannedUserUN, String adminBanningUN, LocalDateTime date) throws NoBanPrivilegeException
    {
        User bannedUser = new User("", bannedUserUN, "", "");
        User adminBanning = new User("", adminBanningUN, "", "");
        return addBan(new Ban(bannedUser, adminBanning, date));
    }
}
