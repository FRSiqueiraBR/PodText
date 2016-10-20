package tcc.tcc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tcc.tcc.R;
import tcc.tcc.model.Folders;

public class FoldersAudioBooksActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listViewFolders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folders_audio_books);

        getSupportActionBar().setTitle("Meus AudioBooks");

        TextView txtViewFileNotFound = (TextView) findViewById(R.id.files_not_found);
        listViewFolders = (ListView) findViewById(R.id.folder_list);

        List<Folders> foldersList = listFolders();

        ArrayAdapter<Folders> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, foldersList);

        listViewFolders.setAdapter(adapter);
        listViewFolders.setOnItemClickListener(this);

        if (foldersList.isEmpty()) {
            txtViewFileNotFound.setText("Desculpe, nenhum AudioBook encontrado :(");
        }

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
        File root = new File(android.os.Environment.getExternalStorageDirectory() + File.separator +  "Xenoma" + File.separator + "MyAudioBooks");
        return root.toString();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Folders folder = (Folders) this.listViewFolders .getItemAtPosition(position);

        Intent myAudioBooksActivity = new Intent(this, MyAudioBooksActivity.class);

        myAudioBooksActivity.putExtra("path" , folder.getPath());
        this.startActivity(myAudioBooksActivity);
    }

}
