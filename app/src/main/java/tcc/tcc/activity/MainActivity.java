package tcc.tcc.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tcc.tcc.R;

public class MainActivity extends AppCompatActivity {
    private Button btnConverter;
    private Button btnMockSpeak;
    private Button btnMockMyAudios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        btnConverter = (Button) findViewById(R.id.btn_converter);
        btnMockSpeak = (Button) findViewById(R.id.button_mock);
        btnMockMyAudios = (Button) findViewById(R.id.btnMockMeusAB);

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

            }
        });
    }
}
