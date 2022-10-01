package com.jeremias.bancoutn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jeremias.bancoutn.databinding.Activity2Binding;


public class Activity2 extends AppCompatActivity {

    private Activity2Binding binding;

    private EditText tasaNom;
    private EditText tasaEf;
    private EditText capitalInvertir;

    private SeekBar dias;

    private TextView textoSimulador;
    private TextView textoDias;
    private TextView textoPlazo;
    private TextView textoCapital;
    private TextView textoIntereses;
    private TextView textoMontoTot;
    private TextView textoMontoTotAnual;

    private Button boton_confirmar;


    private Double interesAnual;
    private Double interesMensual;
    private Double capitalInv;
    private Integer cantMeses;
    private Double interesesGanados;
    private Double montoTotAnual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activity2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        tasaNom = binding.editTasaNom;
        tasaEf = binding.editTasaEf;
        capitalInvertir = binding.editCapitalInvertir;

        dias = binding.seekBarMeses;

        textoSimulador = binding.textoSimulador;

        textoDias = binding.textoDias;
        dias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textoDias.setText("  "+i*30+" dias");
                textoPlazo.setText(" Plazo: " +i*30+" dias");
                calcular();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        textoPlazo = binding.textoPlazo;

        textoCapital = binding.textoCapital;
        textoIntereses = binding.textoIntereses;
        textoMontoTot = binding.textoMonto;
        textoMontoTotAnual = binding.textoMontoAnual;

        tasaNom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calcular();
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        tasaEf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calcular();
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        capitalInvertir.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calcular();
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        boton_confirmar = binding.botonConfirmar;
        boton_confirmar.setOnClickListener((v) -> {
            if(camposCompletos()){
                capitalInv = Double.parseDouble(capitalInvertir.getText().toString());
                cantMeses = dias.getProgress();

                Intent intent = new Intent();
                intent.putExtra("capital", capitalInv);
                intent.putExtra("dias", cantMeses*30);
                setResult(RESULT_OK, intent);
                finish();
            }
            else {
                boton_confirmar.setEnabled(false);
                new AlertDialog.Builder(this)
                        .setMessage("Debe completar todos los campos para confirmar")
                        .setNeutralButton("Entendido", null)
                        .show();
            }

        });



    }
    private void calcular(){
        if(camposCompletos()){

            cantMeses = dias.getProgress();
            interesAnual = Double.parseDouble(tasaNom.getText().toString());
            interesMensual = interesAnual/12;
            capitalInv = Double.parseDouble(capitalInvertir.getText().toString());
            interesesGanados = capitalInv * (interesMensual)/100 * cantMeses;
            textoPlazo.setText(" Plazo: " + cantMeses*30 + " dias");
            textoCapital.setText(" Capital: "+ Math.round(capitalInv*1000.0)/1000.0);
            textoIntereses.setText(" Intereses ganados: " + Math.round(interesesGanados*1000.0)/1000.0);
            textoMontoTot.setText(" Monto total: " + Math.round((interesesGanados+capitalInv)*1000.0)/1000.0);
            textoMontoTotAnual.setText(" Monto total anual: " + Math.round(((interesAnual/100*capitalInv) + capitalInv)*1000.0)/1000.0);
            boton_confirmar.setEnabled(true);
        }
    }


    private boolean camposCompletos(){
        boolean tasaNomVacio = tasaNom.getText().toString().isEmpty();
        boolean tasaEfVacio = tasaEf.getText().toString().isEmpty();
        boolean capitalVacio = capitalInvertir.getText().toString().isEmpty();
        boolean camposIncompletos = (tasaNomVacio || tasaEfVacio || capitalVacio);

        return !camposIncompletos;
    }
}