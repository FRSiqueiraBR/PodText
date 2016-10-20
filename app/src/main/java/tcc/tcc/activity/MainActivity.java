package tcc.tcc.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import tcc.tcc.R;

public class MainActivity extends AppCompatActivity {
    private TextView textViewSalutation;
    private Button btnConverter;
    private Button btnMockSpeak;
    private Button btnMockMyAudios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Home");

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        textViewSalutation = (TextView) findViewById(R.id.textViewSalutation) ;
        btnConverter = (Button) findViewById(R.id.btn_converter);
        btnMockSpeak = (Button) findViewById(R.id.button_mock);
        btnMockMyAudios = (Button) findViewById(R.id.btnMockMeusAB);

        textViewSalutation.setText(getHour());

        btnConverter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent converter = new Intent(MainActivity.this, ConverterActivity.class);
                startActivity(converter);
            }
        });

        btnMockSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent converter = new Intent(MainActivity.this, SpeakActivity.class);
                converter.putExtra("filePath", "/storage/emulated/0/TCC_NAME/MyAudioBooks/java-ee-aproveite-toda-a-plataforma-para-construir-aplicacoes.pdf.txt");
                converter.putExtra("file_name", "java-ee-aproveite-toda-a-plataforma-para-construir-aplicacoes.pdf");
                startActivity(converter);
            }
        });

        btnMockMyAudios.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myAudioBooks = new Intent(MainActivity.this, FoldersAudioBooksActivity.class);
                startActivity(myAudioBooks);
            }
        });
    }

    public String getHour(){
        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        if (hora >= 1 && hora <= 12){
            return "Bom dia!";
        }else if(hora >= 12 && hora <= 18){
            return "Boa tarde!";
        }else if(hora >= 18 && hora <= 24){
            return "Boa noite!";
        }
        return "Olá!";
    }
}
