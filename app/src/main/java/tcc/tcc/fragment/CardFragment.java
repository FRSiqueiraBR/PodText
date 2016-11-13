package tcc.tcc.fragment;

/**
 * Created by FRSiqueira on 03/11/2016.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tcc.tcc.R;
import tcc.tcc.activity.ConverterActivity;
import tcc.tcc.activity.FoldersAudioBooksActivity;
import tcc.tcc.activity.MyFoldersAudioBooksActivity;
import tcc.tcc.model.MenuItem;


public class CardFragment extends Fragment {

    ArrayList<MenuItem> listItens = new ArrayList<>();
    RecyclerView MyRecyclerView;
    String name[] = {"MEUS AUDIO LIVROS", "CONVERTER MEUS LIVROS", "SOBRE NÓS", "FALE CONOSCO"};
    int images[] = {R.drawable.books, R.drawable.livro_e_fone, R.drawable.books_2, R.drawable.doug };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_card, container, false);
        MyRecyclerView = (RecyclerView) view.findViewById(R.id.cardView);
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (listItens.size() > 0 & MyRecyclerView != null) {
            MyRecyclerView.setAdapter(new MyAdapter(listItens));
        }
        MyRecyclerView.setLayoutManager(MyLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<MenuItem> list;

        public MyAdapter(ArrayList<MenuItem> Data) {
            list = Data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_items, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.titleTextView.setText(list.get(position).getName());
            holder.coverImageView.setImageResource(list.get(position).getImageResourceId());
        }
        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public ImageView coverImageView;
        public TextView option1;
        public TextView option2;

        public MyViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.titleTextView);
            coverImageView = (ImageView) v.findViewById(R.id.coverImageView);
            option1 = (TextView) v.findViewById(R.id.txt_option_1);
            option2 = (TextView) v.findViewById(R.id.txt_option_2);

            coverImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (titleTextView.getText().equals("MEUS AUDIO LIVROS")){
                        Intent myAudioBooks = new Intent(getActivity(), FoldersAudioBooksActivity.class);
                        startActivity(myAudioBooks);
                    }

                    if (titleTextView.getText().equals("CONVERTER MEUS LIVROS")){
                        Intent converter = new Intent(getActivity(), ConverterActivity.class);
                        startActivity(converter);
                    }
                }
            });

            option1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    if (titleTextView.getText().equals("MEUS AUDIO LIVROS")){
                        alertDialog.setTitle(titleTextView.getText());
                        alertDialog.setMessage("Aqui você tem uma lista de todos os seus Audio Livros convertidos e dividos por partes e você pode reproduzi-las.");
                    }

                    if (titleTextView.getText().equals("CONVERTER MEUS LIVROS")){
                        alertDialog.setTitle(titleTextView.getText());
                        alertDialog.setMessage("Aqui você tem uma lista dos seus PDF's que poderá converter para um Audio Livro.");
                    }

                    alertDialog.setIcon(R.drawable.alert);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            });

            option2.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if (titleTextView.getText().equals("MEUS AUDIO LIVROS")){
                        Intent myAudioBooks = new Intent(getActivity(), MyFoldersAudioBooksActivity.class);
                        startActivity(myAudioBooks);
                    }

                    if (titleTextView.getText().equals("CONVERTER MEUS LIVROS")){
                        Intent converter = new Intent(getActivity(), ConverterActivity.class);
                        startActivity(converter);
                    }
                }
            });
        }
    }

    public void initializeList() {
        listItens.clear();

        MenuItem item = new MenuItem();
        item.setName(name[0]);
        item.setImageResourceId(images[2]);
        item.setOption1("Ver");
        item.setOption2("Saiba Mais");
        listItens.add(item);

        MenuItem item2 = new MenuItem();
        item2.setName(name[1]);
        item2.setImageResourceId(images[1]);
        item2.setOption1("Ver");
        item2.setOption2("Saiba Mais");
        listItens.add(item2);

        MenuItem item3 = new MenuItem();
        item3.setName(name[2]);
        item3.setImageResourceId(images[3]);
        item3.setOption1("Ver");
        item3.setOption2("Saiba Mais");
        listItens.add(item3);

        MenuItem item4 = new MenuItem();
        item4.setName(name[3]);
        item4.setImageResourceId(images[0]);
        item4.setOption1("Ver");
        item4.setOption2("Saiba Mais");
        listItens.add(item4);

    }
}