package com.example.familytasks.Adapter;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.familytasks.Model.taskFamily;
import com.example.familytasks.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TareasGeneralesFragment extends Fragment {
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    RecyclerView recyclerView;
    ArrayList<taskFamily> list;
    taskAdapter taskAdapter1;
    FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tareas_generales, container, false);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        recyclerView = view.findViewById(R.id.rvGeneral);
        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        taskAdapter1 = new taskAdapter(requireContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
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
                        list.add(new taskFamily(nombreTarea, detalleTarea, fechaInicio, fechaTermino, estadoTarea, estadoAsignado, idTarea, idUsuario));
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                    taskAdapter1.notifyDataSetChanged();
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });

        return view;
    }


}
