package io.devsummit.devsummit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void clickRegisterWithEmail(View view) {
        Intent emailIntent = new Intent(this, FormRegisterActivity.class);
        emailIntent.putExtra("registerWith", "email");
        startActivity(emailIntent);
    }

    public void clickRegisterWithPhone(View view) {
        Intent phoneIntent = new Intent(this, FormRegisterActivity.class);
        phoneIntent.putExtra("registerWith", "phone");
        startActivity(phoneIntent);
    }

    public void clickSignIn(View view) {
    }
}
