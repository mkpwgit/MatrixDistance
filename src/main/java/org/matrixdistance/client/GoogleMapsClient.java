package org.matrixdistance.client;


import org.matrixdistance.fileprocessing.FileProcessing;
import org.matrixdistance.url.UrlBuilder;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

public class GoogleMapsClient {

    private static int totalCount = 10;

    private static String URL = "http://maps.googleapis.com/maps/api/distancematrix/json?origins=Brest,Belarus&destinations=Minsk,Belarus&mode=driving&language=ru-RU&sensor=false";

    private static RestTemplate restTemplate;

    public static void main(String[] args) throws IOException {

        restTemplate = new RestTemplate();
        FileProcessing fileProcessingInputNumbers = new FileProcessing("numbers.txt", true);
        List<Integer> numbers = fileProcessingInputNumbers.getNumbers();
        totalCount = numbers.get(0);
        int iBeginner = numbers.get(1);
        int jBeginner = numbers.get(2);

        FileProcessing fileProcessingInput = new FileProcessing("countries.txt", true);
        List<String> countries = fileProcessingInput.getCountries();

        createCsvFile(countries, iBeginner, jBeginner);

    }

    public static void createCsvFile(List<String> countries, int iBeginner, int jBeginner) throws IOException {
        UrlBuilder urlBuilder = new UrlBuilder();
        FileProcessing fileProcessingOutput = new FileProcessing("result.csv", false);
        int i = iBeginner;
        int j = jBeginner;
        int tempTotalCount = totalCount;

        outerloop:
        for (;i < countries.size(); i = i + 1) {
            String originCountry = countries.get(i);
            for (; j < countries.size(); j = j + 1) {
                String destinationCountry = countries.get(j);
                String url = urlBuilder.buildUrl(originCountry, destinationCountry);
                String jsonResult = restTemplate.getForObject(url, String.class);
                System.out.println(jsonResult);
                fileProcessingOutput.writeLine(originCountry, destinationCountry, jsonResult);
                tempTotalCount--;
                if (tempTotalCount == 0) {
                    break outerloop;
                }
            }
            j=i+2;
        }

        fileProcessingOutput.closeResource();
        writeFinishValues(totalCount, i, ++j);
    }

    public static void writeFinishValues(int totalCount, int iFinish, int jFinish) throws IOException {
        FileProcessing fileProcessing = new FileProcessing("numbers.txt", false);
        fileProcessing.writeNumber(totalCount);
        fileProcessing.writeNumber(iFinish);
        fileProcessing.writeNumber(jFinish);
        fileProcessing.closeResource();

    }
}
