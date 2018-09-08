package mickeylina.creditpal.test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.aesthetic.Aesthetic;

import io.reactivex.disposables.Disposable;
import me.itangqi.waveloadingview.WaveLoadingView;
import mickeylina.creditpal.R;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ItemListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class UserDetails extends BottomSheetDialogFragment {

    public static UserDetails newInstance() {
        final UserDetails fragment = new UserDetails();
        final Bundle args = new Bundle();
//        args.putInt(ARG_ITEM_COUNT, itemCount);
        fragment.setArguments(args);
        return fragment;
    }

    public static UserDetails getInstance(){
        return new UserDetails(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_details_sheet, container, false);

        WaveLoadingView wave = view.findViewById(R.id.wave);
        Disposable disposable = Aesthetic.get().colorPrimary().subscribe(wave::setBackgroundColor);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
        } else {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
