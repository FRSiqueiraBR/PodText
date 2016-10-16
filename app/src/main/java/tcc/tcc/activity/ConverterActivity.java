package tcc.tcc.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tcc.tcc.R;
import tcc.tcc.model.FileItem;
import tcc.tcc.task.ConverterTask;

public class ConverterActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<FileItem> listFileItems = new ArrayList<>();
    private ProgressDialog progress;

    private ListView listViewFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        getSupportActionBar().setTitle("Selecione os Arquivos");

        List<FileItem> listFile = listFiles();
        TextView txtViewFileNotFound = (TextView) findViewById(R.id.files_not_found);

        listViewFiles = (ListView) findViewById(R.id.file_list);

        ArrayAdapter<FileItem> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listFile);

        listViewFiles.setAdapter(adapter);
        listViewFiles.setOnItemClickListener(this);

        progress = new ProgressDialog(this);

        if (listFile.isEmpty()) {
            txtViewFileNotFound.setText("Nenhum Arquivo foi encontrado.");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        progress.cancel();
    }

    private List<FileItem> listFiles() {
        File directory = new File(findDirectory());
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile() && isPDF(f)) {
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
        try {
            final FileItem fileItemSelected = (FileItem) listViewFiles.getItemAtPosition(i);

            progress.setMessage("Convertendo Arquivo para texto.\nIsso pode demorar um pouco, por favor aguarde...");
            progress.setCancelable(false);
            progress.show();

            ConverterTask asyncTask = new ConverterTask(this);
            asyncTask.execute(fileItemSelected.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isPDF(File file) {
        return file.getName() != null && file.getName().substring(file.getName().length() - 4, file.getName().length()).equals(".pdf");
    }
}
