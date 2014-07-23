package joss.jacobo.lol.lcs.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

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
    @InjectView(R.id.emptyView)
    ProgressBar emptyView;

    ListAdapter adapter;
    View loadingView;

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

        loadingView = inflater.inflate(R.layout.view_item_loading, null);
        setHasOptionsMenu(true);

        return view;
    }

    public void setupListView(){
        emptyView.setVisibility(customEmptyView != null ? View.GONE : View.VISIBLE);
        listView.setEmptyView(customEmptyView != null ? customEmptyView : emptyView);
        listView.setOnScrollListener(this);
        listView.setOnItemClickListener(this);
    }

    public void setupListViewWithLoadingView(){
        listView.addFooterView(loadingView);
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
        this.customerEmptyView = view;
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
        listView.removeFooterView(loadingView);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
