package com.esprit.tn.forum.service;


import com.esprit.tn.forum.model.Housing;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IHousingService {


    Housing addhousing(Housing housing);

    List<Housing> allhousing();


    public List<Housing> getHebergementsByPrix(double price);


}
