package mickeylina.creditpal.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.aesthetic.Aesthetic;
import com.afollestad.materialdialogs.MaterialDialog;

import io.reactivex.disposables.Disposable;
import mickeylina.creditpal.Adapters.ActionListAdapter;
import mickeylina.creditpal.R;

import static android.content.Context.MODE_PRIVATE;

public class AdditionalServices extends Fragment {

    private boolean isDialogShowing;
    private final int REQUEST_CODE = 101;

    private MaterialDialog dCallForwarding;

    private BottomSheetDialog dialog;
    private Button btn_activate;
    private Button btn_deactivate;
    private TextView info;
    private String etc = "+251824";
    CardView cardview;
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actions, container, false);
//        cardview= view.findViewById(R.id.cardview68);
//        new Handler().postDelayed(this::startEnterAnimation,100);
        Resources res = getResources();
        String[] arrays = res.getStringArray(R.array.action_additional_names);
        @SuppressLint("Recycle")
        TypedArray icons = res.obtainTypedArray(R.array.icons_additional);

        ActionListAdapter listAdapter = new ActionListAdapter(getActivity(), arrays, icons);
        ListView mListView = view.findViewById(R.id.account_list_view);
        mListView.setAdapter(listAdapter);
        mListView.setOnItemClickListener((parent, view1, position, id) -> {
            if (position == 0) {
                dCallForwarding = new MaterialDialog.Builder(getContext())
                        .autoDismiss(false)
                        .title(R.string.call_forwarding)
                        .content(R.string.info_call_forwarding)
                        .positiveText(R.string.activate)
                        .positiveColorRes(R.color.green)
                        .neutralText(R.string.from_contacts)
                        .neutralColorRes(R.color.blue)
                        .negativeText(R.string.deactivate)
                        .negativeColorRes(R.color.red)
                        .inputRange(10, 13, getResources().getColor(R.color.red))
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .input(R.string.hint_phone, R.string.empty, true, (dialog, input) -> {
                            String pre = "*21*";
                            String number = dialog.getInputEditText().getText().toString();
                            String hash = "%23";
                            String dial = "tel:" + pre + number + hash;
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                            dialog.dismiss();
                        })
                        .onNeutral((dialog, which) -> {
                            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                            startActivityForResult(intent, REQUEST_CODE);
                        })
                        .onNegative((dialog, which) -> {
                            String pre = "%23";
                            String mid = "21";
                            String hash = "%23";
                            String dial = "tel:" + pre + mid + hash;
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                            dialog.dismiss();
                        })
                        .showListener(dialog -> isDialogShowing = true)
                        .dismissListener(dialog -> isDialogShowing = false)
                        .show();
            } if (position== 1){
                String hash = "%23";
                String pre = "*43";
                String no = "43";
                String activate = pre + hash;
                String deactivate = hash + no + hash;
                String info = getResources().getString(R.string.info_call_waiting);
                showConfirmationDialog(activate,deactivate,info);

            } if (position== 2){
                showSMSConfirmationDialog(getString(R.string.info_voice_mail),etc,"S1","U");
            } if (position== 3){
                showSMSConfirmationDialog(getString(R.string.info_mcn),etc,"A2","D2");
            } if (position== 4){
                showSMSConfirmationDialog(getString(R.string.info_reachability_alert),etc,"A1","D1");
            }

        });
        return view;

    }

    private void showSMSConfirmationDialog(String inform, final String to, final String activate, final String deactivate) {
        View sheetDialog = getActivity().getLayoutInflater().inflate(R.layout.confirm_sheet,null);
        info = sheetDialog.findViewById(R.id.textView2);
        info.setText(inform);
        btn_activate = sheetDialog.findViewById(R.id.buttonyes);
        btn_activate.setText(R.string.activate);
        btn_activate.setOnClickListener(v -> {
            sendSMS(to,activate);
            dialog.hide();
        });
        btn_deactivate = sheetDialog.findViewById(R.id.buttonno);
        btn_deactivate.setText(R.string.deactivate);
        btn_deactivate.setOnClickListener(v -> {
            sendSMS(to,deactivate);
            dialog.hide();
        });
        dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(sheetDialog);
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

    private void sendSMS(String to, String code) {

        Disposable aestheticDisposable = Aesthetic.get().colorPrimary().subscribe(color -> {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(to,null,code,null,null);
        Snackbar snackbar;
        snackbar=Snackbar.make(getView(),R.string.notification,Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(color);
        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(android.R.color.black));
        snackbar.show();
        });

    }

    private void showConfirmationDialog(final String codeActivate, final String codeDeactivate, String infoText) {
        View modalbottomsheet = getActivity().getLayoutInflater().inflate(R.layout.confirm_sheet, null);
        info = modalbottomsheet.findViewById(R.id.textView2);
        info.setText(infoText);
        btn_deactivate = modalbottomsheet.findViewById(R.id.buttonno);
        btn_deactivate.setText(R.string.deactivate);
        btn_deactivate.setOnClickListener(v -> {
            if (v.getId() == btn_deactivate.getId()) {
                String dial = "tel:"+ codeDeactivate;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                dialog.hide();
            }
        });
        btn_activate= modalbottomsheet.findViewById(R.id.buttonyes);
        btn_activate.setText(R.string.activate);
        btn_activate.setOnClickListener(v -> {
            if (v.getId() == btn_activate.getId()) {
                String dial = "tel:" + codeActivate;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                dialog.hide();
            }
        });
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
                                    dCallForwarding.getInputEditText().setText(num.replace("+251","0"));
                                }

                            }
                        }
                    }
                    break;
            }

        }
}

