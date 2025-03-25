package com.example.task21;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing UI components
        Spinner spinnerFrom = findViewById(R.id.spinnerFrom);
        Spinner spinnerTo = findViewById(R.id.spinnerTo);
        EditText inputValue = findViewById(R.id.inputValue);
        Button convertButton = findViewById(R.id.convertButton);
        TextView resultText = findViewById(R.id.resultText);

        // Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.units,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        // Convert button click event
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String source = spinnerFrom.getSelectedItem().toString();
                    String target = spinnerTo.getSelectedItem().toString();
                    double input = Double.parseDouble(inputValue.getText().toString());

                    double result = performConversion(source, target, input);
                    String output = String.format("%.2f %s = %.2f %s", input, source, result, target);
                    resultText.setText(output);

                } catch (NumberFormatException e) {
                    resultText.setText("Please enter a valid number");
                }
            }
        });
    }

    private double performConversion(String source, String target, double value) {
        if (isLengthUnit(source) && isLengthUnit(target)) {
            return convertLength(source, target, value);
        } else if (isWeightUnit(source) && isWeightUnit(target)) {
            return convertWeight(source, target, value);
        } else if (isTempUnit(source) && isTempUnit(target)) {
            return convertTemperature(source, target, value);
        }
        return 0; // Invalid conversion
    }

    // Length conversion
    private double convertLength(String src, String dest, double val) {
        // Convert to centimeters
        double cm = 0;
        switch (src) {
            case "Inch": cm = val * 2.54; break;
            case "Foot": cm = val * 30.48; break;
            case "Yard": cm = val * 91.44; break;
            case "Mile": cm = val * 160934; break;
            case "Centimeter": cm = val; break;
            case "Meter": cm = val * 100; break;
            case "Kilometer": cm = val * 100000; break;
        }

        // Convert to target unit
        switch (dest) {
            case "Inch": return cm / 2.54;
            case "Foot": return cm / 30.48;
            case "Yard": return cm / 91.44;
            case "Mile": return cm / 160934;
            case "Centimeter": return cm;
            case "Meter": return cm / 100;
            case "Kilometer": return cm / 100000;
            default: return 0;
        }
    }

    // Weight conversion
    private double convertWeight(String src, String dest, double val) {
        // Convert to kilogram
        double kg = 0;
        switch (src) {
            case "Pound": kg = val * 0.453592; break;
            case "Ounce": kg = val * 0.0283495; break;
            case "Ton": kg = val * 907.185; break;
            case "Gram": kg = val * 0.001; break;
            case "Kilogram": kg = val; break;
        }

        // Convert to target unit
        switch (dest) {
            case "Pound": return kg / 0.453592;
            case "Ounce": return kg / 0.0283495;
            case "Ton": return kg / 907.185;
            case "Gram": return kg * 1000;
            case "Kilogram": return kg;
            default: return 0;
        }
    }

    // Temperature Conversion
    private double convertTemperature(String src, String dest, double val) {
        // Convert to Celsius
        double celsius = val;
        switch (src) {
            case "Fahrenheit": celsius = (val - 32) / 1.8; break;
            case "Kelvin": celsius = val - 273.15; break;
        }

        // Convert to target unit
        switch (dest) {
            case "Celsius": return celsius;
            case "Fahrenheit": return (celsius * 1.8) + 32;
            case "Kelvin": return celsius + 273.15;
            default: return 0;
        }
    }


    private boolean isLengthUnit(String unit) {
        return unit.equals("Inch") || unit.equals("Foot") || unit.equals("Yard")
                || unit.equals("Mile") || unit.equals("Centimeter")
                || unit.equals("Meter") || unit.equals("Kilometer");
    }

    private boolean isWeightUnit(String unit) {
        return unit.equals("Pound") || unit.equals("Ounce") || unit.equals("Ton")
                || unit.equals("Gram") || unit.equals("Kilogram");
    }

    private boolean isTempUnit(String unit) {
        return unit.equals("Celsius") || unit.equals("Fahrenheit") || unit.equals("Kelvin");
    }
}