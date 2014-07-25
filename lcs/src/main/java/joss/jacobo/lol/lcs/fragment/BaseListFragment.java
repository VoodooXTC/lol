package joss.jacobo.lol.lcs.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import joss.jacobo.lol.lcs.R;

/**
 * Created by jossayjacobo on 7/22/14
 */
public class BaseListFragment extends BaseFragment implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    @InjectView(R.id.container)
    RelativeLayout container;
    @InjectView(R.id.listview)
    ListView listView;
    @InjectView(R.id.loadingView)
    LinearLayout loadingView;
    @InjectView(R.id.emptyView)
    TextView emptyView;

    ListAdapter adapter;
    View loadingItem;
    View customEmptyView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_list,
                container, false);
        ButterKnife.inject(this, view);

        loadingItem = inflater.inflate(R.layout.view_item_loading, listView, false);
        setHasOptionsMenu(true);

        return view;
    }

    public void setupListView(){
        emptyView.setVisibility(customEmptyView != null ? View.GONE : View.VISIBLE);
        listView.setEmptyView(customEmptyView != null ? customEmptyView : emptyView);
        listView.setOnScrollListener(this);
        listView.setOnItemClickListener(this);
    }

    public void setupListViewWithLoadingItemView(){
        listView.addFooterView(loadingItem);
        setupListView();
    }

    public void setAdapter(ListAdapter listAdapter){
        this.adapter = listAdapter;
        listView.setAdapter(adapter);
    }

    /**
     * Must be called before setup
     * @param view - Custom Empty View
     */
    public void setEmptyView(View view){
        this.container.addView(view);
        this.customEmptyView = view;
    }


    /**
     * Must be called before setup
     * @param view - Header View
     */
    public void addHeaderView(View view){
        listView.addHeaderView(view);
    }

    /**
     * Must be called before setup
     * @param view - Footer View
     */
    public void addFooterView(View view){
        listView.addFooterView(view);
    }

    public void removeLoadingItem(){
        listView.removeFooterView(loadingItem);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    /**
     * Scroll Listener
     *
     * When the user reaches the end of the list it calls onLoadMore()
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastItemOnScreen = firstVisibleItem + visibleItemCount;
        boolean loadMore = lastItemOnScreen == totalItemCount;
        if(loadMore)
            onLoadMore();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Empty Method
    }

    public void onLoadMore() {
        // Empty Method
    }

    public void showLoading(){
        loadingView.setVisibility(View.VISIBLE);
    }

    public void showContent(){
        loadingView.setVisibility(View.GONE);
    }
}
