package tcc.tcc.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import tcc.tcc.R;
import tcc.tcc.model.FileItem;
import tcc.tcc.task.ConverterTask;
import tcc.tcc.util.ExtractText;

public class ConverterActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String fileName;
    private String size;
    private String numberOfPages;
    private String ext;
    private String[] text;

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
        try {
            final FileItem fileItemSelected = (FileItem) listViewFiles.getItemAtPosition(i);

            ProgressDialog progress = new ProgressDialog(ConverterActivity.this);
            progress.setMessage("Convertendo Arquivo para texto.\nIsso pode demorar um pouco, por favor aguarde...");
            progress.show();

            ConverterTask asyncTask = new ConverterTask(this);
            asyncTask.execute(fileItemSelected.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void thread() {
        //final Thread thread = new Thread() {
        //    @Override
        //  public void run() {
        //      super.run();
        //      text[0] = readFile(fileItemSelected.getName());
        ////      File path = createDirectory();
        //    final String filePath = createFile(path);
        //      chanceActivity(filePath);//TODO
        //  }
        //};
        //thread.start();
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
                    String text = extractText.parsePdf(file);

                    this.fileName = extractText.getFileName();
                    this.numberOfPages = String.valueOf(extractText.getNumberOfPages());
                    this.ext = getExt(file);
                    this.size = String.valueOf(extractText.getSize());

                    return text;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                return extractText.extractTXT(file);
            }
        }
        return "Não foi possível ler o texto";
    }

    private boolean isPDF(File file) {
        return getExt(file).equals(".pdf");
    }

    private String getExt(File file) {
        if (file.getName() != null && !file.getName().isEmpty()) {
            return file.getName().substring(file.getName().length() - 4, file.getName().length());
        } else {
            return new String();
        }

    }

    private void chanceActivity(String filePath) {
        Intent editTextActivity = new Intent(ConverterActivity.this, EditTextActivity.class);
        editTextActivity.putExtra("file_path", filePath);
        editTextActivity.putExtra("file_name", fileName);
        editTextActivity.putExtra("number_pages", numberOfPages);
        editTextActivity.putExtra("extension", ext);
        editTextActivity.putExtra("size", size);
        startActivity(editTextActivity);
    }

    public File createDirectory() {
        File path = Environment.getExternalStorageDirectory().getAbsoluteFile();
        File dir = new File(path, "TCC_NAME" + File.separator + "MyAudioBooks");//TODO
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public String createFile(File path) {
        try {
            File fileExt = new File(path, fileName + ".txt");

            //Cria o arquivo
            fileExt.getParentFile().mkdirs();

            //Abre o arquivo
            FileOutputStream fosExt = new FileOutputStream(fileExt);

            //Escreve no arquivo
            fosExt.write(text[0].getBytes());

            //Obrigatoriamente você precisa fechar
            fosExt.close();

            return fileExt.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
