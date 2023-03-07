package com.esprit.tn.forum.service;


import com.esprit.tn.forum.model.*;
import com.esprit.tn.forum.repository.CandidacyRepository;
import com.esprit.tn.forum.repository.UserRepository;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.opencsv.exceptions.CsvValidationException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.language.LanguageProfile;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VerificationMoyenne {

    @Autowired
    UserRepository userRepositoryy;
    @Autowired
    CandidacyRepository candidacyRepository;

    public  boolean contains(int idUser,File file, String s,int note) throws MalformedURLException,
            IOException, SAXException,TikaException {

        ContentHandler handler = new BodyContentHandler();
        MimeTypes mimeRegistry = TikaConfig.getDefaultConfig().getMimeRepository();
        Detector mimeDetector = (Detector) mimeRegistry;

        LanguageIdentifier lang = new LanguageIdentifier(new LanguageProfile(FileUtils.readFileToString(file)));

        Parser parser = TikaConfig.getDefaultConfig().getParser(MediaType.parse(mimeRegistry.getMimeType(file).getName()));

        Metadata parsedMet = new Metadata();

        parser.parse(file.toURI().toURL().openStream(), handler, parsedMet, new ParseContext());

        //User user=userRepositoryy.getReferenceById(idUser);
        //String name=user.getFirstName();
        //String prenom=user.getLastName();
       // String cin =user.getCin();
        String ss=handler.toString();
        char[] ch = new char[ss.length()];
        for(int i=0;i<ss.length();i++){
            ch[i]=ss.charAt(i);
        }
        InputStream  is= this.getClass().getResourceAsStream(file.toString());

        String last="";
       /* if((handler.toString().toLowerCase().contains(name.toLowerCase())) &&
                (handler.toString().toLowerCase().contains(prenom.toLowerCase()))&&
                handler.toString().toLowerCase().contains(cin.toLowerCase())){*/
        for(int i=0;i<ch.length;i++){

            if(ch[i]=='M' && ch[i+1]=='o' && ch[i+2]=='y' && ch[i+3]=='e' && ch[i+4]=='n'
            && ch[i+5]=='n' && ch[i+6]=='e'){
                for(int j=i+6;j<ch.length;j++){
                last=ss.substring(j,ch.length);

                break;}
            }
        }//}
        //Pattern p = Pattern.compile("\\d+");
        //Matcher m = p.matcher(last);

        /*while(m.find()) {

            System.err.println("============"+m.group());
        }*/
        return  last.toString().toLowerCase().contains(String.valueOf(note).toLowerCase());
    }
    public Score calculscore(int idCandidacy, String fileName) throws IOException {

        String csvFilePath = "C:\\Users\\Mon Pc\\Documents\\GitHub\\codeNinjas1\\src\\main\\java\\com\\esprit\\tn\\forum\\FilesUpload"+fileName;
        Map<String, Map<String, String>> CSVData = new TreeMap<String, Map<String, String>>();
        Path root = Paths.get("C:\\Users\\Mon Pc\\Documents\\GitHub\\codeNinjas1\\src\\main\\java\\com\\esprit\\tn\\forum\\FilesUpload");
        Map<String, String> keyVals = null;
        Reader reader = new FileReader(csvFilePath);
        try {
            Iterator<Map<String, String>> iterator = new CsvMapper().readerFor(Map.class)
                    .with(CsvSchema.emptySchema().withHeader()
                            .withColumnSeparator(';').withoutQuoteChar()).readValues(reader);
            int i=0;
            while(iterator.hasNext()){
                keyVals = iterator.next();
                Object[] keys = keyVals.keySet().toArray();
                CSVData.put(keyVals.get(keys[0]), keyVals);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Score score = new Score();
        List<Matiere> matieres = new ArrayList<>();
        String user=null;
        for(Map.Entry<String,Map<String,String>> entry:CSVData.entrySet()){
            //System.err.println("------"+entry.getKey());
            if(entry.getKey().equals("CIN"))
            {
                List<User> users=this.userRepositoryy.findAll();
                for (User user1 : users){
                    if(user1.getCin().equals("0"+entry.getValue().get("")))
                        user=user1.getCin();
                }

            }


            if(!(entry.getKey().equals("Nom")) && !(entry.getKey().equals("Prenom")) &&
                    !(entry.getKey().equals("CIN"))){
                Matiere matiere = new Matiere();


                  if(!entry.getValue().get("Moyenne").equals("")) {
                      if(entry.getValue().get("Matieres").equals("MG")){
                          score.setMoyenneGenerale(Float.valueOf(entry.getValue().get("Moyenne")));
                      }
                      else {
                          matiere.setMoyenneMatiere(Float.valueOf(entry.getValue().get("Moyenne")));
                          matiere.setNomMatiere(entry.getValue().get("Matieres"));

                      }

                    matieres.add(matiere);
                }

            }
        }
        score.setMatieres(matieres);
        score.setCinUser(user);
        Candidacy candidacy=candidacyRepository.getById(idCandidacy);
        System.err.println("-------"+candidacy.getIdCandidacy());
        if(candidacy.getOffer().getTypeOffer().equals(TypeOffer.SAE)){
            float total=0;
            List<Matiere> matieres1=score.getMatieres();
            System.err.println("+++++"+matieres.size());
            for(Matiere matiere:matieres){
                if(matiere.getNomMatiere()!=null && matiere.getNomMatiere()!="") {
                    if (matiere.getNomMatiere().equals("Archntiers")) {
                        total = total + (matiere.getMoyenneMatiere() * 3);
                        System.err.println("++ Archn-tiers+++");
                    }  if (matiere.getNomMatiere().equals("Stage")) {
                        total += matiere.getMoyenneMatiere();
                        System.err.println("++ Stage+++");
                    }  if (matiere.getNomMatiere().equals("ASEAvance")) {
                        total += matiere.getMoyenneMatiere();
                        System.err.println("++ ASEAvance+++");
                    }  if (matiere.getNomMatiere().equals("Fran�ais")) {
                        total += matiere.getMoyenneMatiere() * (3 / 2);
                        System.err.println("++Fran�ais+++");
                    }  if (matiere.getNomMatiere().equals("Anglais")) {
                        total += matiere.getMoyenneMatiere() * (3 / 2);
                        System.err.println("++Anglais+++");
                    }
                }
            }

            total+=score.getMoyenneGenerale()*(3/2);
            System.err.println(total+"====");
            score.setScoreTotale(total);
            if(candidacy.getOffer().getScoreOffer()<=score.getScoreTotale())
                score.setAccepted(true);
            else
                score.setAccepted(false);
            score.setFile(new File(csvFilePath));
            try {
                Path file = root.resolve(csvFilePath);
                 Files.deleteIfExists(file);
            } catch (IOException e) {
                throw new RuntimeException("Error: " + e.getMessage());
            }
        }
        //this.candidateInformation.findAll().forEach(item -> System.out.println(item.getMoyenneGenerale()));
        return score;
        //return CSVData;
    }

}
