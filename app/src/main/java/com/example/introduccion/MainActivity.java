package com.example.introduccion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.text.TextWatcher;

public class MainActivity extends AppCompatActivity {

    EditText etGrados;
    TextView tvResutlado;
    RadioGroup rgGrados;
    ImageView imgGrados;
    CheckBox chkMostrarImagen;

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(MainActivity.class.getName(), "Paso por onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(MainActivity.class.getName(), "Paso por onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(MainActivity.class.getName(), "Paso por onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(MainActivity.class.getName(), "Paso por onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(MainActivity.class.getName(), "Paso por onDestroy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(MainActivity.class.getName(), "Paso por onCreate");

        etGrados = findViewById(R.id.etGrados);
        tvResutlado = findViewById(R.id.tvResultado);
        rgGrados = findViewById(R.id.rgGrados);
        imgGrados = findViewById(R.id.imgGrados);
        chkMostrarImagen = findViewById(R.id.chkMostrarImagen);

        onTextChange(etGrados, getCurrentFocus());
        onRadioButtonSelected(rgGrados, getCurrentFocus());
        onCheckboxChange(chkMostrarImagen, getCurrentFocus());
    }

    public void cambiarImagen(View view, Double resultado) {
        if (resultado >= 10 && resultado <= 25)
            imgGrados.setImageResource(R.drawable.templado);
        if (resultado < 10)
            imgGrados.setImageResource(R.drawable.frio);
        if (resultado > 25)
            imgGrados.setImageResource(R.drawable.caliente);
    }

    public void convertir(View view) {
        Double grados = 0.0;
        Double resultado = 0.0;

        if (!etGrados.getText().toString().isEmpty())
            grados = Double.parseDouble(etGrados.getText().toString());

        if (rgGrados.getCheckedRadioButtonId() == R.id.rbCentigradosFahrenheit)
            resultado = (grados * (9.0 / 5.0)) + 32;

        if (rgGrados.getCheckedRadioButtonId() == R.id.rbFahrenheitCentigrados)
            resultado = (grados - 32) * (5.0 / 9.0);

        if (resultado.isNaN() || resultado.isInfinite())
            tvResutlado.setText("0.0");
        else
            tvResutlado.setText(Double.toString(resultado));

        cambiarImagen(view, resultado);
    }

    public void onTextChange(EditText editText, final View view) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                convertir(view);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onRadioButtonSelected(RadioGroup radioGroup, final View view) {
        convertir(view);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                convertir(view);
            }
        });
    }

    public void onCheckboxChange(CheckBox checkBox, final View view) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    imgGrados.setVisibility(View.VISIBLE);
                } else {
                    imgGrados.setVisibility(View.GONE);
                }
            }
        });
    }

    public void cambiarActivity(View view) {
        Intent intent = new Intent(this, KotlinMainActivity.class);
        startActivity(intent);
    }

    public void sendMessage(View view) {
        // Create the text message with a string
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Mensaje enviado");
        sendIntent.setType("text/plain");

        // Verify that the intent will resolve to an activity
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        }
    }

    public void mostrarCasa(View view){
        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("google.streetview:cbll=27.471215,-99.499114&cbp=0,30,0,0,-15");

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        // Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
        /*Uri gmmIntentUri = Uri.parse("google.streetview:cbll=29.9774614,31.1329645&cbp=0,30,0,0,-15");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);*/
    }
}