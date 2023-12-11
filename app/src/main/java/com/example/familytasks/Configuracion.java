package com.example.familytasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Configuracion extends AppCompatActivity {
Button btnSalir;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        btnSalir = findViewById(R.id.btnSalir);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        btnSalir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cerrarMain();
                finish();
                SharedPreferences preferences = getSharedPreferences(currentUser.getUid(), MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();
                mAuth.signOut();
                startActivity(new Intent(Configuracion.this,LoginActivity.class));
            }
        });
    }
    public void cerrarMain(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

}