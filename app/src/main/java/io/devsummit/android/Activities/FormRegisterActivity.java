package io.devsummit.android.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import java.util.HashMap;

import io.devsummit.android.R;
import io.devsummit.android.Models.RegisterModel;
import io.devsummit.android.Models.register.RegisterCredentials;
import io.devsummit.android.Models.register.RegisterMobileCredentials;
import io.devsummit.android.Remote.APIService;
import io.devsummit.android.Remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.devsummit.android.R.id.firstName;
import static io.devsummit.android.R.id.lastName;
import static io.devsummit.android.R.id.verifyPassword;

public class FormRegisterActivity extends AppCompatActivity {
    Thread thread = new Thread() {
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
    private EditText refererInput;
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText verifyPasswordInput;
    private APIService mAPIService;
    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private EditText emailEdit;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private EditText verifyPasswordEdit;
    private EditText refererEdit;
    private Button registerButton;
    private String phoneNumberString;
    private RegisterMobileCredentials credentials;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAPIService = ApiUtils.getAPIService();
        AccountKit.initialize(getApplicationContext());

        Intent intent = getIntent();
        String registerWith = intent.getStringExtra("registerWith");

        setContentView(R.layout.activity_form_register);

        //set listeners
        firstNameEdit = (EditText) findViewById(R.id.firstName);
        firstNameEdit.addTextChangedListener(textWatcher);

        lastNameEdit = (EditText) findViewById(R.id.lastName);
        lastNameEdit.addTextChangedListener(textWatcher);

        emailEdit = (EditText) findViewById(R.id.email);
        emailEdit.addTextChangedListener(textWatcher);

        if (registerWith.equals("email")) {
            usernameEdit = (EditText) findViewById(R.id.username);
            usernameEdit.addTextChangedListener(textWatcher);

            passwordInput = (EditText) findViewById(R.id.password);
            passwordInput.addTextChangedListener(textWatcher);

            verifyPasswordInput = (EditText) findViewById(R.id.verifyPassword);
            verifyPasswordInput.addTextChangedListener(textWatcher);
        }

        if (registerWith.equals("phone")) {
            // Hide username
            usernameInput = (EditText) findViewById(R.id.username);
            usernameInput.setVisibility(View.GONE);

            // Hide password
            passwordInput = (EditText) findViewById(R.id.password);
            passwordInput.setVisibility(View.GONE);

            // Hide verify password
            verifyPasswordInput = (EditText) findViewById(verifyPassword);
            verifyPasswordInput.setVisibility(View.GONE);
        }

        refererInput = (EditText) findViewById(R.id.refererName);
        refererInput.setVisibility(View.GONE);
        CheckBox refererCheckBox = (CheckBox) findViewById(R.id.use_referer_checkbox);

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setEnabled(false);

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

    private void checkFieldsForEmptyValues() {
        HashMap<String, String> credentials = getCredentials();
        registerButton = (Button) findViewById(R.id.registerButton);

        if (passwordEdit.isShown()) {
            if (credentials.get("firstname").isEmpty() || credentials.get("lastname").isEmpty() || credentials.get("email").isEmpty() || credentials.get("username").isEmpty() || credentials.get("password").isEmpty() || credentials.get("verify_password").isEmpty()) {
                registerButton.setText(getString(R.string.fields_not_complete));
                registerButton.setBackgroundColor(Color.BLACK);
                registerButton.setAlpha(0.45f);
                registerButton.setEnabled(false);
            } else {
                registerButton.setText(getString(R.string.register));
                registerButton.setAlpha(1);
                registerButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryButtonOrange));
                registerButton.setEnabled(true);
            }
        } else {
            if (credentials.get("firstname").isEmpty() || credentials.get("lastname").isEmpty() || credentials.get("email").isEmpty()) {
                registerButton.setText(getString(R.string.fields_not_complete));
                registerButton.setBackgroundColor(Color.BLACK);
                registerButton.setAlpha(0.45f);
                registerButton.setEnabled(false);
            } else {
                registerButton.setText(getString(R.string.register));
                registerButton.setAlpha(1);
                registerButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryButtonOrange));
                registerButton.setEnabled(true);
            }
        }
    }

    public void onLoginClicked(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void onRegisterClicked(View view) {
        // Reset errors.
        emailEdit.setError(null);
        passwordEdit.setError(null);
        verifyPasswordEdit.setError(null);

        boolean cancel = false;
        View focusView = null;
        HashMap<String, String> credentials = getCredentials();

        String email = credentials.get("email");
        String password = credentials.get("password");
        String verifyPassword = credentials.get("verify_password");

        if (email != null && !email.isEmpty() && !isValidEmail(email)) {
            emailEdit.setError(getString(R.string.invalid_email));
            focusView = emailEdit;
            cancel = true;
        }

        if (password != null && !password.isEmpty() && verifyPassword != null && !verifyPassword.isEmpty()) {
            if (password.length() < 6) {
                passwordEdit.setError(getString(R.string.error_invalid_password));
                focusView = passwordEdit;
                cancel = true;
            } else if (!password.equals(verifyPassword)) {
                verifyPasswordEdit.setError(getString(R.string.error_password_not_match));
                focusView = verifyPasswordEdit;
                cancel = true;
            }
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            attemptRegister(credentials);
        }
    }

    // ACCOUNT KIT
    public void onSMSLoginFlow() {
        final Intent intent = new Intent(this, AccountKitActivity.class);

        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN)
                        .setDefaultCountryCode("ID");

        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());

        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            final String[] toastMessage = new String[1];

            if (loginResult.getAccessToken() != null) {
                credentials = new RegisterMobileCredentials();

                credentials.provider = "mobile";
                credentials.token = loginResult.getAccessToken().getToken();
                credentials.social_id = loginResult.getAccessToken().getAccountId();

                HashMap<String, String> baseCredentials = getCredentials();

                credentials.first_name = (String) baseCredentials.get("firstname");
                credentials.last_name = (String) baseCredentials.get("lastname");
                credentials.email = (String) baseCredentials.get("email");
                credentials.referer = (String) baseCredentials.get("referer_name");

                AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                    @Override
                    public void onSuccess(final Account account) {
                        // Get phone number
                        PhoneNumber phoneNumber = account.getPhoneNumber();
                        phoneNumberString = phoneNumber.toString();

                        if (account.getPhoneNumber() != null) {
                            credentials.username = phoneNumberString;

                            mAPIService.registerMobile(credentials).enqueue(new Callback<RegisterModel>() {
                                @Override
                                public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                                    if (response.body().getMeta().getSuccess().booleanValue() || !response.body().getMeta().getSuccess().booleanValue()) {
                                        toastMessage[0] = capitalizeFirstSentenceLetter(response.body().getMeta().getMessage());
                                    } else {
                                        toastMessage[0] = "Something went wrong";
                                    }

                                    Context context = getApplicationContext();
                                    CharSequence text = toastMessage[0];
                                    int duration = Toast.LENGTH_LONG;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    thread.start();
                                }

                                @Override
                                public void onFailure(Call<RegisterModel> call, Throwable t) {
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(final AccountKitError error) {
                        // Handle Error
                    }
                });
            } else {
                toastMessage[0] = "Fail to get token";

                Context context = getApplicationContext();
                CharSequence text = toastMessage[0];
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                thread.start();
            }
        }
    }

    private void attemptRegister(HashMap<String, String> credentials) {
        RegisterCredentials registerCredentials = new RegisterCredentials();

        registerCredentials.first_name = (String) credentials.get("firstname");
        registerCredentials.last_name = (String) credentials.get("lastname");
        registerCredentials.email = (String) credentials.get("email");
        registerCredentials.username = (String) credentials.get("username");
        registerCredentials.password = (String) credentials.get("password");
        registerCredentials.referer = (String) credentials.get("referer_name");

        if (registerCredentials.username.equals("")) {
            onSMSLoginFlow();
        } else {
            mAPIService.register(registerCredentials).enqueue(new Callback<RegisterModel>() {
                @Override
                public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                    if (response.body().getMeta().getSuccess().booleanValue()) {
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
        firstNameEdit = (EditText) findViewById(firstName);
        String firstName = firstNameEdit.getText().toString();
        credentials.put("firstname", firstName);

        // Get last name
        lastNameEdit = (EditText) findViewById(lastName);
        String lastName = lastNameEdit.getText().toString();
        credentials.put("lastname", lastName);

        // Get email
        emailEdit = (EditText) findViewById(R.id.email);
        String email = emailEdit.getText().toString();
        credentials.put("email", email);

        // Get username
        usernameEdit = (EditText) findViewById(R.id.username);
        String username = usernameEdit.getText().toString();
        credentials.put("username", username);

        // Get password
        passwordEdit = (EditText) findViewById(R.id.password);
        String password = passwordEdit.getText().toString();
        credentials.put("password", password);

        // Get password
        verifyPasswordEdit = (EditText) findViewById(verifyPassword);
        String verifyPassword = verifyPasswordEdit.getText().toString();
        credentials.put("verify_password", verifyPassword);

        // Get referer name
        refererEdit = (EditText) findViewById(R.id.refererName);
        String refererName = refererEdit.getText().toString();
        credentials.put("referer_name", refererName);

        return credentials;
    }
}
