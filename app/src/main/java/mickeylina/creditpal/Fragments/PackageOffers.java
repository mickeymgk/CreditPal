package mickeylina.creditpal.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import mickeylina.creditpal.Adapters.ActionListAdapter;
import mickeylina.creditpal.R;

public class PackageOffers extends Fragment {
    private OnPackageSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnPackageSelectedListener {
        void onPackageSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnPackageSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPackageSelectedListener");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actions, container, false);
        Resources res = getResources();
        String[] arrays = res.getStringArray(R.array.packages_names);
        @SuppressLint("Recycle")
        TypedArray icons = res.obtainTypedArray(R.array.icons_packages);
        ActionListAdapter listAdapter = new ActionListAdapter(getActivity(), arrays, icons);
        ListView mListView = view.findViewById(R.id.account_list_view);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Packages Offers");
        mListView.setAdapter(listAdapter);
        mListView.setOnItemClickListener((parent, view1, position, id) -> {
            if (position == 0){
                mCallback.onPackageSelected(0);
            }if (position ==1){
                mCallback.onPackageSelected(1);
            }

        });
        return view;
    }

}
