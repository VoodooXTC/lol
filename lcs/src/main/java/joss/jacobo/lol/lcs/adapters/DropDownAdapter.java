package joss.jacobo.lol.lcs.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import joss.jacobo.lol.lcs.api.model.LiveStreams.Video;
import joss.jacobo.lol.lcs.views.ActionBarDropDownItem;

/**
 * Created by: jossayjacobo
 * Date: 2/12/15
 * Time: 4:31 PM
 */
public class DropDownAdapter extends BaseAdapter {

    private Context context;
    public List<Video> videos;

    public DropDownAdapter(Context context, List<Video> videos){
        this.context = context;
        this.videos = videos;
    }

    @Override
    public int getCount() {
        return videos != null ? videos.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setVideos(List<Video> videos){
        this.videos = videos;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ActionBarDropDownItem item = convertView == null ?
                new ActionBarDropDownItem(context) :
                (ActionBarDropDownItem) convertView;
        item.setContent(videos.get(position));

        return item;
    }
}
