package com.example.pidev.controllers;

import com.example.pidev.entities.*;
import com.example.pidev.repositories.ScoreRepository;
import com.example.pidev.services.CandidateCandidacyService;
import com.example.pidev.services.VerificationMoyenne;
import com.example.pidev.util.FileUploadUtil;

import com.opencsv.exceptions.CsvValidationException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/candidacy")
public class CandidateCandidacyController {
    CandidateCandidacyService candidateCandidacyService;
    ScoreRepository scoreRepository;
    VerificationMoyenne verificationMoyenne;

    @Transactional
    @PostMapping("/addCandidacy/{idCandidate}/{idOffer}")
    public Candidacy addCandidacy(@PathVariable("idCandidate") int idCandidate,
                                  @PathVariable("idOffer") int idOffer,
                                  @RequestBody Candidacy candidacy) {
        return this.candidateCandidacyService.addCandidacy(idCandidate, idOffer, candidacy);
    }

    @Transactional
    @DeleteMapping("/deleteCandidacy/{idCandidacy}")
    public void deleteCandidacy(@PathVariable("idCandidacy") int idCandidacy) {
        this.candidateCandidacyService.deleteCandidacy(idCandidacy);
    }

    @Transactional
    @GetMapping("/getCandidacyById/{idCandidate}/{cin}")
    public List<Candidacy> getCandidacyById(@PathVariable("idCandidate") int idCandidate,
                                            @PathVariable("cin") int cin) {
        return this.candidateCandidacyService.findCandidacyByidCandidate(idCandidate);
    }

    @Transactional
    @GetMapping("/stat/{idCandidate}")
    public StatCandidacy StatCandidacybyIdCandidate(@PathVariable("idCandidate") int idCandidate) {
        return this.candidateCandidacyService.statCandidacyByIdCandidate(idCandidate);
    }

    @GetMapping("/verifScore/{idCandidacy}")
    @Transactional
    public Score verif(@PathVariable("idCandidacy") int idCandidacy,@RequestParam("file")MultipartFile multipartFile) throws  IOException,  CsvValidationException ,Exception{
        String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size= multipartFile.getSize();
        FileUploadUtil.saveFile(fileName,multipartFile);
        Score score=verificationMoyenne.calculscore( idCandidacy,fileName);

        scoreRepository.deleteAll();
        return scoreRepository.save(score);
    }
    @PostMapping("/uploadFile")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file")MultipartFile multipartFile) throws IOException{
        String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size= multipartFile.getSize();
        FileUploadUtil.saveFile(fileName,multipartFile);
        FileUploadResponse fileUploadResponse=new FileUploadResponse();
        fileUploadResponse.setFileName(fileName);
        fileUploadResponse.setDownloadUri("/downloadFile");
        fileUploadResponse.setSize(size);
        return new ResponseEntity<FileUploadResponse>(fileUploadResponse, HttpStatus.OK);


    }
}
