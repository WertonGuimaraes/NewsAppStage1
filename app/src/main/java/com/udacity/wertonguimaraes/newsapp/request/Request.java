package com.udacity.wertonguimaraes.newsapp.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Request {
    private static final String BASE_URL = "http://content.guardianapis.com/search?q=debates&api-key=test&order-by=newest&show-fields=byline";
    private URL mURL;

    public Request() throws MalformedURLException {
        mURL = new URL(BASE_URL);
    }

    public String getInfoInURL() throws IOException {
        String jsonData = "";

        if (mURL == null) {
            return jsonData;
        }

        HttpURLConnection urlConnection = openConection();
        jsonData = getJsonData(urlConnection);
        closeConection(urlConnection);

        return jsonData;
    }

    private String getJsonData(HttpURLConnection urlConnection) throws IOException {
        InputStream inputStream = urlConnection.getInputStream();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader;
        String line;

        if (inputStream == null) {
            return null;
        }

        reader = new BufferedReader(new InputStreamReader(inputStream));

        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();

        if (buffer.length() == 0) {
            return null;
        }

        return buffer.toString();
    }

    private HttpURLConnection openConection() throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) mURL.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        return urlConnection;
    }

    private void closeConection(HttpURLConnection urlConnection) {
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
    }
}