package my.edu.utem.ftmk.bitp3453.achifapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button login;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                if (currentUser!=null) {
                    System.out.println("LoginActivity " + currentUser);
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);

                    SharedPreferences sharedPreferences = getSharedPreferences("my.edu.utem.ftmk.bitp3453.achifapp", MODE_PRIVATE);

                    sharedPreferences.edit()
                            .putString("user", currentUser.getUid())
                            .putInt("loggedIn", 1).apply();

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    finish();
                    startActivity(intent);
                } else {
                    setContentView(R.layout.activity_login_options_page);
                }
            }
        };
    }

    private void performLogin() {
        String inputemail = email.getText().toString();
        String inputpassword = password.getText().toString();

        if (!inputemail.matches(emailPattern))
        {
            email.setError("Enter Correct Email");
        }else if(inputpassword.isEmpty() || inputpassword.length()<6)
        {
            password.setError("Enter Correct Password");
        }else
        {
            progressDialog.setMessage("Please Wait While Login...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

           mAuth.signInWithEmailAndPassword(inputemail, inputpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();

                        SharedPreferences sharedPreferences = getSharedPreferences("my.edu.utem.ftmk.bitp3453.achifapp", MODE_PRIVATE);

                        sendUserToNextActivity();
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
    }
}

    private void sendUserToNextActivity()
    {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    }

