package tcc.tcc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tcc.tcc.R;
import tcc.tcc.model.AudioBook;

public class MyAudioBooksActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private String path;
    private ListView listViewAudioBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_audio_books);

        getSupportActionBar().setTitle("Meus Audiobooks");

        listViewAudioBooks = (ListView) findViewById(R.id.list_view_audio_book);

        Intent intent = getIntent();
        path = intent.getStringExtra("path");

        List<AudioBook> audioBookList = listAudioBooks();

        ArrayAdapter<AudioBook> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, audioBookList);

        listViewAudioBooks.setAdapter(adapter);
        listViewAudioBooks.setOnItemClickListener(this);
    }

    private List<AudioBook> listAudioBooks() {
        return listFiles();
    }

    private List<AudioBook> listFiles() {
        File directory;
        File[] files;
        List<AudioBook> audioBookList = new ArrayList<>();

        directory = new File(path);
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}