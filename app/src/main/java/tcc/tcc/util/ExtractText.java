package tcc.tcc.util;


import android.util.Log;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tcc.tcc.task.TextFromPageTask;
import tcc.tcc.thread.ConvertToText;

public class ExtractText {

    private String parsedText = null;
    private String fileName;
    private int numberOfPages;
    private long size;

    private ConvertToText convertToText1;
    private ConvertToText convertToText2;
    private ConvertToText convertToText3;
    private ConvertToText convertToText4;
    private ConvertToText convertToText5;
    private ConvertToText convertToText6;
    private ConvertToText convertToText7;
    private ConvertToText convertToText8;
    private ConvertToText convertToText9;
    private ConvertToText convertToText10;

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
        StringBuilder sb;
        final PdfReader reader = new PdfReader(file.getPath());

        convertToText1 = new ConvertToText(file);
        convertToText2 = new ConvertToText(file);
        convertToText3 = new ConvertToText(file);
        convertToText4 = new ConvertToText(file);
        convertToText5 = new ConvertToText(file);
        convertToText6 = new ConvertToText(file);
        convertToText7 = new ConvertToText(file);
        convertToText8 = new ConvertToText(file);
        convertToText9 = new ConvertToText(file);
        convertToText10 = new ConvertToText(file);

        numberOfPages = reader.getNumberOfPages();

        if (numberOfPages > 100) {
            sb = largeFiles();
        } else {
            sb = smallFiles(reader);
        }

        fileName = file.getName();
        size = file.length();

        reader.close();
        return sb.toString();
    }

    private StringBuilder largeFiles() {
        int intervalo;
        int intervaloInicial;
        int intervaloAnterior;

        StringBuilder sb = new StringBuilder();
        List<Integer> pages = new ArrayList<>();

        intervaloInicial = numberOfPages / 10;

        for (int i = 0; i<numberOfPages; i++){
            pages.add(i);
        }

        intervalo = intervaloInicial;

        convertToText1.setPages(pages.subList(1, intervalo));
        intervaloAnterior = intervalo + 1;
        intervalo = intervalo+intervaloInicial;
        convertToText2.setPages(pages.subList(intervaloAnterior, intervalo));
        intervaloAnterior = intervalo + 1;
        intervalo = intervalo+intervaloInicial;
        convertToText3.setPages(pages.subList(intervaloAnterior, intervalo));
        intervaloAnterior = intervalo + 1;
        intervalo = intervalo+intervaloInicial;
        convertToText4.setPages(pages.subList(intervaloAnterior, intervalo));
        intervaloAnterior = intervalo + 1;
        intervalo = intervalo+intervaloInicial;
        convertToText5.setPages(pages.subList(intervaloAnterior, intervalo));
        intervaloAnterior = intervalo + 1;
        intervalo = intervalo+intervaloInicial;
        convertToText6.setPages(pages.subList(intervaloAnterior, intervalo));
        intervaloAnterior = intervalo + 1;
        intervalo = intervalo+intervaloInicial;
        convertToText7.setPages(pages.subList(intervaloAnterior, intervalo));
        intervaloAnterior = intervalo + 1;
        intervalo = intervalo+intervaloInicial;
        convertToText8.setPages(pages.subList(intervaloAnterior, intervalo));
        intervaloAnterior = intervalo + 1;
        intervalo = intervalo+intervaloInicial;
        convertToText9.setPages(pages.subList(intervaloAnterior, intervalo));
        intervaloAnterior = intervalo + 1;
        intervalo = intervalo+intervaloInicial;
        convertToText10.setPages(pages.subList(intervaloAnterior, intervalo));

        Thread t1 = new Thread(convertToText1);
        Thread t2 = new Thread(convertToText2);
        Thread t3 = new Thread(convertToText3);
        Thread t4 = new Thread(convertToText4);
        Thread t5 = new Thread(convertToText5);
        Thread t6 = new Thread(convertToText6);
        Thread t7 = new Thread(convertToText7);
        Thread t8 = new Thread(convertToText8);
        Thread t9 = new Thread(convertToText9);
        Thread t10 = new Thread(convertToText10);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            t6.join();
            t7.join();
            t8.join();
            t9.join();
            t10.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sb.append(convertToText1.getFragment());
        sb.append(convertToText2.getFragment());
        sb.append(convertToText3.getFragment());
        sb.append(convertToText4.getFragment());
        sb.append(convertToText5.getFragment());
        sb.append(convertToText6.getFragment());
        sb.append(convertToText7.getFragment());
        sb.append(convertToText8.getFragment());
        sb.append(convertToText9.getFragment());
        sb.append(convertToText10.getFragment());
        Log.i("Tamanho",String.valueOf(sb.length()));
        return sb;
    }

    private StringBuilder smallFiles(PdfReader reader) throws IOException {
        int page;
        StringBuilder sb = new StringBuilder();
        for (page = 1; page <= numberOfPages; page++) {
            sb.append(PdfTextExtractor.getTextFromPage(reader, page));
            Log.d("Página", String.valueOf(page));
        }
        return sb;
    }


    public String getFileName() {
        return fileName;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public long getSize() {
        return size;
    }
}

