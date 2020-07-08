package com.candiolli.patterncountbody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

    public static final String URL_CHALLENGES_AGE_COUTING = "https://coderbyte.com/api/challenges/json/age-counting";

    public static void main(String[] args) throws IOException {
        URL obj = new URL(URL_CHALLENGES_AGE_COUTING);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();

        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            int countPattern = countPattern(readeBody(con), Pattern.compile("age=[5-6][0-9]"));
            System.out.println(countPattern);
        } else {
            System.out.println("GET request not worked");
        }
    }

    private static String readeBody(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private static int countPattern(String references, Pattern referencePattern) {
        Matcher matcher = referencePattern.matcher(references);
        return Stream.iterate(0, i -> i + 1)
                .filter(i -> !matcher.find())
                .findFirst()
                .get();
    }

}
