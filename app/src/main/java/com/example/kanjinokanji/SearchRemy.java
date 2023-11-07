package com.example.kanjinokanji;

import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.net.URLConnection;

import java.util.Scanner;


// Written by Andrew Calimlim

// before moving on to thee actual app make sure to delete this option
// https://stackoverflow.com/questions/57734823/android-studio-refuses-to-run-main


public class SearchRemy extends Thread {

    /* INSTANCE VARIABLES */
    String kanji;
    String[] results = {null, null, null};

    /* CONSTRUCTOR */
    SearchRemy(String kanji){
        this.kanji = kanji;
    }
    /* THREAD TYPE BEAT */

    public void run(){

        try{

            String api_call =
                    "https://remywiki.com/api.php?action=query&list=search&srsearch=" + kanji
                            + "&format=json";

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
                int total_hits = rootNode.path("query").path("searchinfo").findValue("totalhits").asInt();

                //Log.d("BRUH", "total_hits = " + Integer.toString(total_hits));

                if(total_hits > 0){

                    // proudly figured this out by staring at the JsonNode official documentation
                    // for like 2 minutes
                    // anyways let's just arbitrarily choose the first result for now
                    // since its unlikely more than 1 result will ever appear

                    // several months later....
                    // and now my inattentiveness has bitten me in thee ass...essment of what to
                    // do next as I have spent the past hour learning how JSONNode / JSON Array Node
                    // objects work, but it's not terrible

                    // however now this MediaWiki API search can do partial matches now

                    JsonNode firstMatch = rootNode.path("query").findValue("search").get(0);

                    // https://stackoverflow.com/questions/17862418/
                    // how-to-get-a-value-from-a-json-string-using-jackson-library

                    String resultID = firstMatch.findValue("pageid").asText();
                    String resultTitle = firstMatch.findValue("title").asText();
                    String resultPage = "https://remywiki.com/?curid=" + resultID;

                    results[0] = resultTitle;
                    results[1] = resultPage;
                    results[2] = resultID;
                }

            }


        } catch(Exception e) {
            Log.e("BRUH", "EXCEPTION", e);
        }

    }

    public String[] getResults(){
        return this.results;
    }
    public static void main(String[] args) throws Exception{


    }
}
