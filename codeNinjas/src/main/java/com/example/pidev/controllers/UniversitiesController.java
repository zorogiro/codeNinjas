package com.example.pidev.controllers;

import com.example.pidev.entities.Offer;
import com.example.pidev.entities.University;
import com.example.pidev.service.UniversitiesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/Universitie")
@AllArgsConstructor
public class UniversitiesController {
    @Autowired
    UniversitiesService universitiesService;
    @GetMapping("/")

    public Iterable<University>  GetAlluniversities(){
        return universitiesService.retrieveAllUniversitie();
    }

    @GetMapping("/retrieve-Universities/{Universities-id}")

    public University retrieveOffre(@PathVariable("Universities-id") Integer UniversitiesId) {
        return universitiesService.retrieveUniversitie(UniversitiesId);
    }

    @PostMapping("/addUniversities")
    @ResponseBody

    public void addOffre(@RequestBody University u ) {

        universitiesService.addUniversitie(u);
    }

    @PutMapping("/updateUniversities")
    @ResponseBody
    public void updatePOffre(@RequestBody University Universitie) {
        universitiesService.updateUniversitie(Universitie);
    }

    @DeleteMapping("/deleteuniversities/{universities-id}")
    @ResponseBody
    public Iterable<University> deleteEtudiant(@PathVariable("universities-id") Integer universitiesId ) {
        universitiesService.deleteUniversitie(universitiesId);
        return universitiesService.retrieveAllUniversitie();
    }


}
