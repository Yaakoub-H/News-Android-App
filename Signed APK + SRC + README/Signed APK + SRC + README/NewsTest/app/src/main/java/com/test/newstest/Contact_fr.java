package com.test.newstest;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Contact_fr  extends Fragment {
    private View view;

    private int layout_id;
    public Contact_fr(int layout_id){
        this.layout_id = layout_id;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(layout_id, container, false);
        contact();
        return view;
    }
    public void contact(){
        View root = getView();
        if(root!= null) {
            Button submit = getView().findViewById(R.id.send_btn);
            TextView name = getView().findViewById(R.id.name);
            TextView email = getView().findViewById(R.id.email);
            TextView phone = getView().findViewById(R.id.phone);
            TextView msg = getView().findViewById(R.id.message);

            AlertDialog.Builder MsgBox = new AlertDialog.Builder(getContext());
            MsgBox.setPositiveButton("OK", null);

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String error_msg = "";

                    if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches())
                        error_msg += String.format("%s\n", getString(R.string.invalid_email));

                    if (phone.getText().length() < 4 && !Patterns.PHONE.matcher(phone.getText()).matches())
                        error_msg += String.format("%s\n", getString(R.string.invalid_phone));


                    if (TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(phone.getText()) || TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(msg.getText()))
                        error_msg += String.format("%s\n", getString(R.string.check_all));

                    if (!error_msg.equals("")) {
                        MsgBox.setTitle(getString(R.string.error));
                        MsgBox.setMessage(error_msg);
                        MsgBox.show();
                        return;
                    }

                    try {
                        FirebaseDatabase node = FirebaseDatabase.getInstance("https://contact-forms-aba55-default-rtdb.europe-west1.firebasedatabase.app");
                        DatabaseReference reference = node.getReference("forms");
                        String key = reference.push().getKey();

                        reference.child(key).child("name").setValue(name.getText().toString());
                        reference.child(key).child("email").setValue(email.getText().toString());
                        reference.child(key).child("phone").setValue(phone.getText().toString());
                        reference.child(key).child("message").setValue(msg.getText().toString());

                        email.setText("");
                        phone.setText("");
                        msg.setText("");
                        name.setText("");

                        MsgBox.setTitle(R.string.success);
                        MsgBox.setMessage(getString(R.string.msg_sent));
                        MsgBox.show();

                    } catch (Exception e) {
                        MsgBox.setTitle(getString(R.string.error));
                        MsgBox.setMessage(getString(R.string.connection_error));
                        MsgBox.show();
                    }

                }
            };
            submit.setOnClickListener(clickListener);
        }
        }
    }

