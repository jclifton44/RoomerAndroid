package com.roomra.roomerAndroid.roomerandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
/**
 * Created by jeremyclifton on 7/24/14.
 */
public class ModularStoryArrayAdapter extends ArrayAdapter<ModularStory>{
    public Context context;
    public static ListView listContainer;
    public ModularStoryArrayAdapter(Context c, int resourceId, List<ModularStory> items) {
        super(c, resourceId, items);
        this.context = c;
    }
    private class ViewHolder {
        ImageView contentPhoto;
        TextView markText;
        TextView topicText;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ModularStory rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.story_item, null);
            holder = new ViewHolder();
            holder.markText = (TextView) convertView.findViewById(R.id.markText);
            holder.topicText = (TextView) convertView.findViewById(R.id.topicText);
            // holder.contentPhoto = (ImageView) convertView.findViewById(R.id.contentPhoto);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.markText.setText(rowItem.getMarkText());
        holder.topicText.setText(rowItem.getTopicText());

        //holder.contentPhoto.setImageResource(rowItem.getContentPhotoId());


        return convertView;
    }
    public void setupAdapterClickListener(ListView lv) {
        this.listContainer = lv;
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                //listContainer.smoothScrollToPosition(position);
                listContainer.setSelectionFromTop(position, 0);
                listContainer.post( new Runnable() {
                    @Override
                    public void run() {
                        //call smooth scroll
                    }
                });
                //Toast.makeText(context,
                //        ((TextView) view.findViewById(R.id.markText)).getText() + " --- " + Integer.toString(position), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
