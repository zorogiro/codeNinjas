package com.esprit.tn.forum.controller;

import com.esprit.tn.forum.model.Housing;
import com.esprit.tn.forum.repository.HousingRepository;
import com.esprit.tn.forum.service.IHousingService;
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

















