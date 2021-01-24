package org.mubbs.seata.account.controller;

import org.mubbs.seata.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/{user}/{points}/points")
    public void addPoints(@PathVariable("points") Integer points, @PathVariable("user") String user) {
        accountService.incPoints(user, points);
    }

}
