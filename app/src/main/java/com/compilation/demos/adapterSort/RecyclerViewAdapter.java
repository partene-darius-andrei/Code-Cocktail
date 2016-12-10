package com.compilation.demos.adapterSort;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.compilation.mainApp.Model;
import com.compilation.R;

import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<Model> models;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }
    }


    RecyclerViewAdapter(List<Model> models) {
        this.models = models;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_row_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Model model = models.get(position);
        holder.title.setText(model.getTitle());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}