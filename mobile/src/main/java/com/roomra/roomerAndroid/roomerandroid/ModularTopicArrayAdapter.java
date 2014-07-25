package com.roomra.roomerAndroid.roomerandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ModularTopicArrayAdapter extends ArrayAdapter<ModularTopic> {
    public Context context;
    public ModularTopicArrayAdapter(Context c, int resourceId, List<ModularTopic> items) {
        super(c, resourceId, items);
        this.context = c;
    }
    private class ViewHolder {
        TextView topicText;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ModularTopic rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.topic_item, null);
            holder = new ViewHolder();
            holder.topicText = (TextView) convertView.findViewById(R.id.topicText);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.topicText.setText(rowItem.getTopicText());



        return convertView;
    }
}
