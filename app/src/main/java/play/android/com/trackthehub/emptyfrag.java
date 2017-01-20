package play.android.com.trackthehub;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


/**
 * A simple {@link Fragment} subclass.
 */
public class emptyfrag extends Fragment {

    private AdView madview;


    public emptyfrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_emptyfrag, container, false);

        madview=(AdView)rootview.findViewById(R.id.adview);
        AdRequest adRequest= new AdRequest.Builder().build();

        madview.loadAd(adRequest);


        return rootview;
    }

}
