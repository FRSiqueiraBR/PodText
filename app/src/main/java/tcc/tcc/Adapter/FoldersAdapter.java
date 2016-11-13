package tcc.tcc.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tcc.tcc.R;
import tcc.tcc.model.Folders;

/**
 * Created by FRSiqueira on 07/11/2016.
 */

public class FoldersAdapter extends RecyclerView.Adapter<FoldersAdapter.MyViewHolder> {

    List<Folders> foldersList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_folder);
        }
    }

    public FoldersAdapter(List<Folders> foldersList) {
        this.foldersList = foldersList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.folder_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Folders folder = foldersList.get(position);
        holder.title.setText((CharSequence) folder.getName());

    }

    @Override
    public int getItemCount() {
        return this.foldersList.size();
    }
}
