package com.example.familytasks.Adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Color;
import java.util.HashMap;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.familytasks.MainActivity;
import com.example.familytasks.Model.taskFamily;
import com.example.familytasks.R;
import com.example.familytasks.nueva_tarea;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class taskAdapter extends RecyclerView.Adapter<taskAdapter.MiViewHolder>{
    private HashMap<Integer, Boolean> expandableMap = new HashMap<>();
    Context context;
    ArrayList<taskFamily> taskFamilyArrayList;

    public taskAdapter(Context context, ArrayList<taskFamily> taskFamilyArrayList) {
        this.context = context;
        this.taskFamilyArrayList = taskFamilyArrayList;
    }

    @NonNull
    @Override
    public taskAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.view_task,parent,false);

        return new MiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder holder, int position) {
        taskFamily task = taskFamilyArrayList.get(position);
        boolean isExpanded = expandableMap.get(position) != null && expandableMap.get(position);
        holder.bottomContainer.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.btnCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isExpanded = expandableMap.get(position) != null && expandableMap.get(position);
                expandableMap.put(position, !isExpanded);
                notifyItemChanged(position);
            }
        });

        String estadoSalida;
        holder.nombreTarea.setText(task.getNombreTarea());
        if(task.isEstadoTarea()){
            estadoSalida = "Completada";
        }else{estadoSalida = "Incompleta";}
        holder.estadoTarea.setText(estadoSalida);
        holder.detalleTarea.setText(task.getDetalleTarea());
        holder.fechaInicio.setText((task.getFechaInicio()));
        holder.fechaTermino.setText(task.getFechaTermino());
        if(task.isEstadoAsignado()){
            holder.btnCollapse.setBackgroundColor(Color.parseColor("GREEN"));
            holder.btnCollapse.setText("Asignado");
        }else{
            holder.btnCollapse.setBackgroundColor(Color.parseColor("RED"));
            holder.btnCollapse.setText("Reclamar");
        }
        holder.idCard.setText(task.getIdTarea());
        holder.btnCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                String idUsuario = currentUser.getUid();
                String idTarea = task.getIdTarea();
                DocumentReference idUsuarioRef = db.collection("familyTask").document(idTarea);
                idUsuarioRef.update("idUsuario",idUsuario).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Documento actualizado");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Falla al actualizar", e);
                    }
                });
                boolean estado = task.isEstadoAsignado();
                if(estado){estado=false;}else{estado=true;}
                idUsuarioRef.update("estadoAsignado",estado).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "Documento actualizado");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Falla al actualizar", e);
                    }
                });
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskFamilyArrayList.size();
    }

    public static class MiViewHolder extends RecyclerView.ViewHolder{

        TextView nombreTarea,estadoTarea,detalleTarea,fechaInicio,fechaTermino,idCard;
        Button btnCollapse;

        CardView btnCardView;
        LinearLayout bottomContainer;

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTarea = itemView.findViewById(R.id.nombreCard);
            estadoTarea = itemView.findViewById(R.id.estadoCard);
            detalleTarea = itemView.findViewById(R.id.detalleCard);
            fechaInicio = itemView.findViewById(R.id.fechaInicioCard);
            fechaTermino = itemView.findViewById(R.id.fechaTerminoCard);
            idCard = itemView.findViewById(R.id.idCard);
            btnCollapse = itemView.findViewById(R.id.btnCollapse);
            btnCardView = itemView.findViewById(R.id.btnCardView);
            bottomContainer = itemView.findViewById(R.id.bottom_container);
        }
    }
}
