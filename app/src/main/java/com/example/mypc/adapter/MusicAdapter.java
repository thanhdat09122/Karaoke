package com.example.mypc.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mypc.karaoke.MainActivity;
import com.example.mypc.karaoke.R;
import com.example.mypc.model.Music;


import java.util.List;
import java.util.Locale;

import static com.example.mypc.karaoke.MainActivity.arraySongs;

public class MusicAdapter extends ArrayAdapter<Music> {

    Activity context;
    int resource;
    List<Music> objects;
    public MusicAdapter(@NonNull Activity context, int resource, @NonNull List<Music> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource= resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource,null);
        TextView txtMa   = row.findViewById(R.id.txtMa);
        TextView txtTen  = row.findViewById(R.id.txtTen);
        TextView txtCasi = row.findViewById(R.id.txtCasi);
        ImageButton btnLike    = (ImageButton) row.findViewById(R.id.imgLike);
        ImageButton btnDisLike = (ImageButton) row.findViewById(R.id.imgDislike);

        final Music music = this.objects.get(position);
        txtMa.setText(music.getMa());
        txtTen.setText(music.getTen());
        txtCasi.setText(music.getCasi());
        if(music.isThich())
        {
            btnLike.setVisibility(View.INVISIBLE);
            btnDisLike.setVisibility(View.VISIBLE);
        }
        else
        {
            btnLike.setVisibility(View.VISIBLE);
            btnDisLike.setVisibility(View.INVISIBLE);
        }
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SolveThich(music);
            }
        });

        btnDisLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SolveDisLike(music);
            }
        });

//        Animation animation = AnimationUtils.loadAnimation(this.context,)
//        row.startAnimation(animation);
        return row;
    }

    private void SolveDisLike(Music music) {
        MainActivity.database.delete("ArirangSongList","MABH=?",new String[]{music.getMa()});
    }

    private void SolveThich(Music music) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("YEUTHICH",1);
        MainActivity.database.update("ArirangSongList",
                contentValues,
                "MABH=?",new String[]{music.getMa()});


    }
//    public void filter(String charText) {
//        charText = charText.toLowerCase(Locale.getDefault());
//        objects.clear();
//        if (charText.length() == 0) {
//            objects.addAll(arraySongs);
//        } else {
//            for (Music music : arraySongs) {
//                if (music.getTen().toLowerCase(Locale.getDefault()).contains(charText)) {
//                    objects.add(music);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }

}
