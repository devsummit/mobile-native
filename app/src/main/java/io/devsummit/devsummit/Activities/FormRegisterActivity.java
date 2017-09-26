package io.devsummit.devsummit.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import io.devsummit.devsummit.Models.RegisterModel;
import io.devsummit.devsummit.Models.register.RegisterCredentials;
import io.devsummit.devsummit.R;
import io.devsummit.devsummit.Remote.APIService;
import io.devsummit.devsummit.Remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormRegisterActivity extends AppCompatActivity {
    private EditText refererInput;
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText verifyPasswordInput;
    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAPIService = ApiUtils.getAPIService();

        Intent intent = getIntent();
        String registerWith = intent.getStringExtra("registerWith");

        setContentView(R.layout.activity_form_register);

        if (registerWith.equals("phone")) {
            // Hide username
            usernameInput = (EditText) findViewById(R.id.username);
            usernameInput.setVisibility(View.GONE);

            // Hide password
            passwordInput = (EditText) findViewById(R.id.password);
            passwordInput.setVisibility(View.GONE);

            // Hide verify password
            verifyPasswordInput = (EditText) findViewById(R.id.verifyPassword);
            verifyPasswordInput.setVisibility(View.GONE);
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

    public void onRegisterWithEmailClicked(View view) {
        HashMap<String, String> credentials = getCredentials();

        attemptRegister(credentials);
    }

    Thread thread = new Thread(){
        @Override
        public void run() {
            try {
                Thread.sleep(3500); // As I am using LENGTH_LONG in Toast
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void attemptRegister(HashMap<String, String> credentials) {
        RegisterCredentials registerCredentials = new RegisterCredentials();

        registerCredentials.first_name = (String) credentials.get("firstname");
        registerCredentials.last_name = (String) credentials.get("lastname");
        registerCredentials.email = (String) credentials.get("email");
        registerCredentials.username = (String) credentials.get("username");
        registerCredentials.password = (String) credentials.get("password");
        registerCredentials.referer = (String) credentials.get("referer_name");

        mAPIService.register(registerCredentials).enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                if(response.body().getMeta().getSuccess().booleanValue()) {
                    Context context = getApplicationContext();
                    CharSequence text = capitalizeFirstSentenceLetter(response.body().getMeta().getMessage());
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    thread.start();
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = capitalizeFirstSentenceLetter(response.body().getMeta().getMessage());
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
            }
        });
    }

    private String capitalizeFirstSentenceLetter(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    private boolean editTextIsEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private HashMap<String, String> getCredentials() {
        HashMap<String, String> credentials = new HashMap<String, String>();

        // Get first name
        EditText firstNameEdit = (EditText) findViewById(R.id.firstName);
        String firstName = firstNameEdit.getText().toString();
        credentials.put("firstname", firstName);

        // Get last name
        EditText lastNameEdit = (EditText) findViewById(R.id.lastName);
        String lastName = lastNameEdit.getText().toString();
        credentials.put("lastname", lastName);

        // Get email
        EditText emailEdit = (EditText) findViewById(R.id.email);
        String email = emailEdit.getText().toString();
        credentials.put("email", email);

        // Get username
        EditText usernameEdit = (EditText) findViewById(R.id.username);
        String username = usernameEdit.getText().toString();
        credentials.put("username", username);

        // Get password
        EditText passwordEdit = (EditText) findViewById(R.id.password);
        String password = passwordEdit.getText().toString();
        credentials.put("password", password);

        // Get referer name
        EditText refererEdit = (EditText) findViewById(R.id.refererName);

        if (!editTextIsEmpty(refererEdit)) {
            String refererName = refererEdit.getText().toString();
            credentials.put("referer_name", refererName);
        } else {
            credentials.put("referer_name", null);
        }

        return credentials;
    }
}
