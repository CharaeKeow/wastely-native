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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity
{
    EditText name, email, phone, password;
    Button register;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAuth();
            }
        });
    }

    private void PerformAuth() {
        String inputemail = email.getText().toString();
        String inputpassword = password.getText().toString();

        if (!inputemail.matches(emailPattern))
        {
            email.setError("Enter Correct Email");
        }else if(inputpassword.isEmpty() || inputpassword.length()<6)
        {
            password.setError("Enter Proper Password");
        }else
        {
            progressDialog.setMessage("Please Wait While Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(inputemail, inputpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        String userName = name.getText().toString();
                        String userEmail = email.getText().toString();
                        String userPhoneNo = password.getText().toString();

                        //get current user
                        FirebaseUser currentUser = mAuth.getCurrentUser();

                        db = FirebaseFirestore.getInstance();

                        //create reference to Firestore collection
                        CollectionReference dbUser = db.collection("user");

                        User user = new User(currentUser.getUid(), userName, userEmail, userPhoneNo);

                        dbUser.add(user);

                        SharedPreferences sharedPreferences = getSharedPreferences("my.edu.utem.ftmk.bitp3453.achifapp", MODE_PRIVATE);

                        sharedPreferences.edit()
                                .putString("userID", currentUser.getUid())
                                .putString("userName", userName)
                                .putString("phoneNo", userPhoneNo)
                                .putString("email", userEmail)
                                .apply();

                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }

    private void sendUserToNextActivity()
    {
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }
}

