package com.example.kanjinokanji;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.util.Scanner;

// Written by Andrew Calimlim

// before moving on to thee actual app make sure to delete this option
// https://stackoverflow.com/questions/57734823/android-studio-refuses-to-run-main


public class SearchRemy {


    public static void searchRemy(String kanji) throws Exception{
        System.out.println("Input: " + kanji);

        String api_call =
                "https://remywiki.com/api.php?action=query&format=json&prop=&titles=" + kanji
                + "&redirects";

        // https://stackoverflow.com/questions/22235903/wikipedia-search-api-get-redirect-pageid
        // oh dope you can bypass redirects and just get the pageid for the final page
        // by adding &redirects god i love APIs

        URLConnection connection = new URL(api_call).openConnection();

        // converting web URL to JSON string
        // done this way for printing JSON here and being able to verify its accuracy
        // taken from https://stackoverflow.com/questions/14961100/reading-json-from-url-java

        try(Scanner scanner = new Scanner(connection.getInputStream());){
            String response = scanner.useDelimiter("\\A").next();
            //System.out.println(response);

            // THANK GOD FOR THE JACKSON TREE MODEL
            // taken from https://www.baeldung.com/jackson-json-node-tree-model
            // (4.1 Locating a Node)

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode penultimateNode = rootNode.path("query").path("pages");


            // proudly figured this out by staring at the JsonNode official documentation
            // for like 2 minutes
            // anyways let's just arbitrarily choose the first result for now
            // since its unlikely more than 1 result will ever appear

            String someNumber = penultimateNode.fieldNames().next();

            if(someNumber.equals("-1")){
                System.out.println("There is no song on RemyWiki with this/these kanji as" +
                        " a title.");

            }

            else{
                JsonNode finalNode = penultimateNode.path(someNumber);

                // https://stackoverflow.com/questions/17862418/
                // how-to-get-a-value-from-a-json-string-using-jackson-library

                String resultID = finalNode.get("pageid").asText();
                String resultTitle = finalNode.get("title").asText();

                System.out.println("This title is romanized as " + resultTitle + ".");
                String resultPage = "https://remywiki.com/?curid=" + resultID;
                System.out.println("RemyWiki search returns the following page:\n" +
                        resultPage);

            }

            System.out.println("===");

        }

    }


    public static void main(String[] args) throws Exception{

        searchRemy("〆");
        searchRemy("草");
        searchRemy("最小三倍完全数");
        searchRemy("鬼言集");
    }
}
