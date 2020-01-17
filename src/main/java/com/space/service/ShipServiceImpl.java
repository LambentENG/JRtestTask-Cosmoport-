package com.space.service;

import com.space.exceptions.BadRequestException;
import com.space.exceptions.ShipNotFoundException;
import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;


@Service
public class ShipServiceImpl implements ShipService {

    private ShipRepository shipRepository;

    @Autowired
    public void setShipRepository(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    private void paramsChecker(Ship shipRequired) {

        if (shipRequired.getName() != null && (shipRequired.getName().length() < 1 || shipRequired.getName().length() > 50)) {
            throw new BadRequestException("The ship name is too long or absent");
        }

        if (shipRequired.getPlanet() != null && shipRequired.getPlanet().length() > 50) {
            throw new BadRequestException("The planet name is too long or absent");
        }

        if (shipRequired.getSpeed() != null && (shipRequired.getSpeed() < 0.1D || shipRequired.getSpeed() > 9999.9D)) {
            throw new BadRequestException("The ship speed is out of range");
        }

        if (shipRequired.getCrewSize() != null && (shipRequired.getCrewSize() < 10 || shipRequired.getCrewSize() > 5000)) {
            throw new BadRequestException("The crew size is out of range");
        }

        if (shipRequired.getProdDate() != null) {
            Calendar date = Calendar.getInstance();
            date.setTime(shipRequired.getProdDate());
            if (date.get(Calendar.YEAR) < 2800 || date.get(Calendar.YEAR) > 3019) {
                throw new BadRequestException("The date of ship manufacture is out of range");
            }
        }
    }

@Override
    public Long idChecker(String id) {
//realization is to be added
    }

    private Double calculateRating(Ship shipRequired) {
        //realization to be added
    }

    @Override
    public Ship createShip(Ship shipRequired) {
        if (shipRequired.getName() == null
                || shipRequired.getPlanet() == null
                || shipRequired.getShipType() == null
                || shipRequired.getProdDate() == null
                || shipRequired.getSpeed() == null
                || shipRequired.getCrewSize() == null) {
            throw new BadRequestException("Please fill in all required fields");
        }
        paramsChecker(shipRequired);
        if (shipRequired.getUsed() == null) {
            shipRequired.setUsed(false);
        }
        Double rating = calculateRating(shipRequired);
        shipRequired.setRating(rating);
        return shipRepository.saveAndFlush(shipRequired);
    }

    @Override
    public Ship getShip(Long id) {
        if (!shipRepository.existsById(id)) {
            throw new ShipNotFoundException("Ship is not found");
        }
        return shipRepository.findById(id).get();
    }

    @Override
    public Ship editShip(Long id, Ship shipRequired) {
        paramsChecker(shipRequired);

        if (!shipRepository.existsById(id))
            throw new ShipNotFoundException("Ship is not found");

        Ship changedShip = shipRepository.findById(id).get();

        if (shipRequired.getName() != null)
            changedShip.setName(shipRequired.getName());

        if (shipRequired.getPlanet() != null)
            changedShip.setPlanet(shipRequired.getPlanet());

        if (shipRequired.getShipType() != null)
            changedShip.setShipType(shipRequired.getShipType());

        if (shipRequired.getProdDate() != null)
            changedShip.setProdDate(shipRequired.getProdDate());

        if (shipRequired.getSpeed() != null)
            changedShip.setSpeed(shipRequired.getSpeed());

        if (shipRequired.getUsed() != null)
            changedShip.setUsed(shipRequired.getUsed());

        if (shipRequired.getCrewSize() != null)
            changedShip.setCrewSize(shipRequired.getCrewSize());

        Double rating = calculateRating(changedShip);
        changedShip.setRating(rating);

        return shipRepository.save(changedShip);
    }

    @Override
    public void deleteByID(Long id) {
        if (shipRepository.existsById(id)) {
            shipRepository.deleteById(id);
        } else {
            throw new ShipNotFoundException("Ship is not found");
        }
    }

    @Override
    public void updateShip(Long id) {
//optional
    }

//Specification is to be added

}