package com.example.pidev.service;

import com.example.pidev.entities.University;
import com.example.pidev.repository.UniversitiesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversitiesService implements IUniversities {
    @Autowired
    UniversitiesRepo universitiesRepo;
    @Override
    public University addUniversitie(University u) {

        University university = universitiesRepo.getByIdUniversity(u.getIdUniversity());
        return universitiesRepo.save(u);
    }

    @Override
    public Iterable<University> retrieveAllUniversitie() {
        return universitiesRepo.findAll();
    }

    @Override
    public University retrieveUniversitie(Integer idUniversitie) {

        return universitiesRepo.findById(idUniversitie).get();
    }

    @Override
    public void deleteUniversitie(Integer id) {
            universitiesRepo.deleteById(id);
    }

    @Override
    public University updateUniversitie(University u) {
        return universitiesRepo.save(u);
    }
}
