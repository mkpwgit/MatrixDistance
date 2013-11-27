package org.matrixdistance.fileprocessing;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.matrixdistance.domain.Element;
import org.matrixdistance.domain.Matrix;

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
        resultString.append(",");
        for (String country : countries) {
            String countryParts [] = country.split(",");
            resultString.append(countryParts[0]).append(",");
        }
        resultString.append('\n');
        bw.write(new String(resultString));
    }

    public void writeLine(int number, String city, String jsonMatrix) throws IOException {
        StringBuilder resultString = new StringBuilder();
        String cityParts [] = city.split(",");
        resultString.append(cityParts[0]).append(",");
        for (int i=0; i<=number; i++) {
            resultString.append(",");
        }

        ObjectMapper mapper = new ObjectMapper();
        Matrix matrix = mapper.readValue(jsonMatrix, Matrix.class);

        List<Element> elements = matrix.getRows().get(0).getElements();
        for (Element element: elements) {
            resultString.append(element.getDistance().getValue()).append(",");
        }
        resultString.append('\n');
        bw.write(new String(resultString));
    }

    public void writeLine(String originCity, String destinationCity, String jsonMatrix) throws IOException {
        StringBuilder resultString = new StringBuilder();
        String originCityParts [] = originCity.split(",");
        String destinationCityParts [] = destinationCity.split(",");

        resultString.append(originCityParts[0]).append(",").append(destinationCityParts[0]).append(",");

        ObjectMapper mapper = new ObjectMapper();
        Matrix matrix = mapper.readValue(jsonMatrix, Matrix.class);
        List<Element> elements = matrix.getRows().get(0).getElements();
        String kmDistance = elements.get(0).getDistance().getText().split(" ")[0];

        resultString.append(kmDistance);
        resultString.append('\n');

        bw.write(new String(resultString));
    }

    public void closeResource() throws IOException {
        bw.close();
    }

}
