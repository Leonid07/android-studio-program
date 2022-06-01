package com.progect.in_service;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.progect.in_service.regestrationsNewMan.Registration;

import java.util.Collections;

public class LoginAndReg extends AppCompatActivity {

    Button buttonPhoneAuth;
    private static final int RC_SIGN_IN = 101;
    public FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doPhoneLogin();
    }

    private void doPhoneLogin() {
        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                .setAvailableProviders(Collections.singletonList(
                        new AuthUI.IdpConfig.PhoneBuilder().build()))
                .setTheme(R.style.AppThemeLogo)
                .setLogo(R.drawable.sw_thumb_red)
                .build();

        startActivityForResult(intent, RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(user != null){
            Intent intent = new Intent(LoginAndReg.this, Registration.class);
            startActivity(intent);
        }
        if(user == null){
        if (requestCode == RC_SIGN_IN) {

    Intent intent = new Intent(LoginAndReg.this, MainActivity.class);
    startActivity(intent);

            IdpResponse idpResponse = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {

                Intent intent1 = new Intent(LoginAndReg.this, MainActivity.class);
                startActivity(intent1);

            }
                showAlertDialog(user);
            } else {
                Toast.makeText(getBaseContext(), "Phone Auth Failed", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void showAlertDialog(FirebaseUser user) {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                LoginAndReg.this);
        mAlertDialog.setTitle("Successfully Signed In");
        //mAlertDialog.setMessage(" Phone Number is " + user.getPhoneNumber());
        mAlertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        mAlertDialog.create();
        mAlertDialog.show();
    }

}
