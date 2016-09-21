package tcc.tcc.task;

import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;

public class ConverterTask extends AsyncTask<String, Float, String> {
    private ProgressBar progressBar;
    private TextView textView;

    private String fileName;
    private Float page = 0f;
    private Float numberOfPages = 0f;
    private Float progress = 0f;


    public ConverterTask(ProgressBar progressBar, TextView textView, String fileName) {
        this.progressBar = progressBar;
        this.textView = textView;
        this.fileName = fileName;
    }

    @Override
    protected void onPreExecute() {
        textView.setText("0%");
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        if (fileName != null && !fileName.isEmpty()) {
            File file = new File(android.os.Environment.getExternalStorageDirectory(), fileName);

            if (isPDF(file)) {
                try {
                    parsePdf(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Float... values) {
        Float value = values[0];
        Float result = (value / numberOfPages) * 100;
        if (result.intValue() > progress.intValue()){
            progress=result;
            progressBar.incrementProgressBy(1);
        }
        textView.setText("Convertendo páginas: (" + page.intValue() + "/" + numberOfPages.intValue() + ")...");
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String text) {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        textView.setText("Tarefa concluída");
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
    }

    public String parsePdf(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        PdfReader reader = new PdfReader(file.getPath());
        numberOfPages = (float) reader.getNumberOfPages();

        for (int i = 1; i <= numberOfPages; i++) {
            page = (float) i;
            publishProgress((float)i);

            sb.append(PdfTextExtractor.getTextFromPage(reader, i));
            Log.d("Pagina:", String.valueOf(i));
        }

        reader.close();
        return sb.toString();
    }

    public boolean isPDF(File file) {
        return file.getName().substring(file.getName().length() - 4, file.getName().length()).equals(".pdf");
    }

}
