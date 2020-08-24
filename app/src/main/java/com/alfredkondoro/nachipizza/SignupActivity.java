package com.alfredkondoro.nachipizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity{

    private EditText txtUsername, txtPassword, txtEmail, txtCPassword;
    private Button buttonSignup;
    private static final String URL_CREATE_ACCOUNT = "http://10.0.2.2/nachipizza/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_signup);

        txtEmail = (EditText) findViewById (R.id.email);
        txtUsername = (EditText) findViewById (R.id.SignupUsername);
        txtPassword = (EditText) findViewById (R.id.password);
        txtCPassword = (EditText) findViewById (R.id.password);
        buttonSignup = (Button) findViewById (R.id.btnSignup);

        buttonSignup.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
            if (view.getId () == R.id.btnSignup) {

                String username = txtUsername.getText ().toString ().trim ();
                String email = txtEmail.getText ().toString ().trim ();
                String password = txtPassword.getText ().toString ().trim ();
                String confirmpassword = txtCPassword.getText ().toString ().trim ();

                if (!(username.isEmpty () || email.isEmpty () || password.isEmpty () || confirmpassword.isEmpty ())) {
                    if (password.equals (confirmpassword)) {
                        try {
                            createAccount (username, email, password);
                        } catch (JSONException e) {
                            e.printStackTrace ();
                        }
                        Intent MainActivityIntent = new Intent (SignupActivity.this, MainActivity.class);
                        startActivity (MainActivityIntent);
                    } else {
                        Toast.makeText (SignupActivity.this, "Password does not match", Toast.LENGTH_SHORT).show ();
                    }
                } else {
                    if ((username.isEmpty () || email.isEmpty () || password.isEmpty () || confirmpassword.isEmpty ())) {
                        Toast.makeText (SignupActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show ();

                    }
                }
            }
        }

    });

    }

    public void createAccount(final String username, final String email, final String password) throws JSONException {

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CREATE_ACCOUNT, new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            Toast.makeText(SignupActivity.this, "Success"+response, Toast.LENGTH_SHORT).show();
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(SignupActivity.this, "Fail"+error, Toast.LENGTH_SHORT).show();
            System.out.println("ERROR: "+error);
        }
    }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> params = new HashMap<> ();
            params.put("email",email);
            params.put("username",username);
            params.put("password",password);
            return params;
        }
    };
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
