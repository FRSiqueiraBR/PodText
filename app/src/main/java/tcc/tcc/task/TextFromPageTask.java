package tcc.tcc.task;

import android.os.AsyncTask;
import android.util.Log;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

/**
 * Created by FRSiqueira on 22/09/2016.
 */

public class TextFromPageTask extends AsyncTask<String, Float, String> {

    private int firstPage;
    private int lastPage;
    private PdfReader reader;


    public TextFromPageTask(int firstPage, int lastPage, PdfReader reader){
        super();
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        this.reader = reader;
    }


    @Override
    protected String doInBackground(String... params) {
        StringBuilder sb = new StringBuilder();
        try{
            for (int i = firstPage; firstPage <= lastPage; i++) {
                sb.append(PdfTextExtractor.getTextFromPage(reader, i));
                Log.d("Página", String.valueOf(i));
            }

        } catch(Exception e){
            Log.e("ERROR", e.getMessage());
        }
        return sb.toString();
    }
}
