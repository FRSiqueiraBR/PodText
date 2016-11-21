package tcc.tcc.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tcc.tcc.R;
import tcc.tcc.Adapter.FoldersAdapter;
import tcc.tcc.model.Folders;

public class MyFoldersAudioBooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_folders_audio_books);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

        List<Folders> folderList = listFolders();

        FoldersAdapter foldersAdapter = new FoldersAdapter(folderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(foldersAdapter);

    }

    private List<Folders> listFolders() {
        File directory = new File(findDirectory());
        List<Folders> foldersList = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    Folders folder = new Folders();
                    folder.setName(f.getName());
                    folder.setPath(f.getPath());
                    foldersList.add(folder);
                }
            }
        }
        return foldersList;
    }

    private String findDirectory() {
        File root = new File(android.os.Environment.getExternalStorageDirectory() + File.separator + "PodText" + File.separator + "MyAudioBooks");
        return root.toString();
    }
}
