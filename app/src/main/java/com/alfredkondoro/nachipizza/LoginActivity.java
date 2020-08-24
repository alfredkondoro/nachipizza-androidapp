package com.alfredkondoro.nachipizza;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText txtUsername, txtPassword;
    private ProgressDialog progressDialog;
    private static final String URL_LOGIN = "http://10.0.2.2/nachipizza/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById (R.id.btnlogin);

        txtUsername = findViewById (R.id.loginUsername);
        txtPassword = findViewById (R.id.loginPassword);

        progressDialog = new ProgressDialog (this);
        progressDialog.setMessage ("Please wait...");

        loginButton.setOnClickListener (this);
    }

        @Override
    public void onClick(View view) {
            if(view.getId() == R.id.btnlogin){
                String username = txtUsername.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                if (!(username.isEmpty() || password.isEmpty())){
                    login(username,password);
                }
                else {
                    if(username.isEmpty() && !password.isEmpty()){
                        Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
                    }
                    else if(password.isEmpty() && !username.isEmpty()){
                        Toast.makeText(this, "Please enter your  password", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Please enter your username and password", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        }
    private void login(final String username, final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Login Successful")){
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    Intent HomeIntent = new Intent(LoginActivity.this,MenuActivity.class);
                    startActivity(HomeIntent);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",username);
                params.put("password",password);
                return  params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }
}