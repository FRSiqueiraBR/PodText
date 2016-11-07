package tcc.tcc.service;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by FRSiqueira on 25/10/2016.
 */

public class SynthesizeToFileService {
    private TextToSpeech ts;
    private String text;
    private String fileName;
    private List<File> listFiles = new ArrayList<>();
    private Context applicationContext;

    public SynthesizeToFileService(Context applicationContext, String text, String fileName) {
        this.applicationContext = applicationContext;
        this.text = text;
        this.fileName = fileName;

        initTextToSpeech();
    }

    /**
     * Alterar esse metedo caso for
     * incluir novas línguas ou  a velocidade da fala
     */
    private void initTextToSpeech() {
        ts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    Locale localeBR = new Locale("pt", "br");
                    ts.setLanguage(localeBR);
                    ts.setSpeechRate(Float.valueOf("0.85"));
                    ts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {
                            Log.d("TTS: ", "Iniciado com sucesso");
                        }

                        @Override
                        public void onDone(String utteranceId) {
                            Log.d("TTS: ", "Concluído");
                        }

                        @Override
                        public void onError(String utteranceId) {
                            Log.d("TTS: ", "Ocorreu um erro");
                        }
                    });
                    synthesize();
                }
            }
        });
    }

    public void synthesize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(this.text);
        } else {
            ttsUnder20(this.text);
        }
    }

    /**
     * Metodo caso a version android for
     * maior igual a Lollipop
     *
     * @param text
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        String utteranceId = this.hashCode() + "";
        List<String> listText = divideText(text);

        for (int i = 0; i < listText.size(); i++) {
            Log.i("Sintetizando", i + " de " + listText.size());
            ts.synthesizeToFile(listText.get(i), null, listFiles.get(i), utteranceId);
        }
    }

    /**
     * Metodo caso a version android for
     * maior igual a Lollipop
     *
     * @param text
     */
    @Deprecated
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21Old(String text) {
        String utteranceId = this.hashCode() + "";
        String paragraph[] = parseData(text);

        for (int i = 0; i < paragraph.length; i++) {
            //ts.speak(paragraph[i], TextToSpeech.QUEUE_FLUSH, null, utteranceId);
            File file = new File("/storage/emulated/0/TCC_NAME/MyAudioBooks/Teste.wav");
            ts.synthesizeToFile(paragraph[i], null, file, utteranceId);
            while (ts.isSpeaking()) {

            }
        }
    }

    /**
     * Metodo caso a version android for
     * maior igual a Lollipop
     *
     * @param text
     */
    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        ts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    public List<String> divideText(String text) {
        List<String> listString = new ArrayList<>();

        if (text.length() > 3000) {
            int parts = text.length() / 3000;

            for (int i = 0; i <= parts; i++) {
                int length = text.length();
                String part;
                if (i == 0) {
                    part = text.substring(0, 3000);
                } else if (i == 1) {
                    part = text.substring(3000, 6000);
                } else if (i == parts) {
                    part = text.substring(3000 * i, length);
                } else {
                    part = text.substring(3000 * i, 3000 * (i + 1));
                }
                listString.add(part);
                File file = createDirectory();
                createFile(file, fileName, i);
            }
        }
        return listString;
    }

    public File createDirectory() {
        File path = Environment.getExternalStorageDirectory().getAbsoluteFile();
        File dir = new File(path, "Xenoma" + File.separator + "MyAudioBooks" + File.separator + fileName.substring(0, fileName.length() - 4));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public File createFile(File path, String fileName, int part) {
        File fileExt = new File(path, fileName.substring(0, fileName.length() - 4) + "(" + part + ")" + ".wav");
        fileExt.getParentFile().mkdirs();
        listFiles.add(fileExt);

        return fileExt;
    }

    /**
     * Remove '/n' do texto convertido pelo iText
     * <p>
     * Retorna um array de paragraphs separados
     *
     * @param data
     * @return
     */
    private String[] parseData(String data) {
        String regex = "[\\r\\n]+";
        return data.split(regex);
    }

    public Context getApplicationContext() {
        return applicationContext;
    }
}
