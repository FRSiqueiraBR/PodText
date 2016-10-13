package tcc.tcc.thread;

import android.util.Log;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by FRSiqueira on 27/09/2016.
 */

public class ConvertToText implements Runnable {

    private List<Integer> pages;
    private String fragment;
    private File file;

    public ConvertToText(File file) {
        this.file = file;
    }


    @Override
    public void run() {
        try {
            StringBuilder sb = new StringBuilder();
            PdfReader reader = new PdfReader(file.getPath());
            for (Integer page : pages) {
                sb.append(PdfTextExtractor.getTextFromPage(reader, page));
                Log.d("Page", String.valueOf(page));
            }
            fragment = sb.toString();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFragment() {
        return fragment;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }
}
