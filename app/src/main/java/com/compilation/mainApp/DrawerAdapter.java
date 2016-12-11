package com.compilation.mainApp;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.compilation.R;

import java.util.List;

class DrawerAdapter extends BaseAdapter {

    private Context context;
    private List<String> icons;
    private List<String> names;

    DrawerAdapter(Context context, List<String> icons, List<String> names) {
        this.context = context;
        this.icons = icons;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.drawer_list_item, null);
        TextView icon = (TextView) view.findViewById(R.id.icon);
        icon.setTypeface(getFont());
        icon.setText(icons.get(position));

        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(names.get(position));

        return view;

    }

    Typeface getFont() {
        return Typeface.createFromAsset(context.getAssets(), "fontello.ttf");
    }


}
