package com.jeremias.bancoutn;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.jeremias.bancoutn.databinding.ActivityMainBinding;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Button boton_simular;
    private Button boton_constituir;
    private EditText editNombre;
    private EditText editApellido;
    private Spinner monedas;
    String[] lista_moneda = {"PESOS", "DOLARES"};
    private static final int REQUEST_CODE_ACTIVITY2 = 77;
    private ArrayAdapter<String> adapter;
    private Double capital;
    private int dias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        monedas = binding.spinnerMoneda;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista_moneda);
        monedas.setAdapter(adapter);

        editNombre = binding.editNombre;
        editApellido = binding.editApellido;

        boton_simular = binding.botonSimular;
        boton_simular.setOnClickListener((v) -> {
            if(camposCompletos()){

                Intent intent = new Intent(this, Activity2.class);

                String moneda = adapter.getItem(monedas.getSelectedItemPosition()).toString();

                intent.putExtra("moneda", moneda);

                startActivityForResult(intent, REQUEST_CODE_ACTIVITY2);
            }
            else {
                new AlertDialog.Builder(this)
                        .setMessage("Debe ingresar su Nombre y Apellido para continuar")
                        .setNeutralButton("Entendido", null)
                        .show();
            }

        });
        boton_constituir = binding.botonConstituir;
        boton_constituir.setOnClickListener((v) -> {

            new AlertDialog.Builder(this)
                    .setTitle("Felicitaciones "
                            + editNombre.getText() + " "
                            + editApellido.getText() + "! ")
                    .setMessage("Tu plazo fijo de "
                    + capital + " "
                    + adapter.getItem(monedas.getSelectedItemPosition()).toString().toLowerCase(Locale.ROOT)
                    + " por "
                    + dias
                    + " dias ha sido constituido!")
                    .setPositiveButton("PIOLA!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            boton_constituir.setEnabled(false);
                        }
                    })
                    .show();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== RESULT_OK){
            if(requestCode==REQUEST_CODE_ACTIVITY2){
                binding.botonConstituir.setEnabled(true);
                capital = data.getExtras().getDouble("capital");
                dias = data.getExtras().getInt("dias");

            }
        }
    }
    private boolean camposCompletos(){
        boolean nombreVacio = editNombre.getText().toString().isEmpty();
        boolean apellidoVacio = editApellido.getText().toString().isEmpty();
        boolean camposIncompletos = (nombreVacio || apellidoVacio);

        return !camposIncompletos;
    }


}

