package com.parse.starter;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GalleryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class GalleryFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    String imageURL;
    public GalleryFragment() {
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
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // for each folder processing then go with image url to assign to image
        ImageView boy = (ImageView) getActivity().findViewById(R.id.boy2);
        boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.boy2);
                ImageURLHolderClass.getInstance().setImageURL(uri.toString());
                getActivity().getFragmentManager().popBackStack();
                getActivity().getFragmentManager().popBackStack();


            }
        });
        ImageView boy1 = (ImageView) getActivity().findViewById(R.id.boy1);
        boy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.boy1);
                ImageURLHolderClass.getInstance().setImageURL(uri.toString());
                getActivity().getFragmentManager().popBackStack();
                getActivity().getFragmentManager().popBackStack();
            }
        });
        ImageView girl2 = (ImageView) getActivity().findViewById(R.id.girl2);
        girl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.girl2);
                ImageURLHolderClass.getInstance().setImageURL(uri.toString());
                getActivity().getFragmentManager().popBackStack();
                getActivity().getFragmentManager().popBackStack();
            }
        });
        ImageView girl3 = (ImageView) getActivity().findViewById(R.id.imagegirl3);
        girl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.girl3);
                ImageURLHolderClass.getInstance().setImageURL(uri.toString());
                getActivity().getFragmentManager().popBackStack();
                getActivity().getFragmentManager().popBackStack();
            }
        });

        ImageView baby = (ImageView) getActivity().findViewById(R.id.imageBaby);
        baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.baby);
                ImageURLHolderClass.getInstance().setImageURL(uri.toString());
                getActivity().getFragmentManager().popBackStack();
                getActivity().getFragmentManager().popBackStack();
            }
        });

        ImageView chef_icon = (ImageView) getActivity().findViewById(R.id.chef_icon);
        chef_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.chef_icon);
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
