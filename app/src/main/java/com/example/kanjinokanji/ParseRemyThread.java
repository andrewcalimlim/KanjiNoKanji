package com.example.kanjinokanji;

import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



// Written by Andrew Calimlim

public class ParseRemyThread extends Thread {

    /* INSTANCE VARIABLES */
    String pageid;
    String[] results = {null, null, null};

    /* CONSTRUCTOR */
    ParseRemyThread(String pageid){
        this.pageid = pageid;
    }

    /* THREAD TYPE BEAT */

    public void run(){

        try{

            String api_call =
                    "https://remywiki.com/api.php?action=parse&pageid=" + pageid
                            + "&format=json";

            //Log.d("BRUH", api_call);

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
                //Log.d("BRUH", "da jp title is " + jp_title);

                // the following are additional information about the remywiki page that I am
                // scraping off of the HTML code via regular expressions since RemyWiki doesn't
                // organize their data any further than song title and page id

                // shoutouts to simpleflips

                // this is a HUGE multi-line string because it literally is the entire html code
                // in a single string
                String html_text = rootNode.path("parse").findValue("text").findValue("*").asText();
                //Log.d("BRUH", "html text is as follows: \n" + html_text);

                // regex matching for thee artist name

                // possible mismatch if there was a song or artist named "Artist: "
                // but thankfully there is none (with that exact syntax)
                // although there was a IIDX Heroic Verse song called Artist lol
                // dw it still works on that one
                String artist_regex = "(?<=Artist: )(.*)(?=<br)";
                Pattern artist_pattern = Pattern.compile(artist_regex, Pattern.MULTILINE);
                Matcher artist_matcher = artist_pattern.matcher(html_text);
                artist_matcher.find();
                String artist = artist_matcher.group(1);
                //Log.d("BRUH", "da artist is " + artist);

                // regex matching for thee BPM
                // cause I will prob use this to look up kanji ah ddr songs
                // especially thee boss songs with ???? bpm

                String bpm_regex = "(?<=BPM: )(.*)(?=<br)";
                Pattern bpm_pattern = Pattern.compile(bpm_regex, Pattern.MULTILINE);
                Matcher bpm_matcher = bpm_pattern.matcher(html_text);
                bpm_matcher.find();
                String bpm = bpm_matcher.group(1);

                //Log.d("BRUH", "da bpm is " + bpm);

                results[0] = artist;
                results[1] = bpm;
                results[2] = jp_title;

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
