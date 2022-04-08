package com.example.simplebackgroundtask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetail extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private User user;
    private TextView edtNameUser;
    private TextView edtEmail;
    private TextView edtGender;
    private TextView edtStatus;
    private Integer idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        user = (User) bundle.get("object_user");
        idUser = user.id;
        edtNameUser = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtGender = findViewById(R.id.edt_gender);
        edtStatus = findViewById(R.id.edt_status);

        //set
        edtNameUser.setText(user.name);
        edtEmail.setText(user.email);
        edtGender.setText(user.gender);
        edtStatus.setText(user.status);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);

    }

    public void onClick(View view) {
        User updateUser = new User();
        updateUser.email=edtEmail.getText().toString();
        updateUser.name=edtNameUser.getText().toString();
        updateUser.status=edtStatus.getText().toString();
        updateUser.gender=edtGender.getText().toString();

        ApiClient.getAPI().updateUser(idUser,updateUser).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code()==200) {
                    Toast.makeText(UserDetail.this,"Update success",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UserDetail.this,response.message(),Toast.LENGTH_SHORT).show();
                }
                Log.d("respon user",response.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }
}