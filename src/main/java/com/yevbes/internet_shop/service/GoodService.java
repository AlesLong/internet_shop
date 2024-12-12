package com.yevbes.internet_shop.service;

import com.yevbes.internet_shop.model.Good;

import java.util.List;

public interface GoodService {

    List<Good> showAllGoods();

    Good getGoodById(Long id);

    Good addGood(Good good);

    Good updateGood(Long id, Good updatedGood);

}
