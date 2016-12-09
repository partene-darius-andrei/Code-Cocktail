package com.compilation.demos.coverImages;

import android.net.Uri;

import com.compilation.R;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import java.util.ArrayList;
import java.util.List;

class Covers {

    //TODO replace with funnier images

    static List<Uri> getImages(int size){
        List<Uri> list = new ArrayList<>();
        list.add((ImageRequestBuilder.newBuilderWithResourceId(R.drawable.cover_1).build()).getSourceUri());
        list.add((ImageRequestBuilder.newBuilderWithResourceId(R.drawable.cover_2).build()).getSourceUri());
        list.add((ImageRequestBuilder.newBuilderWithResourceId(R.drawable.cover_3).build()).getSourceUri());

        List<Uri> aux = new ArrayList<>();
        for (int i = 0; i < size; i = i + list.size()){
            for (int j = 0; j < list.size(); j++){
                if (j <size )
                    aux.add(list.get(j));
            }
        }
        return aux;
    }
}
