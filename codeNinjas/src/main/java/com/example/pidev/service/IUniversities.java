package com.example.pidev.service;


import com.example.pidev.entities.University;

public interface IUniversities {
    public University addUniversitie (University u);
    public Iterable<University> retrieveAllUniversitie();
    University retrieveUniversitie(Integer idUniversitie);

    public void deleteUniversitie(Integer id);
    University updateUniversitie(University u);
}
