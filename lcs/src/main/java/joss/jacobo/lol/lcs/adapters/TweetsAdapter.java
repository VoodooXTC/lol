package joss.jacobo.lol.lcs.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import joss.jacobo.lol.lcs.model.TweetsModel;
import joss.jacobo.lol.lcs.views.TweetItem;

/**
 * Created by Joss on 8/2/2014
 */
public class TweetsAdapter extends BaseAdapter {

    Context context;
    public List<TweetsModel> items;

    public TweetsAdapter(Context context, List<TweetsModel>items){
        this.context = context;
        this.items = items;
    }

    public void setItems(List<TweetsModel> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        TweetsModel tweet = items.get(position);

        TweetItem tweetItem = convertView == null
                ? new TweetItem(context)
                : (TweetItem) convertView;
        tweetItem.setContent(tweet);

        return tweetItem;
    }
}