package com.example.currencyconverter;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private Spinner spinnerFrom;
    // value of the current currency
    double selectedValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // values of the currencies
        final List<Double> values = new ArrayList<Double>();
        values.add((double) 0);


        // Spinner for currencies (from)
        spinnerFrom = findViewById(R.id.spinnerFrom);
        final List<String> spinnerFromArray = new ArrayList<>();
        spinnerFromArray.add("Select");
        // style of the spinner
        ArrayAdapter<String> adapterFrom = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerFromArray);

        // part that gets the data from url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<Currency>> call = api.getCurrencies();
        call.enqueue(new Callback<List<Currency>>() {
            @Override
            public void onResponse(Call<List<Currency>> call, Response<List<Currency>> response) {
                List<Currency> currency = response.body();
                String[] currencyCodes = new String[currency.size()];
                double buyingRate[] = new double[currency.size()];
                for (int i = 0; i < currency.size(); i++) {
                    currencyCodes[i] = currency.get(i).getCurrency_code();
                    spinnerFromArray.add(currency.get(i).getCurrency_code());
                    values.add(currency.get(i).getBuying_rate());
                }
            }

            @Override
            public void onFailure(Call<List<Currency>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapterFrom);

        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = spinnerFrom.getItemAtPosition(i).toString();
                selectedValue = values.get(i);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final MediaPlayer media_player = MediaPlayer.create(this, R.raw.cash);

        // setting on click listener to a "Submit" button
        Button submit = (Button) findViewById(R.id.submitBt);
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                media_player.start();

                // finding views for from input and setting to value, casting the value to double so we can work with decimal numbers
                EditText fromInput = (EditText) findViewById(R.id.fromInput);
                String inputText = fromInput.getText().toString();


                // checks if there is something in input
                if (inputText.matches("")) {
                    Toast.makeText(MainActivity.this, "Please enter values", Toast.LENGTH_SHORT).show();
                    return;
                }

                double fromInputDouble = Double.valueOf(inputText);
                double convertedValue = fromInputDouble * selectedValue;


                double finalFrom = Double.parseDouble(fromInput.getText().toString());
                TextView result = (TextView) findViewById(R.id.result);
                String selectedFrom = spinnerFrom.getSelectedItem().toString();

                result.setText(Double.toString(convertedValue)
                );
            }
        });
    }

}


