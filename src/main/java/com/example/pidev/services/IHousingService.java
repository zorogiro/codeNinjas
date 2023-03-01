package com.example.pidev.services;


import com.example.pidev.entities.Housing;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public interface IHousingService {



    Housing addhousing (Housing housing);

    List<Housing> allhousing();




    public List<Housing> getHebergementsByPrix(double price);




}
