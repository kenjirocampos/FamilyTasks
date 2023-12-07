package com.example.familytasks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
Button btn_Ingresar,btn_Registrar;
CheckBox cbEstado;
EditText email,password;
FirebaseAuth mAuth;
FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        email = findViewById(R.id.editMail);
        password = findViewById(R.id.editPass);
        cbEstado = findViewById(R.id.cbEstado);
        btn_Ingresar = findViewById(R.id.btnIngresar);
        btn_Registrar = findViewById(R.id.btnRegistrar);
        if (currentUser != null) {
            SharedPreferences preferences = getSharedPreferences(currentUser.getUid(), MODE_PRIVATE);
            boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);
            if (isLoggedIn) {
                finish();
                obtenerNombreUsuario();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        }
    }
    public void cambiarActivity(View v){
        finish();
        startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
    }
    public void datosSesion(View v){
        String email_Usuario = email.getText().toString().trim();
        String pass_Usuario = password.getText().toString().trim();
        if(email_Usuario.isEmpty() && pass_Usuario.isEmpty()){
            Toast.makeText(LoginActivity.this, "Ingresar Datos", Toast.LENGTH_SHORT).show();
        }else{
            iniciarSesion(email_Usuario,pass_Usuario);
        }
    }

    private void iniciarSesion(String emailUsuario, String passUsuario) {
        mAuth.signInWithEmailAndPassword(emailUsuario, passUsuario).addOnCompleteListener(task -> {
        if(task.isSuccessful()){
            if(cbEstado.isChecked()) {
                String currentUserID = String.valueOf(mAuth.getCurrentUser().getUid());
                SharedPreferences preferences = getSharedPreferences(currentUserID, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.apply();
            }
            finish();
            obtenerNombreUsuario();
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
        }).addOnFailureListener(e -> Toast.makeText(LoginActivity.this,"Error al iniciar sesion",Toast.LENGTH_SHORT).show());
    }
    private void obtenerNombreUsuario() {
        if (currentUser != null) {
            CollectionReference usuariosCollection = FirebaseFirestore.getInstance().collection("usuarios");
            DocumentReference usuarioDocRef = usuariosCollection.document(currentUser.getUid());
            usuarioDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        String nombre = documentSnapshot.getString("nombreUsuario");
                        Toast.makeText(LoginActivity.this, "Bienvenido " + nombre, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}