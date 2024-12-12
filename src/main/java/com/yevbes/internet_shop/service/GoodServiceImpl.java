package com.yevbes.internet_shop.service;

import com.yevbes.internet_shop.exception.GoodNotFoundException;
import com.yevbes.internet_shop.model.Good;
import com.yevbes.internet_shop.repository.GoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodServiceImpl implements GoodService {

    private static final Logger logger = LoggerFactory.getLogger(GoodServiceImpl.class);

    @Autowired
    private GoodRepository goodRepository;

    @Override
    public List<Good> showAllGoods() {
        logger.info("Fetching all goods");
        return goodRepository.findAll();
    }

    @Override
    public Good getGoodById(Long id) {
        logger.info("Fetching good with id: {}", id);
        return goodRepository.findById(id)
                .orElseThrow(() -> new GoodNotFoundException("Good not found with id: " + id));
    }

    @Override
    public Good addGood(Good good) {
        logger.info("Adding new good: {}", good);
        return goodRepository.save(good);
    }

    @Override
    public Good updateGood(Long id, Good updatedGood) {
        logger.info("Updating good with id: {}", id);
        Good existingGood = goodRepository.findById(id)
                .orElseThrow(() -> new GoodNotFoundException("Good not found"));

        existingGood.setName(updatedGood.getName() != null ? updatedGood.getName() : existingGood.getName());
        existingGood.setPrice(updatedGood.getPrice() > 0 ? updatedGood.getPrice() : existingGood.getPrice());
        existingGood.setQuantity(updatedGood.getQuantity() >= 0 ? updatedGood.getQuantity() : existingGood.getQuantity());

        return goodRepository.save(existingGood);
    }

}
