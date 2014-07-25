package joss.jacobo.lol.lcs.fragment;

import android.os.Bundle;
import android.view.View;

/**
 * Created by jossayjacobo on 7/25/14.
 */
public class TeamBaseFragment extends BaseFragment {

    public static final String TITLE = "title";

    public String title;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        title = getArguments().getString(TITLE);
    }

    @Override
    public void onViewCreated(View view, Bundle savedState) {
        super.onViewCreated(view, savedState);

    }
}
