package com.example.noteappjava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteappjava.R;
import com.example.noteappjava.databinding.ItemNoteBinding;
import com.example.noteappjava.model.NotesModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.MyViewHolder> {
    Context context;

    private List<NotesModel> notes;
    NotesClickListener listener;

    public NotesListAdapter(Context context,List<NotesModel> notes,NotesClickListener listener){
        this.context = context;
        this.notes = notes;
        this.listener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ItemNoteBinding binding;

        public MyViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.binding.tvTitle.setText(notes.get(position).getTitle());
        holder.binding.tvTitle.setSelected(true);

        holder.binding.tvNotes.setText(notes.get(position).getNotes());

        holder.binding.tvDate.setText(notes.get(position).getDatetime());
        holder.binding.tvDate.setSelected(true);

        if (notes.get(position).getPinned()) {
            holder.binding.imgPin.setImageResource(R.drawable.svg_pin);
        } else {
            holder.binding.imgPin.setImageResource(0);
        }
        int color_code = getRandomColor();
        holder.binding.cvItem.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));

        holder.binding.cvItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                listener.onClick(notes.get(holder.getAdapterPosition()));
            }
        });

        holder.binding.cvItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(notes.get(holder.getAdapterPosition()),holder.binding.cvItem);
                return true;
            }
        });
    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();

        colorCode.add(R.color.color_red);
        colorCode.add(R.color.color_orange);
        colorCode.add(R.color.color_lime);
        colorCode.add(R.color.color_teal);
        colorCode.add(R.color.color_purple);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

}

