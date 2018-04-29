package edu.illinois.cs.cs125.lab12;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


public class updated extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updated);
        ImageView logoPic = findViewById(R.id.logo);
        Intent intent = getIntent();
        String userInput = intent.getExtras().getString("userInput");
        String jsonFile = get_json();
        JsonParser p = new JsonParser();
        //JsonArray arr = p.parse(jsonFile).getAsJsonObject().get("Data").getAsJsonArray();
        JsonArray arr = p.parse(jsonFile).getAsJsonArray();

        for (int i = 1; i < arr.size(); i++) {
            JsonObject obj = arr.get(i).getAsJsonObject();
            String expression = obj.get("expression").getAsString();
            if (expression.equals(userInput)) {
                TextView phraseSearched = findViewById(R.id.Phrase_Searched);
                    phraseSearched.setText(obj.get("expression").getAsString());
                TextView phraseDefined= findViewById(R.id.Definition_Phrase);
                    phraseDefined.setText(obj.get("meaning").getAsString());
                TextView phraseExample= findViewById(R.id.Intro_to_Example);
                    phraseExample.setText("例文/Examples of usage :");
                TextView phraseSentence= findViewById(R.id.Example_Sentence);
                    phraseSentence.setText(obj.get("example").getAsString());
                TextView phraseSentenceEnglish= findViewById(R.id.English_translation);
                    phraseSentenceEnglish.setText(obj.get("example_translation").getAsString());
                TextView phraseFrequency= findViewById(R.id.Possibility);
                    phraseFrequency.setText("Frequency :");
                TextView phraseFrequencyNo= findViewById(R.id.Possibility_Percentage);
                    phraseFrequencyNo.setText(obj.get("frequency").getAsInt());
                    break;
            } else {
                TextView phraseSearched = findViewById(R.id.Phrase_Searched);
                phraseSearched.setText("Invalid Input");
                phraseSearched.setTextColor(Color.parseColor("#FF0000"));
            }
        }
        logoPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(updated.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
    public String get_json() {
        String json;
        try {
            InputStream is = getAssets().open("json_cs15_final_project_2");
            Scanner s = new Scanner(is).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
