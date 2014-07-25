package joss.jacobo.lol.lcs.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import javax.inject.Inject;

import joss.jacobo.lol.lcs.Datastore;
import joss.jacobo.lol.lcs.MainApp;
import joss.jacobo.lol.lcs.R;
import joss.jacobo.lol.lcs.interfaces.BaseFragmentListener;

/**
 * Created by jossayjacobo on 7/22/14
 */
public class BaseFragment extends Fragment {

    @Inject
    Datastore datastore;

    BaseFragmentListener listener;

    @Override
    public void onViewCreated(View view, Bundle savedState) {
        super.onViewCreated(view, savedState);
        ((MainApp) getActivity().getApplication()).inject(this);
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        if(activity instanceof BaseFragmentListener){
            listener = (BaseFragmentListener) activity;
        }else{
            throw new ClassCastException(activity.toString() + " must implement BaseFragmentListener");
        }
    }

}
