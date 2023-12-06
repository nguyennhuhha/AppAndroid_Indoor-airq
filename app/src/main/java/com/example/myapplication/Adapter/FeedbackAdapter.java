package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Feedback;
import com.example.myapplication.R;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {
    private Context context;
    private List<Feedback> fbs;
    public FeedbackAdapter(Context context, List<Feedback> fbs){
            this.fbs=fbs;
            this.context=context;
            notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FeedbackAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_feedback, parent, false);
            return new FeedbackAdapter.MyViewHolder(view);}
    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.MyViewHolder holder, int position) {
        Feedback feedback = fbs.get(position);
        if (feedback == null) return;
        holder.email.setText(feedback.getEmail());
        holder.name.setText(feedback.getUsername());
        holder.time.setText(feedback.getTime());
        holder.overall.setText(feedback.getOverall());
        holder.temp.setText(feedback.getDegree());
        holder.wind.setText(feedback.getWind());
        holder.other.setText(feedback.getOther());
        // Set long click listener for each item
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Handle long click (delete fb)
                deleteFb(feedback);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (fbs!= null)
            return fbs.size();
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView email, name, time, overall, temp, wind, other;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.email);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            overall = itemView.findViewById(R.id.overall);
            temp = itemView.findViewById(R.id.temp);
            wind = itemView.findViewById(R.id.wind);
            other = itemView.findViewById(R.id.other);
        }
    }
        // Method to delete contact from Database
        private void deleteFb(Feedback fb) {
            FeedbackHandler db = new FeedbackHandler(context);
            db.deleteFb(fb);
            fbs=db.getAllFb();
            notifyDataSetChanged();
        }
}

