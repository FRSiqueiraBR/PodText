package tcc.tcc;

import java.io.File;
import java.io.IOException;

import tcc.tcc.util.ExtractText;

/**
 * Created by felip on 16/09/2016.
 */
public class ConvertFileToString {

    /**
     * FAZ A LEITURA DO ARQUIVO,
     * UTILIZA O ITEXT CASO O ARQUIVO SEJA UM PDF
     */
    public String convert(String fileName){
        if (fileName != null && !fileName.isEmpty()) {
            File file = new File(android.os.Environment.getExternalStorageDirectory(), fileName);

            if (isPDF(file)) {
                try {
                    return new ExtractText().parsePdf(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                return new ExtractText().extractTXT(file);
            }
        }
        return "Não foi possível ler o texto";
    }

    public boolean isPDF(File file) {
        return file.getName() != null && file.getName().substring(file.getName().length() - 4, file.getName().length()).equals(".pdf");
    }

}
