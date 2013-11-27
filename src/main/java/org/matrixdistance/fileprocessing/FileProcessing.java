package org.matrixdistance.fileprocessing;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileProcessing {

    private String filePath;
    private BufferedReader br;
    private BufferedWriter bw;

    public FileProcessing(String filePath, boolean input) throws IOException {
        this.filePath = filePath;
        if (input)
            br = new BufferedReader(new FileReader(filePath));
        else
            bw = new BufferedWriter(new FileWriter(filePath));
    }

    public List<String> getCountries() throws IOException {
        List<String> countries = new ArrayList<String>();
        String country = null;
        while ((country = br.readLine()) != null) {
            countries.add(country);
        }
        return countries;
    }

    public void writeMatrixHeader(List<String> countries) throws IOException {
        StringBuilder resultString = new StringBuilder();
        for (String country : countries) {

            resultString.append(country).append(",");
        }
        bw.write(new String(resultString));
    }

    public void closeResource() throws IOException {
        bw.close();
    }

}
