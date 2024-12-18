package com.yevbes.internet_shop.service;

import com.yevbes.internet_shop.exception.GoodNotFoundException;
import com.yevbes.internet_shop.model.Good;
import com.yevbes.internet_shop.repository.GoodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoodServiceImpl implements GoodService {

    private static final Logger logger = LoggerFactory.getLogger(GoodServiceImpl.class);

    private final GoodRepository goodRepository;

    @Autowired
    public GoodServiceImpl(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

    @Override
    public List<Good> showAllGoods() {
        logger.info("Fetching all goods");
        return goodRepository.findAll();
    }

    @Override
    public Good getGoodById(Long id) {
        logger.info("Fetching good with id: {}", id);
        return goodRepository.findById(id)
                .orElseThrow(() -> new GoodNotFoundException(String.format("Good not found with id: %d", id)));
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
                .orElseThrow(() -> new GoodNotFoundException(String.format("Good not found with id: %d", id)));

        updateGoodFields(existingGood, updatedGood);

        return goodRepository.save(existingGood);
    }

    private void updateGoodFields(Good existingGood, Good updatedGood) {
        Optional.ofNullable(updatedGood.getName())
                .ifPresent(existingGood::setName);

        if (updatedGood.getPrice() > 0) {
            existingGood.setPrice(updatedGood.getPrice());
        }

        if (updatedGood.getQuantity() >= 0) {
            existingGood.setQuantity(updatedGood.getQuantity());
        }
    }
}
