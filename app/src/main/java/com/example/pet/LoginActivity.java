package com.example.pet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.textInputEditText_email)
    EditText textInputEditText_email;
    @BindView(R.id.textInputEditText_password)
    EditText textInputEditText_password;
    @BindView(R.id.button_authorization)
    Button button_authorization;
    @BindView(R.id.button_registration)
    Button button_registration;
    @BindView(R.id.button_password_recovery)
    Button button_password_recovery;

    @BindView(R.id.textInputLayout_email)
    TextInputLayout textInputLayout_email;
    @BindView(R.id.textInputLayout_password)
    TextInputLayout textInputLayout_password;

    LoginViewModel loginViewModel;

    MaterialAlertDialogBuilder progressDialogBuilder;
    AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progressDialogBuilder = new MaterialAlertDialogBuilder(this).setView(R.layout.progress_layout);
        progressDialog = progressDialogBuilder.create();

        button_registration.setOnClickListener(this);
        button_authorization.setOnClickListener(this);
        button_password_recovery.setOnClickListener(this);

        loginViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModelImpl.class);

        initialLiveData();

        loginViewModel.checkCurrentUser();
    }

    public static void startInNewTask(Context context)
    {
        Intent starter = new Intent(context, LoginActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
    }

    boolean empty_editText_check()
    {
        boolean empty_editText = false;
        if (textInputEditText_email.getText().toString().equals(""))
        {
            empty_editText = true;
            textInputLayout_email.setError(getString(R.string.empty_editText));
        } else if (!textInputEditText_email.getText().toString().contains("@")) {
            textInputLayout_email.setError(getString(R.string.bad_email));
            } else {
            textInputLayout_email.setError(null);
        }

        if (textInputEditText_password.getText().toString().equals(""))
        {
            empty_editText = true;
            textInputLayout_password.setError(getString(R.string.empty_editText));
        } else {
            textInputLayout_password.setError(null);
        }
        return empty_editText;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_authorization:
                if (!empty_editText_check()) {
                    showProgress();
                    loginViewModel.loginUser(textInputEditText_email.getText().toString(), textInputEditText_password.getText().toString());
                }
                break;
            case R.id.button_registration:
                RegistrationActivity.startInNewTask(this);
                break;
            case R.id.button_password_recovery:
                    // TODO: password_recovery
                break;
        }
    }


    void initialLiveData()
    {
        loginViewModel.loginError().observe(this, loginError -> {

            hideProgress();

            if (loginError.getClass() == FirebaseAuthInvalidUserException.class)
                 textInputLayout_email .setError(getString(R.string.bad_email3));
            if (loginError.getClass() == FirebaseAuthInvalidCredentialsException.class)
                textInputLayout_password .setError(getString(R.string.bad_password3));
            if (loginError.getClass() == FirebaseNetworkException.class)
                showNetworkFailedDialog();

            Log.d("LOGGG", loginError.getClass().toString());
        });

        loginViewModel.loginConfirmed().observe(this, isConfirmed -> {
            hideProgress();
            if (isConfirmed) {
                Log.d("LOGGG", "ОТКРЫВАЕМ ОКОШКО ВЫБОРА ИМЕНИ И КАРТИНКИ, ЕСЛИ УЖЕ ВЫБРАНО - ОТКРЫВАЕМ ЧАТИК");
                AvatarActivity.startInNewTask(this);
            } else {
                new MaterialAlertDialogBuilder(this)
                        .setTitle(getString(R.string.email_confirm_dialog_title))
                        .setMessage(getString(R.string.email_confirm_dialog_message))
                        .setPositiveButton("OK", null)
                        .setNeutralButton("ОТПРАВИТЬ ЕЩЕ РАЗ", (dialog, which) -> loginViewModel.emailVerified())
                        .show();
            }
        });
    }

    void showProgress()
    {
        progressDialog.show();
    }

    void hideProgress()
    {
        progressDialog.dismiss();
    }

    void showNetworkFailedDialog()
    {
        new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.network_failed_dialog_title))
                .setMessage(getString(R.string.network_failed_dialog_message))
                .setPositiveButton("OK :(", null)
                .show();
    }
}

