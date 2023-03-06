package com.esprit.tn.forum.service;

import com.esprit.tn.forum.model.CsvRow;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CsvService {
    private final AuthService authService;

    public List<CsvRow> readCsv() throws IOException, CsvValidationException {
        List<CsvRow> csvRows = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader("C:\\Users\\Mon Pc\\Documents\\GitHub\\pidev - Copy\\src\\main\\resources\\notePI.csv"))) {
            String[] line;
            // skip the first row, which contains the header
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                double userId = Double.parseDouble(line[0]);
                double moyenne3eme = Double.parseDouble(line[1]);
                double moyenne4eme = Double.parseDouble(line[2]);
                double moyFr = Double.parseDouble(line[3]);
                double moyEng = Double.parseDouble(line[4]);
                double python = Double.parseDouble(line[5]);
                double math = Double.parseDouble(line[6]);
                double spring = Double.parseDouble(line[7]);
                double adf = Double.parseDouble(line[8]);
                double gl = Double.parseDouble(line[9]);
                CsvRow csvRow = new CsvRow(userId, moyenne3eme, moyenne4eme, moyFr, moyEng, python, math, spring, adf, gl);
                csvRows.add(csvRow);
            }
        }
        return csvRows;
    }
}



