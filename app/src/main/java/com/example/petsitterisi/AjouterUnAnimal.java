package com.example.petsitterisi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AjouterUnAnimal extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_un_animal);

        Spinner spinner = (Spinner) findViewById(R.id.type_animal);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_animal_array_string, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        Spinner spinner_2 = (Spinner) findViewById(R.id.race_animal);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_2 = ArrayAdapter.createFromResource(this,
                R.array.race_animal_array_string, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner_2.setAdapter(adapter_2);


    }
}