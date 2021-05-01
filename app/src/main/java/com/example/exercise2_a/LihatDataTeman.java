package com.example.exercise2_a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class LihatDataTeman extends AppCompatActivity {

    private TextView tvnama, tvtelpon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data_teman);

        tvnama = (TextView) findViewById(R.id.tvNamaKontak);
        tvtelpon = (TextView) findViewById(R.id.tvNomorTelepom);

        //Mendeklarasikan variabel bundle yang akan digunakan untuk mengambil pesan
        //yang dikirimkan melalui method Intent
        Bundle bundle = getIntent().getExtras();

        //Membuat variabel string yang digunakan untuk menyimpan data yang dikirimkan
        //dari activity sebelumnya dengan kunci "a"
        String nama = bundle.getString("a");

        //Membuat variabel string yang digunakan untuk menyimpan data yang dikirimkan
        //dari activity sebelumnya dengan kunci "b"
        String telpon = bundle.getString("b");

        //Menampilkan value dari variabel email ke dalam txEmail
        tvnama.setText(nama);

        //Menampilkan value dari variabel email ke dalam txPassword
        tvtelpon.setText(telpon);
    }
}