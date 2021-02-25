package com.trafficassistant.userservice.controller;

import com.trafficassistant.userservice.model.Ban;
import com.trafficassistant.userservice.model.wrapper.BanList;
import com.trafficassistant.userservice.service.BanService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bans-rest")
public class BanController
{
    private final BanService banService;

    public BanController(BanService banService)
    {
        this.banService = banService;
    }

    @PostMapping("/get-by-banned-user")
    public BanList getByBannedUser(@RequestBody String username)
    {
        return new BanList(banService.getByBannedUser(username));
    }

    @PostMapping("add-ban")
    public Ban addBan(@RequestBody Ban ban)
    {
        return banService.addBan(
                ban.getBannedUser().getUsername(),
                ban.getAdminBanning().getUsername(),
                ban.getDate());
    }
}
