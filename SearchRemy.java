import com.google.gson.Gson;
//import java.io.InputStreamReader;


// compile this via bash by entering the following
// javac -cp gson-2.10.1.jar SearchRemy.java
// then run this via bash by entering the following:
// java -cp gson-2.10.1.jar SearchRemy.java

//or if ur lazy...which i know u are...
// javac -cp gson-2.10.1.jar SearchRemy.java; java -cp gson-2.10.1.jar SearchRemy.java

public class SearchRemy {

    public static void searchRemy(String kanji){
        String api_call = 
        "https://remywiki.com/api.php?action=query&format=json&prop=&titles=" + kanji;

        System.out.println(api_call);

        Gson gson = new Gson();
        
        
    }

    public static void main(String[] args){
        //System.out.println("SLATT");

        searchRemy("〆");
    }
}
