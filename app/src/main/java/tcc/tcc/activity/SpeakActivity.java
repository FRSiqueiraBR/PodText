package tcc.tcc.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import tcc.tcc.R;

public class SpeakActivity extends AppCompatActivity {
    private TextToSpeech ts;
    private String text;
    private String fileName;
    private List<File> listFiles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speak);

        TextView txtTextSpeaking = (TextView) findViewById(R.id.textSpeaking);
        Button btnPlay = (Button) findViewById(R.id.btn_play);

        initTextToSpeech();

        Intent intent = getIntent();
        //text = intent.getStringExtra("text");
        text = loadFile(intent.getStringExtra("filePath"));
        fileName = intent.getStringExtra("file_name");


        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak(text);
            }
        });

    }

    /**
     * TODO
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
                }
            }
        });
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

    private void speak(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //ttsGreater21(text);
            //SpeakTask speak = new SpeakTask(getApplicationContext(), text);
            //speak.execute();
            ttsGreater2(text);
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
        String paragraph[] = parseData(text);

        for (int i = 0; i < paragraph.length; i++) {
            //ts.speak(paragraph[i], TextToSpeech.QUEUE_FLUSH, null, utteranceId);
            File file = new File("/storage/emulated/0/TCC_NAME/MyAudioBooks/Teste.wav");
            ts.synthesizeToFile(paragraph[i], null, file, utteranceId);
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
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater2(String text) {
        String utteranceId = this.hashCode() + "";
        List<String> listText = divideText(text);

        for (int i = 0; i < listText.size(); i++) {
            //ts.speak(paragraph[i], TextToSpeech.QUEUE_FLUSH, null, utteranceId);
            //File file = new File("/storage/emulated/0/TCC_NAME/MyAudioBooks/Teste.wav");
            Log.i("adasd", listText.get(i).length() + "");
            ts.synthesizeToFile(listText.get(i), null, listFiles.get(i), utteranceId);
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

    public String loadFile(String path) {
        try {
            String line;
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                line = line.replace(".", "\n");
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public List<String> divideText(String text) {
        List<String> listString = new ArrayList<>();

        if (text.length() > 3000) {
            int parts = text.length() / 3000;

            for (int i = 0; i <= parts; i++) {
                Log.i("linha", i + "");
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
        File dir = new File(path, "TCC_NAME" + File.separator + "MyAudioBooks");//TODO
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public File createFile(File path, String fileName, int part) {
        File fileExt = new File(path, fileName + part + ".wav");
        fileExt.getParentFile().mkdirs();
        listFiles.add(fileExt);

        return fileExt;
    }

}
