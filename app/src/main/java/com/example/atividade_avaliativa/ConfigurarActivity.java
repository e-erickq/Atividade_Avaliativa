package com.example.atividade_avaliativa;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConfigurarActivity extends AppCompatActivity implements View.OnClickListener{
    EditText editText;
    RadioGroup radioGroup;
    CheckBox hipolipidicaChk;
    CheckBox hipoglicidicaChk;
    CheckBox semGlutenChk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configurar);

        editText = findViewById(R.id.name_edit);
        String nomeSalvo = getSharedPreferences("Configuracao", MODE_PRIVATE)
                .getString("nome", "Admin");
        editText.setText(nomeSalvo);

        radioGroup = findViewById(R.id.sexo_radioGrp);
        String generoSalvo = getSharedPreferences("Configuracao", MODE_PRIVATE)
                .getString("genero", "Masculino");

        if (generoSalvo.equals("Masculino")) {
            radioGroup.check(R.id.male_btn);
        } else if (generoSalvo.equals("Feminino")) {
            radioGroup.check(R.id.female_btn);
        }

        hipolipidicaChk = findViewById(R.id.hipolip_checkbox);
        hipoglicidicaChk = findViewById(R.id.hipoglici_checkbox);
        semGlutenChk = findViewById(R.id.semglutenn_checkbox);

        SharedPreferences prefs = getSharedPreferences("Configuracao", MODE_PRIVATE);
        hipolipidicaChk.setChecked(prefs.getBoolean("hipolipidica", false));
        hipoglicidicaChk.setChecked(prefs.getBoolean("hipoglicidica", false));
        semGlutenChk.setChecked(prefs.getBoolean("semGluten", false));

        Button confirmarBtn = findViewById(R.id.confirmar_id);
        Button cancelarBtn = findViewById(R.id.cancelar_id);
        confirmarBtn.setOnClickListener(this);
        cancelarBtn.setOnClickListener(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirmar_id) {
            String nome = editText.getText().toString();

            String genero;
            int radioId = radioGroup.getCheckedRadioButtonId();
            if (radioId == R.id.female_btn) {
                genero = "Feminino";
            } else {
                genero = "Masculino";
            }

            SharedPreferences prefs = getSharedPreferences("Configuracao", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("nome", nome);
            editor.putString("genero", genero);
            editor.putBoolean("hipolipidica", hipolipidicaChk.isChecked());
            editor.putBoolean("hipoglicidica", hipoglicidicaChk.isChecked());
            editor.putBoolean("semGluten", semGlutenChk.isChecked());
            editor.apply();

            finish();
        }
        if (v.getId() == R.id.cancelar_id){
            finish();
        }

    }
}