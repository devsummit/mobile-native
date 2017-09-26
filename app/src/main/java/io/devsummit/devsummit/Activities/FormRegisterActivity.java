package io.devsummit.devsummit.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import io.devsummit.devsummit.R;

public class FormRegisterActivity extends AppCompatActivity {
    public EditText refererInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String registerWith = intent.getStringExtra("registerWith");

        if (registerWith.equals("email")) {
            setContentView(R.layout.activity_form_register_with_email);
        } else {
            setContentView(R.layout.activity_form_register_with_phone);
        }

        refererInput = (EditText) findViewById(R.id.refererName);
        refererInput.setVisibility(View.GONE);
        CheckBox refererCheckBox = (CheckBox) findViewById(R.id.use_referer_checkbox);

        refererCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    refererInput.setVisibility(View.VISIBLE);
                } else {
                    refererInput.setVisibility(View.GONE);
                }
            }
        });
    }

    protected void onRegisterWithEmailClicked() {
    }
}
