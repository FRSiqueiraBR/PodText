package tcc.tcc.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import tcc.tcc.R;
import tcc.tcc.activity.EditTextActivity;
import tcc.tcc.activity.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by FRSiqueira on 25/10/2016.
 */

public class SynthesizeToFileService {
    private TextToSpeech ts;
    private String text;
    private String fileName;
    private List<File> listFiles = new ArrayList<>();
    private Context applicationContext;
    private int partCompleted;

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
                            Log.d("TTS: ", "Completado com sucesso");
                            createNotification(partCompleted);
                            partCompleted++;
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

    public void createNotification(final int partCompleted){
        String notificationText = "A parte " + partCompleted + " do seu livro " + fileName + " já terminou.";

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.alert)
                        .setContentTitle("PodText")
                        .setContentText(notificationText);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) applicationContext.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(123456, mBuilder.build());
    }

    public Context getApplicationContext() {
        return applicationContext;
    }
}
