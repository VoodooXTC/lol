package joss.jacobo.lol.lcs.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import joss.jacobo.lol.lcs.items.DrawerItem;
import joss.jacobo.lol.lcs.views.DrawerItemSectionTitle;
import joss.jacobo.lol.lcs.views.DrawerItemView;

/**
 * Created by: jossayjacobo
 * Date: 2/12/15
 * Time: 1:43 PM
 */
public class MenuListAdapter extends BaseAdapter {

    private Context context;
    public List<DrawerItem> items;
    private int hintPosition;

    public MenuListAdapter(Context c, List<DrawerItem> i) {
        this.context = c;
        this.items = i;
    }

    public void setItems(List<DrawerItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position){
        return items.get(position).type;
    }

    @Override
    public int getViewTypeCount(){
        return DrawerItem.TYPE_MAX;
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setHint(int position) {
        hintPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        DrawerItem item = items.get(i);

        switch(item.type){
            case DrawerItem.TYPE_SECTION_TITLE:
                DrawerItemSectionTitle drawerItemSectionTitle = view == null ? new DrawerItemSectionTitle(context) : (DrawerItemSectionTitle) view;
                drawerItemSectionTitle.setContent(item);
                return drawerItemSectionTitle;
            default:
                DrawerItemView drawerItem = view == null ? new DrawerItemView(context) : (DrawerItemView) view;
                drawerItem.setContent(item);
                drawerItem.title.setSelected(i == hintPosition);
                return drawerItem;
        }
    }
}