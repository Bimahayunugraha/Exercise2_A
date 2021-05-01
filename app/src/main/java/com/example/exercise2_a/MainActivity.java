package com.example.exercise2_a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.exercise2_a.adapter.TemanAdapter;
import com.example.exercise2_a.database.DBController;
import com.example.exercise2_a.database.Teman;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TemanAdapter adapter;
    private ArrayList<Teman> temanArrayList;
    DBController controller = new DBController(this);
    String id, nm, tlp;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.floatingBtn);
        BacaData();

        adapter = new TemanAdapter(temanArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TemanBaru.class);
                startActivity(intent);
            }
        });

    }

    public  void BacaData() {
        ArrayList<HashMap<String,String>> daftarTeman = controller.getAllTeman();
        temanArrayList = new ArrayList<>();

        //Memindah dari hasil Query kedalam teman
        for (int i=0; i<daftarTeman.size();i++) {
            Teman teman = new Teman();

            teman.setId(daftarTeman.get(i).get("id").toString());
            teman.setNama(daftarTeman.get(i).get("nama").toString());
            teman.setTelpon(daftarTeman.get(i).get("telpon").toString());

            //Pindahkan dari teman kedalam ArrayList teman di adapter
            temanArrayList.add(teman);

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.infomenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.infoMenu){
            callInfo();
        }
        return super.onOptionsItemSelected(item);
    }

    public void callInfo(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("* Klik pada card untuk melihat detail data. \n" +
                "* Klik dan tahan pada card untuk memilih opsi menu edit atau hapus data.");
        builder.setCancelable(true);
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}