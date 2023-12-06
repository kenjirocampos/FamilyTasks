package com.example.familytasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.familytasks.Model.taskFamily;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class nueva_tarea extends AppCompatActivity {
EditText nombreTarea,detalleTarea,fechaInicio,fechaTermino;
Switch estadoSwitch;
boolean estadoTarea,estadoAsignado;
FirebaseFirestore miFirestore;
Button btnGuardarTarea;
ArrayList<taskFamily> taskFamilyArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_tarea);
        btnGuardarTarea = findViewById(R.id.btnGuardarTarea);
        nombreTarea = findViewById(R.id.editNombreTarea);
        detalleTarea = findViewById(R.id.editDetalles);
        fechaInicio = findViewById(R.id.editFechaInicio);
        fechaTermino = findViewById(R.id.editFechaTermino);
        estadoSwitch = findViewById(R.id.estadoSW);
        miFirestore = FirebaseFirestore.getInstance();
    }

    public void guardarDatos(View view) {
                    String idUsuario = "";
                    String nombreT = nombreTarea.getText().toString().trim();
                    String detalleT = detalleTarea.getText().toString().trim();
                    String fechaI = fechaInicio.getText().toString().trim();
                    String fechaT = fechaTermino.getText().toString().trim();
                    if (estadoSwitch.isChecked()) estadoTarea = true;
                    else estadoTarea = false;
                    estadoAsignado = false;
                    Map<String, Object> map = new HashMap<>();
                    map.put("idUsuario", idUsuario);
                    map.put("nombreTarea", nombreT);
                    map.put("detalleTarea", detalleT);
                    map.put("fechaInicio", fechaI);
                    map.put("fechaTermino", fechaT);
                    map.put("estadoTarea", estadoTarea);
                    map.put("estadoAsignado", estadoAsignado);
                    miFirestore.collection("familyTask").document().set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(nueva_tarea.this, MainActivity.class));
                            Toast.makeText(nueva_tarea.this, "Exito al guardar", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(nueva_tarea.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                        }
                    });
            }

}