package io.devsummit.devsummit.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.devsummit.devsummit.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    private void startFormRegisterActivity(String registerWith) {
        Intent intent = new Intent(this, FormRegisterActivity.class);
        intent.putExtra("registerWith", registerWith);
        startActivity(intent);
    }

    public void clickRegisterWithEmail(View view) {
        String registerWith = "email";
        startFormRegisterActivity(registerWith);
    }

    public void clickRegisterWithPhone(View view) {
        String registerWith = "phone";
        startFormRegisterActivity(registerWith);
    }

    public void onLoginButtonClicked(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
