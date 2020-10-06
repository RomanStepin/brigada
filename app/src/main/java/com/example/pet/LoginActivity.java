package com.example.pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.textInputEditText_email)
    EditText textInputEditText_login;
    @BindView(R.id.textInputEditText_password)
    EditText textInputEditText_password;
    @BindView(R.id.button_authorization)
    Button button_authorization;
    @BindView(R.id.button_registration)
    Button button_registration;
    @BindView(R.id.button_password_recovery)
    Button button_password_recovery;

    @BindView(R.id.textInputLayout_email)
    TextInputLayout textInputLayout_login;
    @BindView(R.id.textInputLayout_password)
    TextInputLayout textInputLayout_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        button_registration.setOnClickListener(this);
        button_authorization.setOnClickListener(this);
        button_password_recovery.setOnClickListener(this);


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
        if (textInputEditText_login.getText().toString().equals(""))
        {
            empty_editText = true;
            textInputLayout_login.setError(getString(R.string.empty_editText));
        } else if (!textInputEditText_login.getText().toString().contains("@")) {
            textInputLayout_login.setError(getString(R.string.bad_email));
            } else {
            textInputLayout_login.setError(null);
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
                    // TODO: authorization
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
}

