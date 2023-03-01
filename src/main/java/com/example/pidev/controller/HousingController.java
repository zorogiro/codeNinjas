package com.example.pidev.controller;

import com.example.pidev.entities.Feedback;
import com.example.pidev.entities.Housing;
import com.example.pidev.repository.HousingRepository;
import com.example.pidev.services.IHousingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/housing/")
public class HousingController {

    IHousingService housingService;
    private final HousingRepository housingRepository;

    @PostMapping("add-housing")
    public Housing addhousing(@RequestBody Housing housing) {

        return housingService.addhousing(housing);
    }

    @Transactional
    @GetMapping("/affichage")
    public List<Housing> findByTypeAndPrixLessThan() {


        return housingService.allhousing();
    }
    @GetMapping("/byPrice/{price}")
    public List<Housing> getHebergementsByPrix(@PathVariable double price) {

        return housingService.getHebergementsByPrix(price);
    }



}

















