package com.esprit.tn.forum.controller;


import com.esprit.tn.forum.model.*;
import com.esprit.tn.forum.repository.ScoreRepository;

import com.esprit.tn.forum.service.CandidateCandidacyService;
import com.esprit.tn.forum.service.IEmailService;
import com.esprit.tn.forum.service.VerificationMoyenne;
import com.esprit.tn.forum.util.FileUploadUtil;
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
    ScoreRepository scoreRepository;
    CandidateCandidacyService candidateCandidacyService;
    VerificationMoyenne verificationMoyenne;
     IEmailService emailService;
    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details)
    {
        String status
                = emailService.sendSimpleMail(details);

        return status;
    }

    @Transactional
    @PostMapping("/addCandidacy/{idOffer}")
    public Candidacy addCandidacy(
                                  @PathVariable("idOffer") int idOffer) {
        return candidateCandidacyService.addCandidacy( idOffer);

    }

    @Transactional
    @DeleteMapping("/deleteCandidacy/{idCandidacy}")
    public void deleteCandidacy(@PathVariable("idCandidacy") int idCandidacy) {
        System.err.println(idCandidacy);
        this.candidateCandidacyService.deleteCandidacy(idCandidacy);
    }

    @Transactional
    @GetMapping("/getCandidacy")
    public List<Candidacy> getCandidacy() {
        return this.candidateCandidacyService.findCandidacyByidCandidate();
        //return scoreRepository.findScoreByCinUser("06996868");
    }

    @Transactional
    @GetMapping("/stat")
    public StatCandidacy StatCandidacybyIdCandidate() {
        return this.candidateCandidacyService.statCandidacyByIdCandidate();
    }

    @GetMapping("/verifScore/{idCandidacy}")
    @Transactional
    public Score verif(@PathVariable("idCandidacy") int idCandidacy, @RequestParam("file")MultipartFile multipartFile) throws  IOException,  CsvValidationException ,Exception{
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
    @GetMapping("/getAppointmentByIdCandidacy/{idCandidacy}")
    @Transactional
    public Appointment getAppointmentByIdCandidacy(@PathVariable("idCandidacy") int idCandidacy){
        return this.candidateCandidacyService.getAppointmentByIdCandidacy(idCandidacy);
    }

    @GetMapping("/getAppointmentsWithCloseDateAndIdCandidate")
    @Transactional
    public List<Appointment> getAppointmentsWithCloseDateAndIdCandidate(){
        return this.candidateCandidacyService.getAppointmentsWithCloseDateAndIdCandidate();
    }
    @GetMapping("/getAllAppointmentByIdCandidate")
    @Transactional
    public List<Appointment> getAllAppointmentByIdCandidate(){
        return this.candidateCandidacyService.getAllAppointmentByIdCandidate();
    }
}
