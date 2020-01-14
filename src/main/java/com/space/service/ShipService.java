package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShipService {
    List<Ship> getAllExistingShipsList(Specification<Ship> specification);
    Page<Ship> getAllExistingShipsList(Specification<Ship> specification, Pageable sortedByName);
    Ship createShip(Ship shipRequired);
    Ship editShip(Long id, Ship ship);
    void deleteByID(Long id);
    void updateShip(Long id);
    Ship getShip(Long id);
    Long checkAndParseId(String id);
    Specification<Ship> nameFilter(String name);
    Specification<Ship> planetFilter(String planet);
    Specification<Ship> shipTypeFilter(ShipType shipType);
    Specification<Ship> dateFilter(Long before, Long after);
    Specification<Ship> usageFilter(Boolean isUsed);
    Specification<Ship> speedFilter(Double min, Double max);
    Specification<Ship> crewSizeFilter(Integer min, Integer max);
    Specification<Ship> ratingFilter(Double min, Double max);
}



