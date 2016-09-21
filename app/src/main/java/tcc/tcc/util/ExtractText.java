package tcc.tcc.util;


import android.util.Log;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ExtractText {

    private String parsedText = null;
    private int page;

    public String extractTXT(File file) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();

            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }
            parsedText = sb.toString();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parsedText;
    }

    public String parsePdf(File file) throws IOException {
        StringBuilder sb    = new StringBuilder();
        PdfReader reader    = new PdfReader(file.getPath());

        for (page = 1; page <= reader.getNumberOfPages(); page++) {
            sb.append(PdfTextExtractor.getTextFromPage(reader, page));
            Log.d("Pagina:", String.valueOf(page));
        }

        reader.close();
        return sb.toString();
    }

}

