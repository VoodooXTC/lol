package joss.jacobo.lol.lcs.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import javax.inject.Inject;

import joss.jacobo.lol.lcs.Datastore;
import joss.jacobo.lol.lcs.MainApp;

/**
 * Created by jossayjacobo on 7/22/14
 */
public class BaseFragment extends Fragment {

    @Inject
    Datastore datastore;

    @Override
    public void onViewCreated(View view, Bundle savedState) {
        super.onViewCreated(view, savedState);
        ((MainApp) getActivity().getApplication()).inject(this);
    }
}
