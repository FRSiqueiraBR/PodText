package tcc.tcc.task;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import tcc.tcc.activity.EditTextActivity;
import tcc.tcc.model.ConverterTaskModel;
import tcc.tcc.util.ExtractText;

public class ConverterTask extends AsyncTask<String, Float, ConverterTaskModel> {
    private Activity activity;
    private ExtractText extractText = new ExtractText();
    private ConverterTaskModel converterTaskModel = new ConverterTaskModel();

    public ConverterTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected ConverterTaskModel doInBackground(String... fileName) {
        final String filePath;
        File path;
        String text;

        text = readFile(fileName);
        path = createDirectory();
        filePath = createFile(path, text, fileName[0]);

        converterTaskModel.setFilePath(filePath);
        return converterTaskModel;
    }

    @Override
    protected void onPostExecute(ConverterTaskModel converterTaskModel) {
        Intent editTextActivity = new Intent(activity, EditTextActivity.class);
        editTextActivity.putExtra("file_path", converterTaskModel.getFilePath());
        editTextActivity.putExtra("file_name", converterTaskModel.getFileName());
        editTextActivity.putExtra("number_pages", converterTaskModel.getNumberOfPages());
        editTextActivity.putExtra("extension", converterTaskModel.getExt());
        editTextActivity.putExtra("size", converterTaskModel.getSize());

        activity.startActivity(editTextActivity);
    }

    /**
     * FAZ A LEITURA DO ARQUIVO,
     * UTILIZA O ITEXT CASO O ARQUIVO SEJA UM PDF
     */
    public String readFile(String[] name) {
        String fileName = name[0];

        if (fileName != null && !fileName.isEmpty()) {
            File file = new File(Environment.getExternalStorageDirectory(), fileName);

            if (isPDF(file)) {
                try {
                    String text = extractText.parsePdf(file);

                    this.converterTaskModel.setFileName(extractText.getFileName());
                    this.converterTaskModel.setNumberOfPages(String.valueOf(extractText.getNumberOfPages()));
                    this.converterTaskModel.setExt(getExt(file));
                    this.converterTaskModel.setSize(String.valueOf(extractText.getSize()));

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

    public File createDirectory() {
        File path = Environment.getExternalStorageDirectory().getAbsoluteFile();
        File dir = new File(path, "PodText" + File.separator + "MyAudioBooks");//TODO
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public String createFile(File path, String text, String fileName) {
        try {
            File fileExt = new File(path, fileName + ".txt");
            fileExt.getParentFile().mkdirs();
            FileOutputStream fosExt = new FileOutputStream(fileExt);
            fosExt.write(text.getBytes());
            fosExt.close();

            return fileExt.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
