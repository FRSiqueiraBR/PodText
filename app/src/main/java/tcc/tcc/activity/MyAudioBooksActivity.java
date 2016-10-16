package tcc.tcc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tcc.tcc.R;
import tcc.tcc.model.AudioBook;
import tcc.tcc.model.FileItem;

public class MyAudioBooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_audio_books);

        getSupportActionBar().setTitle("Meus Audiobooks");

        //List<AudioBook> audioBookList = listAudioBooks();
    }

    private List<AudioBook> listAudioBooks(){
        File file = (File) listFiles();
        return null;

    }

    private String findDirectory() {
        File root = android.os.Environment.getExternalStorageDirectory();
        File audioBooksDirectory = root;
        return root.toString();
    }

    private List<AudioBook> listFiles() {
        File directory;
        File[] files;
        List<AudioBook> audioBookList = new ArrayList<>();

        directory = new File(findDirectory());
        files = directory.listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    AudioBook audioBook = new AudioBook();
                    audioBook.setName(getName(f));
                    audioBook.setPart(getPart(f));

                    audioBookList.add(audioBook);
                }
            }
        }
        return audioBookList;
    }

    private String getName(File file){
        if (file != null){
            return file.getName().substring(0, file.getName().length() - 1);
        }
        return new String();
    }

    private String getPart(File file){
        if (file != null){
            return file.getName().substring(file.getName().length() - 1, file.getName().length());
        }
        return new String();
    }



}
