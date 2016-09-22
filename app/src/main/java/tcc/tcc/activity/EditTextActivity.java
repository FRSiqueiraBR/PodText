package tcc.tcc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import tcc.tcc.R;

public class EditTextActivity extends AppCompatActivity {
    private EditText edtText;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        Button btnConverterFile = (Button) findViewById(R.id.btn_converter);
        edtText = (EditText) findViewById(R.id.edt_text);

        Intent intent = getIntent();
        text = intent.getStringExtra("text");
        edtText.setText(text);

        btnConverterFile.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent speakActivity = new Intent(EditTextActivity.this, SpeakActivity.class);
                String text = String.valueOf(edtText.getText());
                speakActivity.putExtra("text", text);

                startActivity(speakActivity);

            }
        });
    }
}
