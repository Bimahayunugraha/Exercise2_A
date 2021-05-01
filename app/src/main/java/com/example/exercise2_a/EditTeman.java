package com.example.exercise2_a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.exercise2_a.database.DBController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class EditTeman extends AppCompatActivity {

    private TextInputEditText editNama, editTelpon;
    private FloatingActionButton fabSave, fabCancel;
    String id, nama, telpon;
    DBController controller = new DBController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teman);

        editNama = (TextInputEditText) findViewById(R.id.txtIdtNama);
        editTelpon = (TextInputEditText) findViewById(R.id.txtIdtTelpon);
        fabSave = (FloatingActionButton) findViewById(R.id.fabSave);
        fabCancel = (FloatingActionButton) findViewById(R.id.fabCancel);

        id = getIntent().getStringExtra("id");
        nama = getIntent().getStringExtra("nama");
        telpon = getIntent().getStringExtra("telepon");

        setTitle("Edit Data");
        editNama.setText(nama);
        editTelpon.setText(telpon);

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editNama.getText().toString().equals("")||editTelpon.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Data Belum Lengkap!!", Toast.LENGTH_SHORT).show();
                }else {
                    nama = editNama.getText().toString();
                    telpon = editTelpon.getText().toString();

                    HashMap<String,String> qvalues =  new HashMap<>();
                    qvalues.put("id", id);
                    qvalues.put("nama",nama);
                    qvalues.put("telpon",telpon);

                    Toast.makeText(getApplicationContext(),"Data Disimpan", Toast.LENGTH_SHORT).show();
                    controller.updateData(qvalues);
                    callHome1();
                }
            }
        });

        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHome1();
            }
        });

    }


    public void callHome1(){
        Intent intent = new Intent(EditTeman.this,MainActivity.class);
        startActivity(intent);
    }

}