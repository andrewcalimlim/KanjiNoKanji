package com.example.kanjinokanji;

import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


// Written by Andrew Calimlim

public class ParseRemy extends Thread {

    /* INSTANCE VARIABLES */
    String pageid;
    String[] results = {null, null};

    /* CONSTRUCTOR */
    ParseRemy(String pageid){
        this.pageid = pageid;
    }

    /* THREAD TYPE BEAT */

    public void run(){

        try{

            String api_call =
                    "https://remywiki.com/api.php?action=parse&pageid=" + pageid
                            + "&format=json";

            Log.d("BRUH", api_call);

            URLConnection connection = new URL(api_call).openConnection();
            // converting web URL to JSON string
            // done this way for printing JSON here and being able to verify its accuracy
            // taken from https://stackoverflow.com/questions/14961100/reading-json-from-url-java

            try(Scanner scanner = new Scanner(connection.getInputStream());){
                String response = scanner.useDelimiter("\\A").next();

                // THANK GOD FOR THE JACKSON TREE MODEL
                // taken from https://www.baeldung.com/jackson-json-node-tree-model
                // (4.1 Locating a Node)

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response);

                String jp_title = rootNode.path("parse").findValue("sections").get(0).findValue("line").asText();
                //String html_text = rootNode.path("parse").findValue("text").findValue("*").asText();

                Log.d("BRUH", "da title is " + jp_title);
                //Log.d("BRUH", "da title is " + jp_title);

                /*

                String resultID = firstMatch.findValue("pageid").asText();
                String resultTitle = firstMatch.findValue("title").asText();
                String resultPage = "https://remywiki.com/?curid=" + resultID;

                results[0] = resultTitle;
                results[1] = resultPage;

                 */


            }


        } catch(Exception e) {
            Log.e("BRUH", "EXCEPTION", e);
        }

    }

    public String[] getResults(){
        return this.results;
    }
    public static void main(String[] args) throws Exception{}
}
