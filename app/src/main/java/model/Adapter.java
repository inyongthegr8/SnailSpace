package model;

import android.content.Intent;
import android.os.Build;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.celestialsnails.covid19.EntryDetails;
import com.celestialsnails.covid19.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<String> titles;
    List<String> content;


    public Adapter(List<String> title, List<String> content){
        this.titles = title;
        this.content = content;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_view_layout,parent,false);
        return new ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.entryTitle.setText(titles.get(position));
        holder.entryContent.setText(content.get(position));
        final int rand = getRandomColor();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.mCardView.setCardBackgroundColor(holder.view.getResources().getColor(rand,null));
        } else {
            holder.mCardView.setCardBackgroundColor(rand);
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EntryDetails.class);
                i.putExtra("title",titles.get(position));
                i.putExtra("content",content.get(position));
                i.putExtra("color",rand);

                v.getContext().startActivity(i);
            }
        });

    }

    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.green);
        colorCode.add(R.color.cyan);
        colorCode.add(R.color.blue);
        colorCode.add(R.color.purple);
        colorCode.add(R.color.yellow);
        colorCode.add(R.color.red);
        colorCode.add(R.color.grey);

        Random randomColor = new Random();
        int number = randomColor.nextInt(colorCode.size());
        return colorCode.get(number);

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        TextView entryTitle,entryContent;
        View view;
        CardView mCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            entryTitle = itemView.findViewById(R.id.titles);
            entryContent = itemView.findViewById(R.id.content);
            view = itemView;
            mCardView = itemView.findViewById(R.id.entryCard);

        }


    }
}
