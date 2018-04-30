package edu.illinois.cs.cs125.lab12;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Scanner;

/**
 * Main class for our UI design lab.
 */
public final class MainActivity extends AppCompatActivity {

    /**
     * Run when this activity comes to the foreground.
     *
     * @param savedInstanceState unused
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button searchButton = (Button) findViewById(R.id.Search_Button);
        final SearchView userInput = findViewById(R.id.Search_Bar);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(userInput.getQuery().toString());

            }
        });
    }
    protected void search(final String userInput) {
        //ImageView logoPic = findViewById(R.id.logo);
        String jsonFile = get_json();
        //System.out.println("start printing \n\n\n");
        //System.out.println(jsonFile);
        JsonParser p = new JsonParser();
        //JsonArray arr = p.parse(jsonFile).getAsJsonObject().get("Data").getAsJsonArray();
        JsonArray arr = p.parse(jsonFile).getAsJsonArray();

        boolean found = false;
        for (int i = 0; i < arr.size(); i++) {
            JsonObject obj = arr.get(i).getAsJsonObject();
            String expression = obj.get("expression").getAsString();
            if (expression.equals(userInput)) {
                TextView phraseSearched = findViewById(R.id.Phrase_Searched);
                phraseSearched.setText(obj.get("expression").getAsString());
                TextView phraseDefined= findViewById(R.id.Definition_Phrase);
                phraseDefined.setText(obj.get("meaning").getAsString());
                TextView phraseExample = findViewById(R.id.Intro_to_Example);
                phraseExample.setText("例文/Examples of usage :");
                TextView phraseSentence = findViewById(R.id.Example_Sentence);
                phraseSentence.setText(obj.get("example").getAsString());
                TextView phraseSentenceEnglish = findViewById(R.id.English_translation);
                phraseSentenceEnglish.setText(obj.get("example_translation").getAsString());
                TextView phraseFrequency = findViewById(R.id.Possibility);
                phraseFrequency.setText("Frequency :");
                TextView phraseFrequencyNo = findViewById(R.id.Possibility_Percentage);
                phraseFrequencyNo.setText(obj.get("frequency").getAsString());
                found = true;
                break;
            }

        }
        if(!found) {
            TextView phraseSearched = findViewById(R.id.Phrase_Searched);
            phraseSearched.setText("Invalid Input");
            phraseSearched.setTextColor(Color.parseColor("#FF0000"));

        }

    }
    public String get_json() {
        //String json;
        try {
            InputStream is = getAssets().open("json_cs15_final_project_2.json");
            Scanner s = new Scanner(is).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    }
