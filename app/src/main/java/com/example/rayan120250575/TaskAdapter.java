package com.example.rayan120250575;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    Context context;
    ArrayList<Task> list;
    DBHelper db;

    public TaskAdapter(Context context, ArrayList<Task> list) {
        this.context = context;
        this.list = list;
        this.db = new DBHelper(context);
    }

    public void setList(ArrayList<Task> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = list.get(position);

        holder.tvTitle.setText(task.getTitle());
        holder.tvGrade.setText("العلامة: " + task.getGrade());
        holder.cbDone.setChecked(task.isSubmitted());


        holder.cbDone.setOnClickListener(v -> {
            db.updateSubmitted(task.getId(), holder.cbDone.isChecked());
        });


        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context).setTitle("حذف").setMessage("هل تريد الحذف؟").setPositiveButton("نعم", (dialog, which) -> {
                        db.deleteTask(task.getId());
                        list.remove(task);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("لا", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvGrade;
        CheckBox cbDone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvGrade = itemView.findViewById(R.id.tvGrade);
            cbDone = itemView.findViewById(R.id.cbDone);
        }
    }
}