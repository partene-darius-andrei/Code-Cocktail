package com.compilation.demos.coverImages;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.compilation.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    /**
     * We load the URIs of the images in the constructor using getImages(size)
     */

    private List<Uri> coverImages;

    private int size;

    class MyViewHolder extends RecyclerView.ViewHolder {

        //fresco's custom view for images
        SimpleDraweeView cover;

        MyViewHolder(View view) {
            super(view);
            cover = (SimpleDraweeView) view.findViewById(R.id.cover);
        }
    }


    RecyclerViewAdapter(int size) {
        this.size = size;
        coverImages = Covers.getImages(size);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cover_row_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //loading the cover based on position

        holder.cover.setImageURI(coverImages.get(position));
    }

    @Override
    public int getItemCount() {
        return size;
    }
}