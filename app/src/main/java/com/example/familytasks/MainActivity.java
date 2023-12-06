package com.example.familytasks;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.familytasks.Adapter.taskAdapter;
import com.example.familytasks.Model.taskFamily;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
FirebaseAuth mAuth;
FirebaseUser currentUser;
FloatingActionButton fab;
RecyclerView recyclerView;
ArrayList<taskFamily> list;
taskAdapter taskAdapter1;
FirebaseFirestore db;

Button btnAsignar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        recyclerView = findViewById(R.id.rvGeneral);
        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        taskAdapter1 = new taskAdapter(this,list);
        taskAdapter1.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter1);
        db.collection("familyTask").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String idTarea = document.getId();
                        String fechaTermino = document.getString("fechaTermino");
                        String nombreTarea = document.getString("nombreTarea");
                        String detalleTarea = document.getString("detalleTarea");
                        String fechaInicio = document.getString("fechaInicio");
                        boolean estadoTarea = document.getBoolean("estadoTarea");
                        boolean estadoAsignado = document.getBoolean("estadoAsignado");
                        String idUsuario = mAuth.getCurrentUser().getUid();
                        list.add(new taskFamily(nombreTarea,detalleTarea,fechaInicio,fechaTermino,estadoTarea,estadoAsignado,idTarea,idUsuario));
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                    taskAdapter1.notifyDataSetChanged();
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
        taskAdapter1.notifyDataSetChanged();
        fab = findViewById(R.id.miFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_fab);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                handleMenuItemClick(item.getItemId());
                return true;
            }
        });
        popupMenu.show();
    }
    private void handleMenuItemClick(int itemId) {
        int id = itemId;
        if(id == R.id.item1){
            startActivity(new Intent(MainActivity.this,nueva_tarea.class));
        }else if(id == R.id.item2){

        } else if (id == R.id.item3) {

        } else if (id == R.id.item4) {
            startActivity(new Intent(MainActivity.this,Configuracion.class));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_fab,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){

        return super.onOptionsItemSelected(item);
    }
}