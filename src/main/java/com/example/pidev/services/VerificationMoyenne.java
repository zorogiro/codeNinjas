package com.example.pidev.services;

import com.example.pidev.entities.Matiere;
import com.example.pidev.entities.Score;
import com.example.pidev.entities.User;
import com.example.pidev.repositories.CandidateInformation;
import com.example.pidev.repositories.UserRepository;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@AllArgsConstructor
@NoArgsConstructor
@Service
public class VerificationMoyenne {

    @Autowired
     UserRepository userRepositoryy;
    CandidateInformation candidateInformation;
    public  boolean contains(int idUser,File file, String s,int note) throws MalformedURLException,
            IOException, MimeTypeException, SAXException,TikaException {
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
    public  Score calculscore() throws IOException, CsvValidationException {

        String csvFilePath = "C:\\Users\\pc\\Downloads\\codeNinjas-houssem\\src\\main\\java\\com\\example\\pidev\\services\\releve.csv";
        Map<String, Map<String, String>> CSVData = new TreeMap<String, Map<String, String>>();
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
        User user=new User();
        for(Map.Entry<String,Map<String,String>> entry:CSVData.entrySet()){
            //System.err.println("------"+entry.getKey());
            if(entry.getKey().equals("CIN"))
            {
                List<User> users=this.userRepositoryy.findAll();
                for (User user1 : users){
                    if(user1.getCin().equals("0"+entry.getValue().get("")))
                        user=user1;
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
        score.setUser(user);
        //this.candidateInformation.findAll().forEach(item -> System.out.println(item.getMoyenneGenerale()));
        try {
            this.candidateInformation.save(score);
        }catch (Exception e){
            System.err.println(e.toString());
        }

        return score;
        //return CSVData;
    }

}
