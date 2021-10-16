package com.example.wildlife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Register_User extends AppCompatActivity implements View.OnClickListener{

    private TextView  register;
    private EditText editTextName, editTextEmail, editTextPassword, editTextCode, editTextConfPw;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        register = (Button) findViewById(R.id.Register);
        register.setOnClickListener(this);

        editTextName = (EditText) findViewById(R.id.Name);
        editTextEmail = (EditText) findViewById(R.id.Email);
        editTextPassword = (EditText) findViewById(R.id.Password);
        editTextConfPw = (EditText) findViewById(R.id.confPassword);

        editTextCode = (EditText) findViewById(R.id.Code);
        progressBar = (ProgressBar) findViewById(R.id.Progressbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Register:
                register();
                break;
        }

    }

    private void register() {
        String Name = editTextName.getText().toString().trim();
        String Email = editTextEmail.getText().toString().trim();
        String Password = editTextPassword.getText().toString().trim();
        String ConfPassword = editTextConfPw.getText().toString().trim();
        String Code = editTextCode.getText().toString().trim();



        if(Name.isEmpty()){
            editTextName.setError("Name is Required!");
            editTextName.requestFocus();
            return;
        }

        if(Email.isEmpty()){
            editTextEmail.setError("Email is Required!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            editTextEmail.setError("Please Provide Valid Email");
            editTextEmail.requestFocus();
            return;
        }

        if(Password.isEmpty()){
            editTextPassword.setError("Password is Required!");
            editTextPassword.requestFocus();
            return;
        }

        if(ConfPassword.isEmpty()){
            editTextPassword.setError("Password Confirmation is Required!");
            editTextPassword.requestFocus();
            return;
        }

        if(Password.length()<6){
            editTextPassword.setError("Minimum Password Length Should be 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        if(Code.isEmpty()){
            editTextCode.setError("Code is Required!");
            editTextCode.requestFocus();
            return;
        }

        if(!ConfPassword.equals(Password))
        {
            editTextPassword.setError("Password Didn't Match!");
            editTextPassword.requestFocus();
            return;
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Employees");
        Query checkUser = reference.orderByChild("EmpId").equalTo(Code);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(Email,Password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        User user = new User(Name, Email, Code);
                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(Register_User.this, "User has been Registered Successfully", Toast.LENGTH_LONG).show();
                                                    progressBar.setVisibility(View.GONE);
                                                    startActivity(new Intent(Register_User.this,MainActivity.class));
                                                }
                                                else{
                                                    Toast.makeText(Register_User.this, "Registration Failed -01", Toast.LENGTH_LONG).show();
                                                    progressBar.setVisibility(View.GONE);
                                                    editTextName.setText(""); editTextEmail.setText(""); editTextPassword.setText(""); editTextCode.setText(""); editTextConfPw.setText("");
                                                }

                                            }

                                        });
                                    }
                                    else{
                                        Toast.makeText(Register_User.this, "Registration Failed -02", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        editTextName.setText(""); editTextEmail.setText(""); editTextPassword.setText(""); editTextCode.setText(""); editTextConfPw.setText("");
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(Register_User.this, "Not a valid Employee Id", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    editTextName.setText(""); editTextEmail.setText(""); editTextPassword.setText(""); editTextCode.setText(""); editTextConfPw.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








    }
}