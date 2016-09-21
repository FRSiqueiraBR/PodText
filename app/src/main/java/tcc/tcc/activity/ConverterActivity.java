package tcc.tcc.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tcc.tcc.R;
import tcc.tcc.model.FileItem;
import tcc.tcc.util.ExtractText;

public class ConverterActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ArrayList<FileItem> listFileItems = new ArrayList<>();
    private ExtractText extractText = new ExtractText();

    private ListView listViewFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        List<FileItem> listFile = listFiles();
        TextView txtViewFileNotFound = (TextView) findViewById(R.id.files_not_found);

        listViewFiles = (ListView) findViewById(R.id.file_list);

        ArrayAdapter<FileItem> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listFile);

        listViewFiles.setAdapter(adapter);
        listViewFiles.setOnItemClickListener(this);

        if (listFile.isEmpty()) {
            txtViewFileNotFound.setText("Nenhum Arquivo foi encontrado.");
        }

    }

    private List<FileItem> listFiles() {
        File directory = new File(findDirectory());
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    FileItem file = new FileItem();
                    file.setName(f.getName());
                    listFileItems.add(file);
                }
            }
        }
        return listFileItems;
    }

    private String findDirectory() {
        File root = android.os.Environment.getExternalStorageDirectory();
        return root.toString();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final String[] text = {new String()};

        final FileItem fileItemSelected = (FileItem) listViewFiles.getItemAtPosition(i);

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Convertendo PDF para texto.\nIsso pode demorar um pouco, por favor aguarde...");
        progress.show();

        final Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                text[0] = readFile(fileItemSelected.getName());
                chanceActivity(text[0]);//TODO
            }
        };
        thread.start();

    }

    /**
     * FAZ A LEITURA DO ARQUIVO,
     * UTILIZA O ITEXT CASO O ARQUIVO SEJA UM PDF
     */
    public String readFile(String fileName) {
        if (fileName != null && !fileName.isEmpty()) {
            File file = new File(android.os.Environment.getExternalStorageDirectory(), fileName);

            if (isPDF(file)) {
                try {
                    return extractText.parsePdf(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                return extractText.extractTXT(file);
            }
        }
        return "Não foi possível ler o texto";
    }

    public boolean isPDF(File file) {
        return file.getName() != null && file.getName().substring(file.getName().length() - 4, file.getName().length()).equals(".pdf");
    }

    private void chanceActivity(String text) {
        Intent editTextActivity = new Intent(ConverterActivity.this, EditTextActivity.class);
        editTextActivity.putExtra("text", text);
        startActivity(editTextActivity);
    }
}
