package com.yevbes.internet_shop.controller;

import com.yevbes.internet_shop.model.Good;
import com.yevbes.internet_shop.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class GoodController {

    @Autowired
    private GoodService goodService;

    @GetMapping
    public List<Good> showAllGoods() {
        return goodService.showAllGoods();
    }

    @GetMapping("/{id}")
    public Good getGoodById(@PathVariable Long id) {
        return goodService.getGoodById(id);
    }

    @PostMapping
    public Good addGood(@RequestBody Good good) {
        return goodService.addGood(good);
    }

    @PutMapping("/{id}")
    public Good updateGood(@PathVariable Long id, @RequestBody Good good) {
        return goodService.updateGood(id, good);
    }

}
