package com.example.exercise2_a.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.exercise2_a.EditTeman;
import com.example.exercise2_a.LihatDataTeman;
import com.example.exercise2_a.MainActivity;
import com.example.exercise2_a.R;
import com.example.exercise2_a.database.DBController;
import com.example.exercise2_a.database.Teman;

import java.util.ArrayList;
import java.util.HashMap;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {
    private ArrayList<Teman> listData;
    private Context context;

    public TemanAdapter(ArrayList<Teman> listData) {
        this.listData = listData;
    }

    @Override
    public TemanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View view = layoutInf.inflate(R.layout.row_data_teman,parent,false);
        return new TemanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemanViewHolder holder, int position) {
        String id, nm, tlp;
        DBController db = new DBController(context);

        id = listData.get(position).getId();
        nm = listData.get(position).getNama();
        tlp = listData.get(position).getTelpon();

        holder.tvnama.setTextColor(Color.BLUE);
        holder.tvnama.setTextSize(20);
        holder.tvnama.setText(nm);
        holder.tvtelpon.setTextColor(Color.WHITE);
        holder.tvtelpon.setTextSize(15);
        holder.tvtelpon.setText(tlp);

        TextDrawable drawableRounded = TextDrawable.builder()
                .buildRoundRect(String.valueOf(id), Color.GRAY, 50);
        holder.img.setImageDrawable(drawableRounded);

        //Fungsi untuk edit dan delete data jika di tekan lama pada card
        holder.cardku.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu pm = new PopupMenu(context, holder.cardku);
                pm.inflate(R.menu.popupmenu);
                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            //Popup Menu untuk opsi edit data
                            case R.id.menuEdit:
                                //FUngsi untuk mengedit data
                                Intent i = new Intent(context, EditTeman.class);
                                i.putExtra("id", id);
                                i.putExtra("nama", nm);
                                i.putExtra("telepon", tlp);
                                context.startActivity(i);
                                break;
                                //Popup Menu untuk opsi hapus data
                            case R.id.menuDelete:
                                //Membuat AlertDialog saat ingin menghapus data
                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Apakah Anda ingin menghapus data " +nm+ "?");
                                builder.setCancelable(true);
                                //Membuat AlertDialog "Iya" jika ingin melanjutkan untuk menghapus data
                                builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        //Fungsi untuk menghapus data
                                        HashMap<String,String> qvalues =  new HashMap<>();
                                        qvalues.put("id", id);
                                        db.deleteData(qvalues);
                                        Toast.makeText(context, "Data Dihapus", Toast.LENGTH_SHORT).show();
                                        Intent in = new Intent(context, MainActivity.class);
                                        context.startActivity(in);

                                    }
                                });
                                //Membuat AlertDialog "Tidak" jika ingin membatalkan untuk menghapus data
                                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                //Memunculkan AlertDialog nya
                                builder.show();
                                break;
                        }
                        return true;
                    }
                });
                pm.show();
                return true;
            }
        });

        //Fungsi untuk melihat data jika di klik pada card
        holder.cardku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Membuat objek bundle
                Bundle b = new Bundle();

                //Memasukkan value dari variabel nama dengan kunci "a" dan dimasukkan ke dalam bundle
                b.putString("a", nm.trim());

                //Memasukkan value dari variabel nama dengan kunci "b" dan dimasukkan ke dalam bundle
                b.putString("b", tlp.trim());

                //Membuat objek Intent berpindah activity dari MainActivity ke HomeActivity
                Intent i = new Intent(context, LihatDataTeman.class);

                //Memasukkan bundle ke dalam Intent untuk dikirimkan ke ActivityHasil
                i.putExtras(b);

                //Berpindah ke ActivityHaasil
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (listData != null)?listData.size() : 0;
    }

    public class TemanViewHolder extends RecyclerView.ViewHolder{
        private CardView cardku;
        private TextView tvnama, tvtelpon;
        private ImageView img;
        String nm, tlp;
        public TemanViewHolder(View view) {
            super(view);
            cardku = (CardView) view.findViewById(R.id.kartuku);
            tvnama = (TextView) view.findViewById(R.id.textNama);
            tvtelpon = (TextView) view.findViewById(R.id.textTelpon);
            img = (ImageView) view.findViewById(R.id.image);

        }
    }

}
