package tcc.tcc.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tcc.tcc.R;

public class MainActivity extends AppCompatActivity {
    private Button btnConverter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConverter = (Button) findViewById(R.id.btn_converter);

        btnConverter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent converter = new Intent(MainActivity.this, ConverterActivity.class);
                startActivity(converter);
            }
        });
    }
}
