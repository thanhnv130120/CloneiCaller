package com.example.cloneicaller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloneicaller.Models.Members;
import com.example.cloneicaller.auth.RetrofitClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements Callback<Members> {
    Button btn_login;
    LinearLayout layout_otp;
    private EditText edt1, edt2, edt3, edt4, edt5, edt6, edtphoneNumber;
    String verifyCodeBySystem;
    String codeOtp;
    String phoneNum = "+84965999999";

    private String g_token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjZjZmMyMzViZDYxMGZhY2FlYzVlYjBhZGU5NTg5ZGE5NTI4MmRlY2QiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vaWNhbGxlci04YzM3MiIsImF1ZCI6ImljYWxsZXItOGMzNzIiLCJhdXRoX3RpbWUiOjE1OTU4MTUzMDAsInVzZXJfaWQiOiIxclc2ZjBuZ2RSWk5nd3dGRG1ONWNvaVdSSTkzIiwic3ViIjoiMXJXNmYwbmdkUlpOZ3d3RkRtTjVjb2lXUkk5MyIsImlhdCI6MTU5NTgxNTMwMiwiZXhwIjoxNTk1ODE4OTAyLCJwaG9uZV9udW1iZXIiOiIrODQ5NjU5OTk5OTkiLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7InBob25lIjpbIis4NDk2NTk5OTk5OSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBob25lIn19.eVn386uJEZ6skWDRKqwxk-6su46IchXF24Vk0gQ5V5unjtD_0nDtMmG5K7hwv824E5VuCqoq2ZByyRsugQB5D9BkoEKSH-OlmuAt2YY8yAIFuv5lMslT9o4FX6tJxgsqwBBNB637iVK5iRpQThMyg6Omd8Kdh0EALS4mvBJUugoPaC5T4d006QpwAblAO4csrK7dtnoXSE3nfbqXT1huIrhVnyGMCXghFs1lOebPmR2hNMfBfoXo0uM52ET7NvM6h4fMCTlECzjGQPEh0dzFdbcVhUTBzb9OYaJOTdwGZP2sSX5PedBKlMNHmSiYUezzP4FKih-AFnZAWrzDXrenkA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login);
        layout_otp = findViewById(R.id.layout_otp);

        edt1 = (EditText) findViewById(R.id.edt1);
        edt2 = (EditText) findViewById(R.id.edt2);
        edt3 = (EditText) findViewById(R.id.edt3);
        edt4 = (EditText) findViewById(R.id.edt4);
        edt5 = (EditText) findViewById(R.id.edt5);
        edt6 = (EditText) findViewById(R.id.edt6);
        edtphoneNumber = findViewById(R.id.edtphoneNumber);

        final String phoneNumber = edtphoneNumber.getText().toString();


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_otp.setVisibility(view.getVisibility());
                sendOTPToUser(phoneNumber);
            }
        });

        edt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 1) {
                    edt2.requestFocus();
                }
            }
        });

        edt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 1) {
                    edt3.requestFocus();
                } else if (editable.toString().length() == 0) {
                    edt1.requestFocus();
                }
            }
        });

        edt3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 1) {
                    edt4.requestFocus();
                } else if (editable.toString().length() == 0) {
                    edt2.requestFocus();
                }
            }
        });

        edt4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 1) {
                    edt5.requestFocus();
                } else if (editable.toString().length() == 0) {
                    edt3.requestFocus();
                }
            }
        });

        edt5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 1) {
                    edt6.requestFocus();
                } else if (editable.toString().length() == 0) {
                    edt4.requestFocus();
                }
            }
        });

        edt6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 1) {
                    try {
                        codeOtp = edt1.getText().toString() + edt2.getText().toString() + edt3.getText().toString() + edt4.getText().toString() + edt5.getText().toString() + edt6.getText().toString();
                        verifyCode(codeOtp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (editable.toString().length() == 0) {
                    edt5.requestFocus();
                }
            }
        });


    }

    private void sendOTPToUser(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+84382480082",        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Log.e("Success", "Thành công");
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.e("Failed", e.getMessage());
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verifyCodeBySystem = s;
        }
    };

    private void verifyCode(String codeByUser) {
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verifyCodeBySystem, codeByUser);
        signInUserByCredential(phoneAuthCredential);
    }

    private void signInUserByCredential(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try {
                    if (task.isSuccessful()) {
                        Log.e("checkOTP", "true OTP");
//                       task.getResult().getUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
//                           @Override
//                           public void onComplete(@NonNull Task<GetTokenResult> task) {
//                               Log.e("AAAA", task.getResult().getToken());
//                               int a  =1;
//
//                           }
//                       });

                        RetrofitClient.getInstance().getMember(phoneNum, g_token).enqueue(LoginActivity.this);

                    } else {
                        Log.e("checkOTP", "false OTP");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResponse(Call<Members> call, Response<Members> response) {
        if (response.isSuccessful()) {
            Log.e("Auth", "Valid");
            SharedPreferences sharedPreferences = getSharedPreferences("token", Context.MODE_PRIVATE);
            SharedPreferences.Editor spE = sharedPreferences.edit();
            spE.putString("g_token",g_token);
            spE.commit();

            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        } else {
            Log.e("Auth", "Invalid");
        }
    }

    @Override
    public void onFailure(Call<Members> call, Throwable t) {
        Log.e("Auth", t.getMessage());
    }
}