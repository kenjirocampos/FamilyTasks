package com.example.familytasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {
EditText nameUser,mailUser,passUser,conPassUser;
Button regUser;
FirebaseFirestore miFireStore;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        miFireStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        nameUser = findViewById(R.id.editName);
        mailUser = findViewById(R.id.editRegMail);
        passUser = findViewById(R.id.editRegPass);
        conPassUser = findViewById(R.id.editConPass);
        regUser = findViewById(R.id.btnRegistro);
    }
    public void capturarUsuario(View v){
        String nombreUsuario = nameUser.getText().toString().trim();
        String correoUsuario = mailUser.getText().toString().trim();
        String passUsuario = passUser.getText().toString().trim();
        String confPassUser = conPassUser.getText().toString().trim();

        if(nombreUsuario.isEmpty() && correoUsuario.isEmpty() && passUsuario.isEmpty() && confPassUser.isEmpty()){
            Toast.makeText(RegistroActivity.this, "Complete los datos", Toast.LENGTH_SHORT).show();
        }else if(passUsuario.equals(confPassUser)){
                registrarUsuario(nombreUsuario,correoUsuario,passUsuario);
        }else{
                Toast.makeText(RegistroActivity.this, "Las claves no coinciden", Toast.LENGTH_SHORT).show();
            }
        }


    private void registrarUsuario(String nombreUsuario, String correoUsuario, String passUsuario) {
            mAuth.createUserWithEmailAndPassword(correoUsuario,passUsuario).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String id = mAuth.getCurrentUser().getUid();
                Map<String, Object> map = new HashMap<>();
                map.put("id",id);
                map.put("nombreUsuario",nombreUsuario);
                map.put("correoUsuario",correoUsuario);
                map.put("passUsuario",passUsuario);
                miFireStore.collection("usuarios").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        startActivity(new Intent(RegistroActivity.this,MainActivity.class));
                        Toast.makeText(RegistroActivity.this, "Exito al guardar", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistroActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistroActivity.this, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}