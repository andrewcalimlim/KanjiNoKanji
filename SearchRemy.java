import com.google.gson.Gson;


import java.net.URL;
import java.net.URLConnection;

import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.BufferedReader;

import java.util.Scanner;
import java.util.Map;

// compile this via bash by entering the following
// javac -cp gson-2.10.1.jar SearchRemy.java
// then run this via bash by entering the following:
// java -cp gson-2.10.1.jar SearchRemy.java

//or if ur lazy...which i know u are...
// javac -cp gson-2.10.1.jar SearchRemy.java; java -cp gson-2.10.1.jar SearchRemy.java

public class SearchRemy {

    public class RemyWikiJSON{
        String batchcomplete;
        Query query;

    }

    public class Query{
        Pages pages;
    }

    public class Pages{
        Map<String, Result> key;
    }

    public class Result{
        int pageid;
        int ns;
        String title;
        String missing;
    }

    public static void searchRemy(String kanji) throws Exception{
        String api_call = 
        "https://remywiki.com/api.php?action=query&format=json&prop=&titles=" + kanji;
        
        //String json = readUrl(api_call);
        //System.out.println(json);
        
        
        //URL url = new URL(api_call);

        URLConnection connection = new URL(api_call).openConnection();
        
        // converting web URL to JSON string
        // done this way for printing JSON here and being able to verify its accuracy
        // taken from https://stackoverflow.com/questions/14961100/reading-json-from-url-java

        try(Scanner scanner = new Scanner(connection.getInputStream());){
            String response = scanner.useDelimiter("\\A").next();
            System.out.println(response);

            //Type t = new TypeToken<Map<String, Structure>>() {}.getType();

            //JsonParser parser = new JsonParser();
            //JsonElement rootNode = parser.parse(response);

            RemyWikiJSON the_json = new Gson().fromJson(response, RemyWikiJSON.class);
            System.out.println(the_json.batchcomplete);
            System.out.println(the_json.query.pages.key);
        }

        //reader.println();
        //Type listType = new TypeToken<List<MyO
        //final MyClass result = new Gson().fromJson(reader, MyClass.class);

        //RemyWikiJSON the_json = new Gson().fromJson(reader, RemyWikiJSON.class);
        //System.out.println(api_call);

        //Gson gson = new Gson().fromJson(reader, RemyWikiJSON.class);
        //System.out.println(gson);
        /*
        System.out.println(the_json.batchcomplete);
        System.out.println(the_json.query.pages.somenum.pageid);
        System.out.println(the_json.query.pages.somenum.ns);
        System.out.println(the_json.query.pages.somenum.title);
        System.out.println(the_json.query.pages.somenum.missing);
        */


    }




    public static void main(String[] args) throws Exception{
        //System.out.println("SLATT");

        searchRemy("〆");
    }
}
