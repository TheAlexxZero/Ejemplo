package com.example.proyectov11;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {
    private Context context;
    private Cursor cursor;

    public HabitAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_habit, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        if (cursor.moveToPosition(position)) {
            String habitName = cursor.getString(cursor.getColumnIndexOrThrow("nombre_habito"));
            String timeToSleep = cursor.getString(cursor.getColumnIndexOrThrow("hora_dormir"));
            String timeToWake = cursor.getString(cursor.getColumnIndexOrThrow("hora_despertar"));
            String frequency = cursor.getString(cursor.getColumnIndexOrThrow("frecuencia"));
            String type = cursor.getString(cursor.getColumnIndexOrThrow("tipo_habito"));
            String specificType = cursor.getString(cursor.getColumnIndexOrThrow("tipo_especifico"));
            String term = cursor.getString(cursor.getColumnIndexOrThrow("plazo"));

            // Asignar los datos a los TextView correspondientes
            holder.textViewHabitName.setText(habitName);
            holder.textViewTimeToSleep.setText(timeToSleep);
            holder.textViewTimeToWake.setText(timeToWake);
            holder.textViewFrequency.setText(frequency);
            holder.textViewType.setText(type);
            holder.textViewSpecificType.setText(specificType);
            holder.textViewTerm.setText(term);
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    static class HabitViewHolder extends RecyclerView.ViewHolder {
        TextView textViewHabitName;
        TextView textViewTimeToSleep;
        TextView textViewTimeToWake;
        TextView textViewFrequency;
        TextView textViewType;
        TextView textViewSpecificType;
        TextView textViewTerm;

        HabitViewHolder(View itemView) {
            super(itemView);
            textViewHabitName = itemView.findViewById(R.id.textViewHabitName);
            textViewTimeToSleep = itemView.findViewById(R.id.textViewTimeToSleep);
            textViewTimeToWake = itemView.findViewById(R.id.textViewTimeToWake);
            textViewFrequency = itemView.findViewById(R.id.textViewFrequency);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewSpecificType = itemView.findViewById(R.id.textViewSpecificType);
            textViewTerm = itemView.findViewById(R.id.textViewTerm);
        }
    }
}
