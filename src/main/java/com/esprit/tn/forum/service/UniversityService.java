package com.esprit.tn.forum.service;

import com.esprit.tn.forum.model.University;
import com.esprit.tn.forum.model.User;
import com.esprit.tn.forum.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityService {
    @Autowired
    AuthService authService;
    @Autowired
    private UniversityRepository universityRepository;

    public List<University> getAllUniversities() {
        return universityRepository.findAll();
    }

    public University getUniversityById(Long id) {
        Optional<University> universityOptional = universityRepository.findById(id);
        if (universityOptional.isPresent()) {
            return universityOptional.get();
        } else {
            return null;
        }
    }

    public University createUniversity(University university) {
        User user =authService.getCurrentUser();
        if(!user.isCandidate()) {
            university.setRecruiter(user);
            return universityRepository.save(university);
        }
        return null;
    }

    public University updateUniversity(Long id, University universityDetails) {
        Optional<University> universityOptional = universityRepository.findById(id);
        if (universityOptional.isPresent()) {
            University university = universityOptional.get();
            university.setName(universityDetails.getName());
            return universityRepository.save(university);
        } else {
            return null;
        }
    }

    public void deleteUniversity(Long id) {
        Optional<University> universityOptional = universityRepository.findById(id);
        User user = authService.getCurrentUser();
        if(!user.isCandidate()) {
            if (universityOptional.isPresent()) {
                universityRepository.delete(universityOptional.get());
            }
        }
    }


}
