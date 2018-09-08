package mickeylina.creditpal.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.aesthetic.Aesthetic;
import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Objects;

import io.reactivex.disposables.Disposable;
import mickeylina.creditpal.Adapters.PackageListAdapter;
import mickeylina.creditpal.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     InternetPackages.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class VoicePackages extends Fragment  {

    public static VoicePackages getInstance(){
        return new VoicePackages(); }

//    private InternetPackages.SectionsPagerAdapter mSectionsPagerAdapter;

//    private ViewPager mViewPager;
    private ViewPager viewPager;
    private  FragmentPagerAdapter adapter;
    private VoicePackages.SectionsPagerAdapter mSectionsPagerAdapter;
    private Disposable m;


    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_purchases, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setSmoothScrollingEnabled(true);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_daily));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_weekly));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_monthly));
        m = Aesthetic.get().colorPrimary().subscribe(tabLayout::setSelectedTabIndicatorColor);
        new Handler().postDelayed(() -> {
            viewPager = view.findViewById(R.id.pager);
            YoYo.with(Techniques.SlideInDown).duration(300).playOn(viewPager);
//        the method getSupportFragmentManager must not be used here because we are already on a fragment
//        PagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
            mSectionsPagerAdapter = new VoicePackages.SectionsPagerAdapter(getChildFragmentManager());
            viewPager.setAdapter(mSectionsPagerAdapter);
//        viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        }, 450);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    static class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a Fragment (defined as a static inner class below).
            switch (position){
                case 0: return DailyFragment.newInstance(0);
                case 1: return WeeklyFragment.newInstance(2);
                case 2: return MonthlyFragment.newInstance(4);
            }
//            return NightlyFragment.newInstance(0);
            return null;
        }

        @Override
        public int getCount() {
            // Show 5 pages.
            return 3;
        }
    }


    /**==================================
    *========== DAILY PACKAGES==========
    * ==================================*/

    public static class DailyFragment extends Fragment{

    private TextView text;
    private Button yes;
    private Button no;
    private BottomSheetDialog dialog;
    private MaterialDialog sendConfirm;
    boolean isDialogShowing;
    private final int REQUEST_CODE = 101;

        public DailyFragment(){}

        public static DailyFragment newInstance(int sectionNumber) {
           DailyFragment fragment = new DailyFragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_actions, container, false);
            Resources res = getResources();
            @SuppressLint("Recycle")
            String[] amount = res.getStringArray(R.array.daily_sms);
            String[] costs = res.getStringArray(R.array.daily_sms_costs);

            PackageListAdapter listAdapter = new PackageListAdapter(getActivity(), amount, costs);
            ListView mListView = rootView.findViewById(R.id.account_list_view);
            mListView.setDivider(null);

            mListView.setAdapter(listAdapter);
            mListView.setOnItemClickListener((parent, view, position, id) -> {
              if(position==0){
                  TextView tv = view.findViewById(R.id.textAmount);
                  String textAmount = tv.getText().toString();
                  ShowConfirmDialog("tel:*999*1*1*3*1*1*1%23", textAmount);
              }
              if(position==1) {
                  TextView tv = view.findViewById(R.id.textAmount);
                  String textAmount = tv.getText().toString();
                  ShowConfirmDialog("tel:*999*1*1*3*1*2*1%23", textAmount);
              }
              if(position==2) {
                  TextView tv = view.findViewById(R.id.textAmount);
                  String textAmount = tv.getText().toString();
                  ShowConfirmDialog("tel:*999*1*1*3*1*3*1%23", textAmount);
              }

            });
            mListView.setOnItemLongClickListener((parent, view, position, id) -> {
                switch (position){
                    case 0:
                        TextView tv = view.findViewById(R.id.textAmount);
                        String textAmount = tv.getText().toString();
                        showSendConfirm("tel:*999*1*2*3*1*1*",textAmount);
                        break;
                    case 1:
                        TextView tv1 = view.findViewById(R.id.textAmount);
                        String textAmount1 = tv1.getText().toString();
                        showSendConfirm("tel:*999*1*2*3*1*2*",textAmount1);
                        break;
                    case 2:
                        TextView tv2 = view.findViewById(R.id.textAmount);
                        String textAmount2 = tv2.getText().toString();
                        showSendConfirm("tel:*999*1*2*3*1*3*",textAmount2);
                        break;
                }
                return true;
            });
            return rootView;
        }

        private void showSendConfirm(String code,String amount) {
            sendConfirm = new MaterialDialog.Builder(getContext())
                    .autoDismiss(false)
                    .title(R.string.gift_package)
                    .content( amount )
                    .positiveText(R.string.send)
                    .positiveColorRes(R.color.green)
                    .neutralText(R.string.from_contacts)
                    .neutralColorRes(R.color.blue)
                    .inputRange(10, 13, getResources().getColor(R.color.red))
                    .inputType(InputType.TYPE_CLASS_NUMBER)
                    .input(R.string.hint_phone, R.string.empty, true, (dialog, input) -> {
                        String preUSSD = code;
                        String posUSSD = "*1%23";
                        String phone = input.toString();
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(preUSSD+phone+posUSSD)));
                        dialog.dismiss();
                    })
                    .onNeutral((dialog, which) -> {
                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, REQUEST_CODE);
                    })
                    .showListener(dialog -> isDialogShowing = true)
                    .dismissListener(dialog -> isDialogShowing = false)
                    .show();

        }

        private void ShowConfirmDialog(String code,String amount) {
            View modalbottomsheet = getActivity().getLayoutInflater().inflate(R.layout.confirm_sheet, null);
            text = modalbottomsheet.findViewById(R.id.textView2);
            String dialogText = getResources().getString(R.string.dialog_internet).replace("amount", amount);
            text.setText(dialogText);
            yes = modalbottomsheet.findViewById(R.id.buttonyes);
            yes.setOnClickListener(v -> {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(code)));
                dialog.hide();
            });
            no = modalbottomsheet.findViewById(R.id.buttonno);
            no.setPadding(0,0,0,0);
            no.setOnClickListener(v -> dialog.dismiss());
            dialog = new BottomSheetDialog(getActivity());
            dialog.setContentView(modalbottomsheet);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.show();
            SharedPreferences notifPref = getActivity().getSharedPreferences("NOTIFICATION_PREFS", MODE_PRIVATE);
            if (notifPref.getBoolean("vibrations_enabled",true)){
                Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                long[] pattern = {0, 40, 80, 40, 80};
                v.vibrate(pattern, -1);
            }
        }

        @Override
        @Nullable
        public void onActivityResult(int reqCode, int resultCode, Intent data) {
            super.onActivityResult(reqCode, resultCode, data);
            switch (reqCode) {
                case (REQUEST_CODE):
                    if (resultCode == Activity.RESULT_OK) {
                        Uri contactData = data.getData();
                        Cursor c = getActivity().getContentResolver().query(contactData, null, null, null, null);
                        if (c != null && c.moveToFirst()) {
                            String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                            String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                            String num = "";
                            if (Integer.valueOf(hasNumber) == 1) {
                                Cursor numbers = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                                if (numbers != null) {
                                    while (numbers.moveToNext()) {
                                        num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    }
                                }
                                if(isDialogShowing){
                                    sendConfirm.getInputEditText().setText(num.replace("+251","0"));
                                }

                            }
                        }
                    }
                    break;
            }

        }

    }


    /**====================================
     *========== WEEKLY PACKAGES==========
     *=====================================*/
    //    weekly fragment
    public static  class WeeklyFragment extends Fragment{

        private TextView text;
        private Button yes;
        private Button no;
        private BottomSheetDialog dialog;
        private MaterialDialog sendConfirm;
        boolean isDialogShowing;
        private final int REQUEST_CODE = 101;

        public WeeklyFragment(){}

        public static WeeklyFragment newInstance(int sectionNumber) {
            WeeklyFragment fragment = new WeeklyFragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_actions, container, false);
            Resources res = getResources();
            @SuppressLint("Recycle")
            String[] amount = res.getStringArray(R.array.weekly_sms);
            String[] costs = res.getStringArray(R.array.weekly_sms_costs);

            PackageListAdapter listAdapter = new PackageListAdapter(getActivity(), amount, costs);
            ListView mListView = rootView.findViewById(R.id.account_list_view);
            mListView.setDivider(null);
            mListView.setAdapter(listAdapter);
            mListView.setOnItemClickListener((parent, view, position, id) -> {
                if(position==0){
                    TextView tv = view.findViewById(R.id.textAmount);
                    String textAmount = tv.getText().toString();
                    ShowConfirmDialog("tel:*999*1*1*3*2*1*1%23", textAmount);
                }
                if(position==1) {
                    TextView tv = view.findViewById(R.id.textAmount);
                    String textAmount = tv.getText().toString();
                    ShowConfirmDialog("tel:*999*1*1*3*2*2*1%23", textAmount);
                }
            });
            mListView.setOnItemLongClickListener((parent, view, position, id) -> {
                switch (position){
                    case 0:
                        TextView tv = view.findViewById(R.id.textAmount);
                        String textAmount = tv.getText().toString();
                        showSendConfirm("tel:*999*1*2*3*2*1*",textAmount);
                        break;
                    case 1:
                        TextView tv1= view.findViewById(R.id.textAmount);
                        String textAmount1 = tv1.getText().toString();
                        showSendConfirm("tel:*999*1*2*3*2*2*",textAmount1);
                        break;
                }
                return true;
            });
            return rootView;
        }

        private void showSendConfirm(String code,String amount) {
            sendConfirm = new MaterialDialog.Builder(getContext())
                    .autoDismiss(false)
                    .title(R.string.gift_package)
                    .content( amount )
                    .positiveText(R.string.send)
                    .positiveColorRes(R.color.green)
                    .neutralText(R.string.from_contacts)
                    .neutralColorRes(R.color.blue)
                    .inputRange(10, 13, getResources().getColor(R.color.red))
                    .inputType(InputType.TYPE_CLASS_NUMBER)
                    .input(R.string.hint_phone, R.string.empty, true, (dialog, input) -> {
                        String preUSSD = code;
                        String posUSSD = "*1%23";
                        String phone = input.toString();
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(preUSSD+phone+posUSSD)));
                        dialog.dismiss();
                    })
                    .onNeutral((dialog, which) -> {
                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, REQUEST_CODE);
                    })
                    .showListener(dialog -> isDialogShowing = true)
                    .dismissListener(dialog -> isDialogShowing = false)
                    .show();

        }

        private void ShowConfirmDialog(String code,String amount) {
            View modalbottomsheet = getActivity().getLayoutInflater().inflate(R.layout.confirm_sheet, null);
            text = modalbottomsheet.findViewById(R.id.textView2);
            String dialogText = getResources().getString(R.string.dialog_internet).replace("amount", amount);
            text.setText(dialogText);
            yes = modalbottomsheet.findViewById(R.id.buttonyes);
            yes.setOnClickListener(v -> {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(code)));
                dialog.hide();
            });
            no = modalbottomsheet.findViewById(R.id.buttonno);
            no.setPadding(0,0,0,0);
            no.setOnClickListener(v -> dialog.dismiss());
            dialog = new BottomSheetDialog(getActivity());
            dialog.setContentView(modalbottomsheet);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.show();
            SharedPreferences notifPref = getActivity().getSharedPreferences("NOTIFICATION_PREFS", MODE_PRIVATE);
            if (notifPref.getBoolean("vibrations_enabled",true)){
                Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                long[] pattern = {0, 40, 80, 40, 80};
                v.vibrate(pattern, -1);
            }
        }
        @Override
        @Nullable
        public void onActivityResult(int reqCode, int resultCode, Intent data) {
            super.onActivityResult(reqCode, resultCode, data);
            switch (reqCode) {
                case (REQUEST_CODE):
                    if (resultCode == Activity.RESULT_OK) {
                        Uri contactData = data.getData();
                        Cursor c = getActivity().getContentResolver().query(contactData, null, null, null, null);
                        if (c != null && c.moveToFirst()) {
                            String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                            String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                            String num = "";
                            if (Integer.valueOf(hasNumber) == 1) {
                                Cursor numbers = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                                if (numbers != null) {
                                    while (numbers.moveToNext()) {
                                        num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    }
                                }
                                if(isDialogShowing){
                                    sendConfirm.getInputEditText().setText(num.replace("+251","0"));
                                }

                            }
                        }
                    }
                    break;
            }

        }
    }

    /**====================================
     *========== MONTHLY PACKAGES==========
     *=====================================*/
    //    monthly fragment
    public static  class MonthlyFragment extends Fragment{

        private TextView text;
        private Button yes;
        private Button no;
        private BottomSheetDialog dialog;
        private MaterialDialog sendConfirm;
        boolean isDialogShowing;
        private final int REQUEST_CODE = 101;


        public static MonthlyFragment newInstance(int sectionNumber) {
            MonthlyFragment fragment = new MonthlyFragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_actions, container, false);
            Resources res = getResources();
            @SuppressLint("Recycle")
            String[] amount = res.getStringArray(R.array.monthly_sms);
            String[] costs = res.getStringArray(R.array.monthly_sms_costs);

            PackageListAdapter listAdapter = new PackageListAdapter(getActivity(), amount, costs);
            ListView mListView = rootView.findViewById(R.id.account_list_view);
            mListView.setDivider(null);
            mListView.setAdapter(listAdapter);
            mListView.setOnItemClickListener((parent, view, position, id) -> {
                if(position==0){
                    TextView tv = view.findViewById(R.id.textAmount);
                    String textAmount = tv.getText().toString();
                    ShowConfirmDialog("tel:*999*1*1*3*3*1*1%23", textAmount);
                }if(position==1) {
                    TextView tv = view.findViewById(R.id.textAmount);
                    String textAmount = tv.getText().toString();
                    ShowConfirmDialog("tel:*999*1*1*3*3*2*1%23", textAmount);
                }
            });
            mListView.setOnItemLongClickListener((parent, view, position, id) -> {
                switch (position){
                    case 0:
                        TextView tv = view.findViewById(R.id.textAmount);
                        String textAmount = tv.getText().toString();
                        showSendConfirm("tel:*999*1*2*3*3*1*",textAmount);
                        break;
                    case 1:
                        TextView tv1 = view.findViewById(R.id.textAmount);
                        String textAmount1 = tv1.getText().toString();
                        showSendConfirm("tel:*999*1*2*3*3*2*",textAmount1);
                        break;
                }
                return true;
            });
            return rootView;
        }

        private void showSendConfirm(String code,String amount) {
            sendConfirm = new MaterialDialog.Builder(getContext())
                    .autoDismiss(false)
                    .title(R.string.gift_package)
                    .content( amount )
                    .positiveText(R.string.send)
                    .positiveColorRes(R.color.green)
                    .neutralText(R.string.from_contacts)
                    .neutralColorRes(R.color.blue)
                    .inputRange(10, 13, getResources().getColor(R.color.red))
                    .inputType(InputType.TYPE_CLASS_NUMBER)
                    .input(R.string.hint_phone, R.string.empty, true, (dialog, input) -> {
                        String preUSSD = code;
                        String posUSSD = "*1%23";
                        String phone = input.toString();
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(preUSSD+phone+posUSSD)));
                        dialog.dismiss();
                    })
                    .onNeutral((dialog, which) -> {
                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, REQUEST_CODE);
                    })
                    .showListener(dialog -> isDialogShowing = true)
                    .dismissListener(dialog -> isDialogShowing = false)
                    .show();
        }

        private void ShowConfirmDialog(String code,String amount) {
            View modalBottomSheet = getActivity().getLayoutInflater().inflate(R.layout.confirm_sheet, null);
            text = modalBottomSheet.findViewById(R.id.textView2);
            String dialogText = getResources().getString(R.string.dialog_internet).replace("amount", amount);
            text.setText(dialogText);
            yes = modalBottomSheet.findViewById(R.id.buttonyes);
            yes.setOnClickListener(v -> {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(code)));
                dialog.hide();
            });
            no = modalBottomSheet.findViewById(R.id.buttonno);
            no.setPadding(0,0,0,0);
            no.setOnClickListener(v -> dialog.dismiss());
            dialog = new BottomSheetDialog(getActivity());
            dialog.setContentView(modalBottomSheet);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.show();
            SharedPreferences notifPref = getActivity().getSharedPreferences("NOTIFICATION_PREFS", MODE_PRIVATE);
            if (notifPref.getBoolean("vibrations_enabled",true)){
                Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                long[] pattern = {0, 40, 80, 40, 80};
                v.vibrate(pattern, -1);
            }
        }

        @Override
        @Nullable
        public void onActivityResult(int reqCode, int resultCode, Intent data) {
            super.onActivityResult(reqCode, resultCode, data);
            switch (reqCode) {
                case (REQUEST_CODE):
                    if (resultCode == Activity.RESULT_OK) {
                        Uri contactData = data.getData();
                        Cursor c = Objects.requireNonNull(getActivity()).getContentResolver().query(contactData, null, null, null, null);
                        if (c != null && c.moveToFirst()) {
                            String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                            String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                            String num = "";
                            if (Integer.valueOf(hasNumber) == 1) {
                                Cursor numbers = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                                if (numbers != null) {
                                    while (numbers.moveToNext()) {
                                        num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    }
                                }
                                if(isDialogShowing){
                                    sendConfirm.getInputEditText().setText(num.replace("+251","0"));
                                }
                            }
                        }
                    }
                    break;
            }
        }
    }

//    setting up a viewpager class for the tab items

    class ViewPagerAdapter extends FragmentPagerAdapter {
        int mNumOfTabs;

        public ViewPagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    DailyFragment tabNightly = new DailyFragment();
                    return tabNightly;
                case 1:
                    WeeklyFragment tabWeekly = new WeeklyFragment();
                    return tabWeekly;
                case 2:
                    MonthlyFragment tabMonthly = new MonthlyFragment();
                    return tabMonthly;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() { return mNumOfTabs; }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        m.dispose();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        m.dispose();
        super.onDetach();
    }
}
