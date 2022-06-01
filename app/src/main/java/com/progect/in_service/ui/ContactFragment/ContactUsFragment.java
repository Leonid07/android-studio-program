package com.progect.in_service.ui.ContactFragment;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.progect.in_service.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class ContactUsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();
    private EditText description;
    private EditText price;
    private Spinner spinner;
    private int STORAGE_PERMISSION_CODE = 1;
    private Button Btn_new_task;

    private FirebaseFirestore firebaseFirestore;

    public ContactUsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact_us, null, false);

        FirebaseMessaging.getInstance().subscribeToTopic("nitification");

        myRef = FirebaseDatabase.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        Btn_new_task = (Button) v.findViewById(R.id.button4);
        description = (EditText) v.findViewById(R.id.description);
        price = (EditText) v.findViewById(R.id.editText3);
        spinner = (Spinner) v.findViewById(R.id.spinner2);
        final Spinner spinner1 = v.findViewById(R.id.mod);
        final Spinner spinner2 = v.findViewById(R.id.Mark);

        View.OnClickListener tbNewTaskOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (description.getText().length() >= 1 && price.getText().length() >= 1) {
                    Map<String, Object> client = new HashMap<>();
                    client.put("name", spinner.getSelectedItem().toString());
                    client.put("Marka", spinner1.getSelectedItem().toString());
                    client.put("Model", spinner2.getSelectedItem().toString());
                    client.put("description", description.getText().toString());
                    client.put("price", price.getText().toString());
                    client.put("ID", user.getPhoneNumber());
                    firebaseFirestore.collection("Products").document(user.getPhoneNumber()).set(client);
                    Toast.makeText(getActivity(), "Заказ успешно опубликован",
                            Toast.LENGTH_SHORT).show();
                }
                if (description.getText().length() == 0) {
                    Toast.makeText(getActivity(), "Укажите описание проблемы", Toast.LENGTH_SHORT).show();
                }
                if (price.getText().length() == 0) {
                    Toast.makeText(getActivity(), "Укажите цену за выполнение работы", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Btn_new_task.setOnClickListener(tbNewTaskOnClick);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.typeWork, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //String gender;
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.MARKA, R.layout.support_simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner1.setAdapter(arrayAdapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> s = Arrays.asList(getResources().getStringArray(R.array.Audi));
                List<String> ss = Arrays.asList(getResources().getStringArray(R.array.BMW));
                List<String> sss = Arrays.asList(getResources().getStringArray(R.array.Acura));
                List<String> ssss = Arrays.asList(getResources().getStringArray(R.array.AlfaRomeo));
                List<String> s1 = Arrays.asList(getResources().getStringArray(R.array.AstonMartin));
                List<String> s2 = Arrays.asList(getResources().getStringArray(R.array.Bentley));
                List<String> s3 = Arrays.asList(getResources().getStringArray(R.array.Bugatti));
                List<String> s4 = Arrays.asList(getResources().getStringArray(R.array.Buick));
                List<String> s5 = Arrays.asList(getResources().getStringArray(R.array.Cadillac));
                List<String> s6 = Arrays.asList(getResources().getStringArray(R.array.Chery));
                List<String> s7 = Arrays.asList(getResources().getStringArray(R.array.Chevrolet));
                List<String> s8 = Arrays.asList(getResources().getStringArray(R.array.Chrysler));
                List<String> s9 = Arrays.asList(getResources().getStringArray(R.array.Citroen));
                List<String> s10 = Arrays.asList(getResources().getStringArray(R.array.Dacia));
                List<String> s11 = Arrays.asList(getResources().getStringArray(R.array.Daewoo));
                List<String> s12 = Arrays.asList(getResources().getStringArray(R.array.Daihatsu));
                List<String> s13 = Arrays.asList(getResources().getStringArray(R.array.Datsun));
                List<String> s14 = Arrays.asList(getResources().getStringArray(R.array.Dodge));
                List<String> s15 = Arrays.asList(getResources().getStringArray(R.array.DS));
                List<String> s16 = Arrays.asList(getResources().getStringArray(R.array.Ferrari));
                List<String> s17 = Arrays.asList(getResources().getStringArray(R.array.Fiat));
                List<String> s18 = Arrays.asList(getResources().getStringArray(R.array.Fisker));
                List<String> s19 = Arrays.asList(getResources().getStringArray(R.array.Ford));
                List<String> s20 = Arrays.asList(getResources().getStringArray(R.array.Geely));
                List<String> s21 = Arrays.asList(getResources().getStringArray(R.array.Genesis));
                List<String> s22 = Arrays.asList(getResources().getStringArray(R.array.GMC));
                List<String> s23 = Arrays.asList(getResources().getStringArray(R.array.Honda));
                List<String> s24 = Arrays.asList(getResources().getStringArray(R.array.Hummer));
                List<String> s25 = Arrays.asList(getResources().getStringArray(R.array.Hyundai));
                List<String> s26 = Arrays.asList(getResources().getStringArray(R.array.Infiniti));
                List<String> s27 = Arrays.asList(getResources().getStringArray(R.array.Isuzu));
                List<String> s28 = Arrays.asList(getResources().getStringArray(R.array.Iveco));
                List<String> s29 = Arrays.asList(getResources().getStringArray(R.array.Jaguar));
                List<String> s30 = Arrays.asList(getResources().getStringArray(R.array.Jeep));
                List<String> s31 = Arrays.asList(getResources().getStringArray(R.array.Kia));
                List<String> s32 = Arrays.asList(getResources().getStringArray(R.array.Koenigsegg));
                List<String> s33 = Arrays.asList(getResources().getStringArray(R.array.Lamborghini));
                List<String> s34 = Arrays.asList(getResources().getStringArray(R.array.Lancia));
                List<String> s35 = Arrays.asList(getResources().getStringArray(R.array.LandRover));
                List<String> s36 = Arrays.asList(getResources().getStringArray(R.array.Lexus));
                List<String> s37 = Arrays.asList(getResources().getStringArray(R.array.Lincoln));
                List<String> s38 = Arrays.asList(getResources().getStringArray(R.array.Lotus));
                List<String> s39 = Arrays.asList(getResources().getStringArray(R.array.Maserati));
                List<String> s40 = Arrays.asList(getResources().getStringArray(R.array.Mazda));
                List<String> s41 = Arrays.asList(getResources().getStringArray(R.array.MercedesBenz));
                List<String> s42 = Arrays.asList(getResources().getStringArray(R.array.Mercury));
                List<String> s43 = Arrays.asList(getResources().getStringArray(R.array.MG));
                List<String> s44 = Arrays.asList(getResources().getStringArray(R.array.Mini));
                List<String> s45 = Arrays.asList(getResources().getStringArray(R.array.Lada));
                List<String> s46 = Arrays.asList(getResources().getStringArray(R.array.Toyota));
                List<String> s47 = Arrays.asList(getResources().getStringArray(R.array.Nissan));
                List<String> s48 = Arrays.asList(getResources().getStringArray(R.array.Renault));
                List<String> s49 = Arrays.asList(getResources().getStringArray(R.array.Volkswagen));
                List<String> s50 = Arrays.asList(getResources().getStringArray(R.array.Mitsubishi));
                List<String> s51 = Arrays.asList(getResources().getStringArray(R.array.Ravon));
                List<String> s52 = Arrays.asList(getResources().getStringArray(R.array.JAC));
                if (position == 0) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 1) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ss);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 2) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, sss);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 3) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ssss);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 4) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s1);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 5) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s2);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 6) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s3);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 7) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s4);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 8) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s5);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 9) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s6);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 10) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s7);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 11) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s8);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 12) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s9);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 13) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s10);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 14) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s11);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 15) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s12);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 16) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s13);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 17) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s14);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 18) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s15);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 19) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s16);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 20) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s17);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 21) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s18);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 22) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s19);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 23) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s20);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 24) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s21);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 25) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s22);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 26) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s23);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 27) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s24);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 28) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s25);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 29) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s26);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 30) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s27);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 31) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s28);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 32) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s29);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 33) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s30);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 34) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s31);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 35) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s32);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 36) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s33);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 37) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s34);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 38) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s35);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 39) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s36);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 40) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s37);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 41) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s38);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 42) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s39);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 43) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s40);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 44) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s41);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 45) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s42);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 46) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s43);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 47) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s44);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 48) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s45);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 49) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s46);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 50) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s47);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 51) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s48);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 52) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s49);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 53) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s50);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 54) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s51);
                    spinner2.setAdapter(dayadapter);
                }
                if (position == 55) {
                    ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, s52);
                    spinner2.setAdapter(dayadapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}