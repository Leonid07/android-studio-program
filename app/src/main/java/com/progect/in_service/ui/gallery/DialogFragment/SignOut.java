package com.progect.in_service.ui.gallery.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.progect.in_service.LoginAndReg;

public class SignOut extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = "Вы действительно хотите выйти из аккаунта?";
        String button1String = "да";
        String button2String = "нет";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message); // сообщение
        builder.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginAndReg.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setCancelable(true);

        return builder.create();
    }
}