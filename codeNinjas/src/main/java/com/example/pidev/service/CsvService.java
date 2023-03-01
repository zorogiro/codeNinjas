package com.example.pidev.service;

import com.example.pidev.entities.CsvRow;
import com.example.pidev.entities.OfferType;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CsvService {

    File filePath = new File("C:\\Users\\khawl\\codeNinjas\\codeNinjas\\src\\main\\resources\\notePI.csv");

    private CsvMapper csvMapper;
    @Autowired
    OfferService offerService;

    public List<CsvRow> readCsvFile(File filePath , OfferType offerType) throws IOException {
        List<CsvRow> csvRows = new ArrayList<>();
        try (Reader reader = new FileReader(filePath)) {
            CsvToBean<CsvRow> csvToBean = new CsvToBeanBuilder<CsvRow>(reader)
                    .withType(CsvRow.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            for (CsvRow csvRow : csvToBean) {
                int marks = csvRow.getMarks();
                offerService.calculateScore(offerType);
               // csvRows.add(csvRow);
            }
        } catch (IOException e) {
            throw new IOException("Error reading CSV file: " + e.getMessage());
        }

        return csvRows;
    }

    // Other methods to manipulate CSV data



    /*public List<UserScore> readCsvFile(String filename) throws IOException {
        List<UserScore> userScores = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(filename))) {
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                String name = line[0];
                int score = Integer.parseInt(line[1]);
                userScores.add(new UserScore(name, score));
            }
        }
        return userScores;
    }*/
}