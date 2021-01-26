package org.mubbs.seata.storage.controller;

import org.mubbs.seata.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/{goods}/{count}/dec")
    public void decStorage(@PathVariable("goods") String goods, @PathVariable("count") Integer count) {
        storageService.decStorage(goods, count);
    }

}
