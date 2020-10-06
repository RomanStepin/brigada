package com.example.pet;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.textInputEditText_email_reg)
    EditText textInputEditText_email_reg;
    @BindView(R.id.textInputEditText_password_reg)
    EditText textInputEditText_password_reg;
    @BindView(R.id.textInputEditText_password_confirm_reg)
    EditText textInputEditText_password_confirm_reg;

    @BindView(R.id.button_registration_reg)
    Button button_registration_reg;

    @BindView(R.id.textInputLayout_email_reg)
    TextInputLayout textInputLayout_email_reg;
    @BindView(R.id.textInputLayout_password_reg)
    TextInputLayout textInputLayout_password_reg;
    @BindView(R.id.textInputLayout_password_confirm_reg)
    TextInputLayout textInputLayout_password_confirm_reg;

    RegistrationModelView registrationModelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        button_registration_reg.setOnClickListener(this);

        registrationModelView = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RegistrationModelViewImpl.class);

        initialLiveData();



    }

    public static void startInNewTask(Context context)
    {
        Intent starter = new Intent(context, RegistrationActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(starter);
    }

    boolean empty_editText_check()
    {
        boolean empty_editText = false;
        if (textInputEditText_email_reg.getText().toString().equals(""))
        {
            empty_editText = true;
            textInputLayout_email_reg.setError(getString(R.string.empty_editText));
        } else if (!textInputEditText_email_reg.getText().toString().contains("@")) {
            textInputLayout_email_reg.setError(getString(R.string.bad_email));
        } else {
            textInputLayout_email_reg.setError(null);
        }

        if (textInputEditText_password_reg.getText().toString().equals(""))
        {
            empty_editText = true;
            textInputLayout_password_reg.setError(getString(R.string.empty_editText));
        } else {
            textInputLayout_password_reg.setError(null);
        }

        if (textInputEditText_password_confirm_reg.getText().toString().equals(""))
        {
            empty_editText = true;
            textInputLayout_password_confirm_reg.setError(getString(R.string.empty_editText));
        } else if (!textInputEditText_password_confirm_reg.getText().toString().equals(textInputEditText_password_reg.getText().toString())) {
            empty_editText = true;
            textInputLayout_password_confirm_reg.setError(getString(R.string.password_not_confirmed));
        } else {
            textInputLayout_password_confirm_reg.setError(null);
        }
        return empty_editText;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_registration_reg:
                if (!empty_editText_check()) {
                    registrationModelView.registrationNewUser(textInputEditText_email_reg.getText().toString(), textInputEditText_password_reg.getText().toString());
                }
                break;
        }
    }

    void initialLiveData()
    {
        registrationModelView.registrationError().observe(this, registrationError -> {

            if (registrationError.getClass() == FirebaseAuthWeakPasswordException.class)
                textInputLayout_password_reg.setError(getString(R.string.bad_password));
            if (registrationError.getClass() == FirebaseAuthUserCollisionException.class)
                textInputLayout_email_reg.setError(getString(R.string.email_already_registered));
            if (registrationError.getClass() == FirebaseAuthInvalidCredentialsException.class)
                textInputLayout_email_reg.setError(getString(R.string.bad_email2));

            Log.d("LOGGG", registrationError.getClass().toString());
        });

        registrationModelView.registrationSuccessful().observe(this, isSuccessful -> {
            if (isSuccessful) {
                new MaterialAlertDialogBuilder(this)
                        .setTitle(getString(R.string.email_confirm_dialog_title))
                        .setMessage(getString(R.string.email_confirm_dialog_message))
                        .setPositiveButton("OK", null)
                        .setOnDismissListener(dialog ->{ finish(); })
                        .show();
            }
        });
    }
}

