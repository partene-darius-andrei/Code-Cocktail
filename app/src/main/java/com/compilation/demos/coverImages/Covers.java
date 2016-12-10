package com.compilation.demos.coverImages;

import android.net.Uri;

import com.compilation.R;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import java.util.ArrayList;
import java.util.List;

class Covers {

    /**
     * This class returns a list of URIs used for the adapter
     * We use URIs because that's how Fresco loads it's custom views
     */

    static List<Uri> getImages(int size){
        List<Uri> list = new ArrayList<>();
        list.add((ImageRequestBuilder.newBuilderWithResourceId(R.drawable.cover_1).build()).getSourceUri());
        list.add((ImageRequestBuilder.newBuilderWithResourceId(R.drawable.cover_2).build()).getSourceUri());
        list.add((ImageRequestBuilder.newBuilderWithResourceId(R.drawable.cover_3).build()).getSourceUri());

        List<Uri> uris = new ArrayList<>();

        //the first for iterates through the list of objects to be populated with covers

        for (int i = 0; i < size; i = i + list.size()){

            //the second for iterates through the list of images

            for (int j = 0; j < list.size(); j++){

                //condition used to stop from getting a cover image from a index higher than the covers actual size

                if (j < size )
                    uris.add(list.get(j));
            }
        }
        return uris;
    }
}
