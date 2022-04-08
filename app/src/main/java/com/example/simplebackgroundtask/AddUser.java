package com.example.simplebackgroundtask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUser extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private TextView edtNameUser;
    private TextView edtEmail;
    private TextView edtGender;
    private TextView edtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        edtNameUser = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtGender = findViewById(R.id.edt_gender);
        edtStatus = findViewById(R.id.edt_status);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button: {
                addUser();
                break;
            }
        }

    }

    private void addUser() {
        User user = new User();
        user.email = edtEmail.getText().toString();
        user.name = edtNameUser.getText().toString();
        user.status = edtStatus.getText().toString();
        user.gender = edtGender.getText().toString();
        if (TextUtils.isEmpty(user.name)) {
            edtNameUser.setError("Required");
            return;
        }
        if (TextUtils.isEmpty(user.email)) {
            edtEmail.setError("Required");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
            edtEmail.setError("Email is invalid");
            return;
        }
        if (TextUtils.isEmpty(user.gender)) {
            edtGender.setError("Required");
            return;
        }
        if (TextUtils.isEmpty(user.status)) {
            edtStatus.setError("Required");
            return;
        }
        ApiClient.getAPI().addUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 201) {
                    Toast.makeText(AddUser.this, "Created Success Your ID is: " + response.body().id, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddUser.this, response.message(), Toast.LENGTH_SHORT).show();
                }
                Log.d("respon user", response.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });


    }

    ;
}