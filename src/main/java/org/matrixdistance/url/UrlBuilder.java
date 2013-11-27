package org.matrixdistance.url;


import org.matrixdistance.common.Constants;

import java.util.List;

public class UrlBuilder {

    private String defaultUrl = "http://maps.googleapis.com/maps/api/distancematrix/json?";

    public UrlBuilder() {

    }

    public UrlBuilder(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    public String buildUrl(String originCountries, List<String> destinationCountries) {
        StringBuilder resultUrl = new StringBuilder(defaultUrl);

        StringBuilder stringDestinationCountries = new StringBuilder();
        for (String country: destinationCountries) {
            stringDestinationCountries.append("|"+country);
        }

        resultUrl.append(Constants.ORIGIN_COUNTRIES_LABEL).append("=").append(originCountries);
        resultUrl.append("&").append(Constants.DISTINATION_COUNTRIES_LABEL).append("=").append(stringDestinationCountries);
        resultUrl.append("&").append(Constants.MODE_LABEL).append("=").append(Constants.MODE);
        resultUrl.append("&").append(Constants.LANGUAGE_LABEL).append("=").append(Constants.LANGUAGE);
        resultUrl.append("&").append(Constants.UNITS_LABEL).append("=").append(Constants.UNITS);
        resultUrl.append("&").append(Constants.SENSOR_LABEL).append("=").append("false");

        return new String(resultUrl);
    }
}
