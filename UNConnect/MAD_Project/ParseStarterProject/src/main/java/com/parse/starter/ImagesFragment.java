package com.parse.starter;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseUser;

/**
 * Created by Rakesh Balan on 12/9/2015.
 */
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ImagesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ImagesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    String imageURL;
    public ImagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_images, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // for each folder processing then go with image url to assign to image
        ImageView monalisa = (ImageView) getActivity().findViewById(R.id.monalisa);
        monalisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.monalisa);
                ImageURLHolderClass.getInstance().setImageURL(uri.toString());
                getActivity().getFragmentManager().popBackStack();
                getActivity().getFragmentManager().popBackStack();


            }
        });
        ImageView angrybird = (ImageView) getActivity().findViewById(R.id.angrybird);
        angrybird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.angrybird);
                ImageURLHolderClass.getInstance().setImageURL(uri.toString());
                getActivity().getFragmentManager().popBackStack();
                getActivity().getFragmentManager().popBackStack();
            }
        });
        ImageView rose = (ImageView) getActivity().findViewById(R.id.rose);
        rose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.rose);
                ImageURLHolderClass.getInstance().setImageURL(uri.toString());
                getActivity().getFragmentManager().popBackStack();
                getActivity().getFragmentManager().popBackStack();
            }
        });
        ImageView flower2 = (ImageView) getActivity().findViewById(R.id.flower2);
        flower2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.flower2);
                ImageURLHolderClass.getInstance().setImageURL(uri.toString());
                getActivity().getFragmentManager().popBackStack();
                getActivity().getFragmentManager().popBackStack();
            }
        });

        ImageView sunflowers = (ImageView) getActivity().findViewById(R.id.sunflowers);
        sunflowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.sunflowers);
                ImageURLHolderClass.getInstance().setImageURL(uri.toString());
                getActivity().getFragmentManager().popBackStack();
                getActivity().getFragmentManager().popBackStack();
            }
        });

        ImageView building = (ImageView) getActivity().findViewById(R.id.building);
        building.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.building);
                ImageURLHolderClass.getInstance().setImageURL(uri.toString());
                getActivity().getFragmentManager().popBackStack();
                getActivity().getFragmentManager().popBackStack();
            }
        });

        ImageView building2 = (ImageView) getActivity().findViewById(R.id.building2);
        building2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.building2);
                ImageURLHolderClass.getInstance().setImageURL(uri.toString());
                getActivity().getFragmentManager().popBackStack();
                getActivity().getFragmentManager().popBackStack();
            }
        });

        ImageView gallery_all = (ImageView) getActivity().findViewById(R.id.gallery_all);
        gallery_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.gallery_all);
                ImageURLHolderClass.getInstance().setImageURL(uri.toString());
                getActivity().getFragmentManager().popBackStack();
                getActivity().getFragmentManager().popBackStack();
            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Gallery Fragment - OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and nam
       public void goToLoginFragment();

        void goToProfile();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            ParseUser.logOut();
            ImageURLHolderClass.setDataobject(null);
            Toast.makeText(getActivity(), "Signed out", Toast.LENGTH_SHORT).show();
            mListener.goToLoginFragment();
            return true;
        }

        if (id == R.id.action_profile) {
            ImageURLHolderClass.setDataobject(null);
            AlbumDataHolder.setDataobject(null);
            mListener.goToProfile();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

}
