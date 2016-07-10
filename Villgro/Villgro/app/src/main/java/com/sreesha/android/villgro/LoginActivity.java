package com.sreesha.android.villgro;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sreesha.android.villgro.Networking.APIUrls;
import com.sreesha.android.villgro.Networking.AsyncResult;
import com.sreesha.android.villgro.Networking.DownloadData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    public static final String USER_MENTOR_TYPE = "userMentor";
    public static final String USER_STAFF_TYPE = "userStaff";

    public static final String USER_LOGIN_EMAIL = "userLoginEmail";
    public static final String USER_LOGIN_NAME = "userLoginPassword";
    public static final String USER_TYPE_KEY = "userTypKey";
    public static final String USER_ID_KEY = "userIDKey";
    public static final String IS_LOGGED_IN = "isLoggedIN";

    CardView loginCard;
    CardView signUpCard;
    EditText loginEmailEditText;
    EditText loginPasswordEditText;

    EditText signUPEmailEditText;
    EditText signUpPasswordEditText;
    EditText signUpUNameEditText;
    RadioGroup signUpTypeRadioGroup;
    RadioButton signUpTypeStaffRadioButton;
    RadioButton signUpTypeMentorRadioButton;
    TextView mNotAMemberTextView;

    Button signInButton;
    Button signUpButton;

    String loginEmail = "";
    String loginPassword = "";

    String signUpEmail = "";
    String signUpUName = "";
    String signUpPassword = "";
    String signUpType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (!preferences.getBoolean(IS_LOGGED_IN, false)) {
            loginCard = (CardView) findViewById(R.id.loginCardView);
            signUpCard = (CardView) findViewById(R.id.signUpCard);
            signInButton = (Button) findViewById(R.id.signInButton);
            signUpButton = (Button) findViewById(R.id.signUpButton);

            loginEmailEditText = (EditText) findViewById(R.id.loginUserNameEditText);
            loginPasswordEditText = (EditText) findViewById(R.id.passwordEditText);

            signUPEmailEditText = (EditText) findViewById(R.id.signUpEmailEditText);
            signUpPasswordEditText = (EditText) findViewById(R.id.signUpPasswordEditText);
            signUpUNameEditText = (EditText) findViewById(R.id.sighUpNameEditText);
            signUpTypeRadioGroup = (RadioGroup) findViewById(R.id.signUpRadioGroup);
            signUpTypeStaffRadioButton = (RadioButton) findViewById(R.id.staffRadioButton);
            signUpTypeMentorRadioButton = (RadioButton) findViewById(R.id.mentorRadioButton);
            mNotAMemberTextView = (TextView) findViewById(R.id.notAMemberTextView);
            mNotAMemberTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ObjectAnimator mTranslateXAnim = ObjectAnimator.ofFloat(loginCard, "translationX", 0, -400);
                    ObjectAnimator mAlphaAnim = ObjectAnimator.ofFloat(loginCard, "alpha", 1, 0);

                    mTranslateXAnim.setInterpolator(new AccelerateInterpolator(2.1f));
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(mTranslateXAnim, mAlphaAnim);
                    set.setDuration(900);
                    set.start();
                }
            });
            signUpTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.staffRadioButton) {
                        preferences.edit().putString(USER_TYPE_KEY
                                , signUPEmailEditText.getText().toString())
                                .apply();
                    }
                }
            });
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DownloadData(getApplicationContext()
                            ,
                            APIUrls.getSignInURL(
                                    loginEmailEditText.getText().toString()
                                    , loginPasswordEditText.getText().toString()
                                    , USER_MENTOR_TYPE
                            ), null);
                    /*preferences.edit().putString(USER_LOGIN_NAME, loginEmailEditText.getText().toString())
                            .apply();
                    preferences.edit().putString(USER_LOGIN_EMAIL,
                            loginEmailEditText.getText().toString())
                            .apply();*/
                    startActivity(new Intent(LoginActivity.this, ModulesDashBoard.class));
                    finish();
                }
            });
            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DownloadData(getApplicationContext()
                            ,
                            APIUrls.getSignUpURL(
                                    signUpUNameEditText.getText().toString()
                                    , signUPEmailEditText.getText().toString()
                                    , signUpPasswordEditText.getText().toString()
                                    , USER_MENTOR_TYPE
                            ), new AsyncResult() {
                        @Override
                        public void onResultJSON(JSONObject object) throws JSONException {

                        }

                        @Override
                        public void onResultString(String stringObject, String errorString, String parseStatus) {

                        }

                        @Override
                        public void onResultQuizData(JSONArray object) {

                        }

                        @Override
                        public void onResultSignInData(JSONObject object) {

                        }

                        @Override
                        public void onResultSignUpData(JSONObject object) {
                            try {
                                SharedPreferences preferences
                                        = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                preferences.edit().putString(USER_TYPE_KEY
                                        , object.getJSONObject("message").getString("type")
                                ).apply();
                                preferences.edit().putString(USER_ID_KEY
                                        , object.getJSONObject("message").getString("_id")
                                ).apply();
                                preferences.edit().putString(USER_LOGIN_NAME, signUpUNameEditText.getText().toString())
                                        .apply();
                                preferences.edit().putString(USER_LOGIN_EMAIL, signUPEmailEditText.getText().toString())
                                        .apply();
                                preferences.edit().putBoolean(IS_LOGGED_IN, true)
                                        .apply();
                                Log.d("Login", object.toString());
                                startActivity(new Intent(LoginActivity.this, ModulesDashBoard.class));
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).execute("signup");

                }
            });
            playInitialLoginAnimation(loginCard, -200, 0);
        } else {
            startActivity(new Intent(LoginActivity.this, ModulesDashBoard.class));
            finish();
        }
    }

    void playInitialLoginAnimation(CardView view, float start, float end) {
        ObjectAnimator mTranslateXAnim = ObjectAnimator.ofFloat(view, "translationX", -200, 0);
        mTranslateXAnim.setInterpolator(new AccelerateInterpolator(2.1f));
        mTranslateXAnim.setDuration(900);
        mTranslateXAnim.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
