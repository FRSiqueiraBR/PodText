package tcc.tcc.thread;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by FRSiqueira on 27/09/2016.
 */

public class Synthesize implements Runnable {

    private Context context;
    private String chunk;
    private File file;

    private TextToSpeech ts;

    public Synthesize(Context context, String chunk, File file, TextToSpeech ts) {
        this.context = context;
        this.chunk = chunk;
        this.file = file;
        this.ts = ts;
    }

    @Override
    public void run() {
        speak(chunk);
    }

    private void speak(String chunk) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(chunk);
        } else {
            ttsUnder20(chunk);
        }
    }

    /**
     * Metodo caso a version android for
     * maior igual a Lollipop
     *
     * @param chunk
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String chunk) {
        Log.d("Chunk", file.getName());

        ts.synthesizeToFile(chunk, null, file, String.valueOf(this.hashCode()));
    }

    /**
     * Metodo caso a version android for
     * maior igual a Lollipop
     *
     * @param chunk
     */
    private void ttsUnder20(String chunk) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        ts.synthesizeToFile(chunk, map, file.getName());
    }
}
