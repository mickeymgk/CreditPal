package mickeylina.creditpal.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;

import mickeylina.creditpal.Adapters.ActionListAdapter;
import mickeylina.creditpal.R;

public class AccountManagement extends Fragment {

    private final static int REQUEST_CODE = 101;
    private MaterialDialog dRechargeOthers;
    private MaterialDialog dSendCredit;
    private MaterialDialog dCallMeBack;
    private boolean isDialogOpenRecharge;
    private boolean isDialogOpenCMB;
    private boolean isDialogOpenSend;
    private static final String TAG = "MainActivity";
    CardView cardview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actions, container, false);
//        List<String> actionLists = Arrays.asList(getResources().getStringArray(R.array.action_names));
//        mAdView = view.findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
        Resources res = getResources();
        @SuppressLint("Recycle")
        String[] arrays = res.getStringArray(R.array.action_names);
        TypedArray icons = res.obtainTypedArray(R.array.icons_account);

        ActionListAdapter listAdapter = new ActionListAdapter(getActivity(), arrays, icons);
        ListView mListView = view.findViewById(R.id.account_list_view);
        mListView.setAdapter(listAdapter);
     
        mListView.setOnItemClickListener((parent, view1, position, id) -> {
            if (position == 0) {
                /** recharge */
                new MaterialDialog.Builder(getContext())
                        .title(R.string.recharge)
                        .positiveText(R.string.recharge)
                        .positiveColor(getResources().getColor(R.color.green))
                        .inputRangeRes(13, 18,R.color.red)
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .input(R.string.hint_recharge, R.string.empty, (dialog, input) -> {
                            String ladder = "%23";
                            String pre = "*805*";
                            String input1 = "tel:" + pre + input + ladder;
                            startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(input1)));
                        })
                        .show();
            } if (position== 1 ){
                /** recharge for others*/
                dRechargeOthers = new MaterialDialog.Builder(getContext())
                        .autoDismiss(false)
                        .title(R.string.recharge_for_others)
                        .positiveText(R.string.recharge)
                        .positiveColor(getResources().getColor(R.color.green))
                        .neutralText(R.string.from_contacts)
                        .neutralColor(getResources().getColor(R.color.blue))
                        .customView(R.layout.dialog_others,true)
                        .onPositive((dialog, which) -> {
                            String phoneNumber = ((EditText) dialog.findViewById(R.id.input_hidden_numbers)).getText().toString();
                            String hiddenNumber = ((EditText) dialog.findViewById(R.id.input_phone_number)).getText().toString();
                            String pre = "*805*2";
                            String hash = "%23";
                            String dial = "tel:" + pre + phoneNumber + hiddenNumber + hash;
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                        })
                        .onNeutral((dialog, which) -> {
//                                  if (id== R.id.input_phone_number){
                              Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                              startActivityForResult(intent, REQUEST_CODE);
//                                  }
                        }).showListener(dialog -> isDialogOpenRecharge = true).dismissListener(dialog -> isDialogOpenRecharge = false)
                        .show();
            }if (position== 2){
                /** Remaining balance*/
                String pre= "*804%23";
                String dial = "tel:" + pre;
                startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
//                showStatusSnackBar();
            }if (position== 3){
                /** Call me back*/
                // TODO: 8/6/2018 Add a contact chooser see you tomorrow goodnight
                dCallMeBack = new MaterialDialog.Builder(getContext())
                        .autoDismiss(false)
                        .title(R.string.call_me_back)
                        .positiveColor(getResources().getColor(R.color.green))
                        .positiveText(R.string.send)
                        .neutralText(R.string.from_contacts)
                        .neutralColorRes(R.color.blue)
                        .inputRange(10,13,getResources().getColor(R.color.red))
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .input(R.string.hint_phone,R.string.empty,true, (dialog, input) -> {
                            String pre = "*807*";
                            String hash = "%23";
                            String dial = "tel:" + pre + input + hash ;
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));

                        })
                        .onPositive((dialog, which) -> {

                        })
                        .onNeutral((dialog, which) -> {
                            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                            startActivityForResult(intent, REQUEST_CODE);
                        }).showListener(dialog -> isDialogOpenCMB=true).dismissListener(dialog -> isDialogOpenCMB=false).show();
            } if (position== 4){
                /** send credit*/
                dSendCredit = new MaterialDialog.Builder(getContext())
                        .autoDismiss(false)
                        .customView(R.layout.dialog_credit,true)
                        .title(R.string.send_credit)
                        .positiveColorRes(R.color.green)
                        .positiveText(R.string.send)
                        .neutralColorRes(R.color.blue)
                        .neutralText(R.string.from_contacts)
                        .onPositive((dialog, which) -> {
                        EditText amount = (EditText) dialog.findViewById(R.id.input_hidden_numbers);
                        amount.setHint(R.string.hint_amount);
                        String pre = "*806*";
                        String hash = "%23";
                        String star = "*";
                        String amountInput = amount.getText().toString();
                        String phoneNumber = ((EditText)dialog.findViewById(R.id.input_phone_number)).getText().toString();
                        String dial = "tel:" + pre + phoneNumber + star + amountInput + hash ;
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                        })
                        .onNeutral((dialog, which) -> {
                            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                            startActivityForResult(intent, REQUEST_CODE);
                        }).showListener(dialog -> isDialogOpenSend = true).dismissListener(dialog -> isDialogOpenSend = false)
                        .show();
            } if (position == 5){
                String code="*111%23";
                String dial = "tel:"+code;
                startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
            }
        });
        return view;
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
                            if(isDialogOpenRecharge){
                                ((EditText) dRechargeOthers.findViewById(R.id.input_phone_number)).setText(num.replace("+251","0"));
                            } if (isDialogOpenCMB){
                                assert dCallMeBack.getInputEditText() != null;
                                dCallMeBack.getInputEditText().setText(num.replace("+251","0"));
                            } if (isDialogOpenSend){
                                ((EditText) dSendCredit.findViewById(R.id.input_phone_number)).setText(num.replace("+251","0"));
                            }

                        }
                    }
                }
                break;
        }

    }

}