package tcc.tcc.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;

import tcc.tcc.R;

public class EditTextActivity extends AppCompatActivity {
    private String text;
    String filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        getSupportActionBar().setTitle("Informações");

        Button btnConverterFile = (Button) findViewById(R.id.btn_converter);
        Button btnEditText = (Button) findViewById(R.id.btnEditText);
        final TextView fileName = (TextView) findViewById(R.id.txtViewFileName);
        TextView numberPages = (TextView) findViewById(R.id.txtViewNumberPages);
        TextView size = (TextView) findViewById(R.id.txtViewSize);
        TextView extension = (TextView) findViewById(R.id.txtViewExtension);

        Intent intent = getIntent();
        filePath = intent.getStringExtra("file_path");
        text = loadFile(filePath);

        fileName.setText(intent.getStringExtra("file_name"));
        numberPages.setText(intent.getStringExtra("number_pages"));
        extension.setText(intent.getStringExtra("extension"));
        size.setText(intent.getStringExtra("size"));

        btnConverterFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = loadFile(filePath);
                Intent speakActivity = new Intent(EditTextActivity.this, SpeakActivity.class);
                speakActivity.putExtra("text", text);
                speakActivity.putExtra("filePath", filePath);
                speakActivity.putExtra("file_name", fileName.getText());

                startActivity(speakActivity);
            }
        });

        btnEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    Uri uri = Uri.parse("file:" + filePath);
                    intent.setDataAndType(uri, "text/plain");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Log.d("Erro", e.getMessage());
                }
            }
        });
    }

    public String loadFile(String path) {
        try {
            String line;
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                line = line.replace(".", "\n");
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
