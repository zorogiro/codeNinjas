package com.example.pidev.services;

import com.example.pidev.entities.Housing;
import com.example.pidev.repository.HousingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class HousingService implements IHousingService {

    HousingRepository housingRepository;




    @Override
    public Housing addhousing(Housing housing) {
        return housingRepository.save(housing);
    }

    @Override
    public List<Housing> allhousing() {
        return housingRepository.findAll();

    }

    @Override
    public List<Housing> getHebergementsByPrix(double price) {

        return housingRepository.findByPrixLessThan(price);
    }


}
