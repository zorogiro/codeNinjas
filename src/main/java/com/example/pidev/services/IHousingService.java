package com.example.pidev.services;


import com.example.pidev.entities.Housing;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IHousingService {



    Housing addhousing (Housing housing);

    List<Housing> allhousing();

    List<Housing> rechercherParPrix(double price);



}
