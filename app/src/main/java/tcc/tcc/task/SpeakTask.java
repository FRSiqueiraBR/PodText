package tcc.tcc.task;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.speech.tts.TextToSpeech;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by FRSiqueira on 23/09/2016.
 */

public class SpeakTask extends AsyncTask<String, Float, String> {
    private TextToSpeech ts;
    private Context context;
    private String textSpeaking;
    private String text;
    private File path;
    private String filePath;

    public SpeakTask(Context context, String text) {
        this.context = context;
        this.text = text;
    }


    @Override
    protected String doInBackground(String... params) {
        initTextToSpeech();
        speak(text);

        return textSpeaking;
    }

    /**
     * TODO
     * Alterar esse metedo caso for
     * incluir novas línguas ou  a velocidade da fala
     */
    private void initTextToSpeech() {
        ts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    Locale localeBR = new Locale("pt", "br");
                    ts.setLanguage(localeBR);
                    ts.setSpeechRate(Float.valueOf("0.85"));
                }
            }
        });
    }

    private void speak(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(text);
        } else {
            ttsUnder20(text);
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
        final String paragraph[] = parseData(text);

        for (int i = 0; i < paragraph.length; i++) {
            ts.speak(paragraph[i], TextToSpeech.QUEUE_FLUSH, null, utteranceId);
            //ts.synthesizeToFile(paragraph[i], null, file, utteranceId);
            textSpeaking = paragraph[i];

            while (ts.isSpeaking()) {
                //TODO buscar um jeito melhor de se fazer
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

    private String[] parseData(String data) {
        String regex = "[\\r\\n]+";
        return data.split(regex);
    }
}
