package com.master.mipasapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.master.mipasapp.R;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnLogin, bt2Register;
    private ScrollView formLogin;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private String email;
    private String password;
    boolean tryLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        bt2Register = findViewById(R.id.buttonToRegister);
        formLogin = findViewById(R.id.formLogin);
        progressBar = findViewById(R.id.progressBarLogin);
        firebaseAuth = FirebaseAuth.getInstance();
        
        changeVisibilityLoginActivity(true);
        signals();
        
    }

    private void signals() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                if (email.isEmpty()){
                    etEmail.setError("Se debe introducir un email");
                }else if (password.isEmpty()){
                    etPassword.setError("Se debe introducir una contraseña");
                }else {
                    //se ha introducido un email y una contraseña ... procedemos a hacer el login
                    changeVisibilityLoginActivity(false);
                    loginUser();
                }

            }
        });
        bt2Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private void loginUser() {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                tryLogin = true; //se ha intentado hacer un login
                if (task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    updateUI(user);
                }else {
                    Log.w("TAG", "SignInError: ", task.getException());
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            //alamacenar la inforamcion del usuario en firebase-firestorm o base datos de tiempo real o base de datos local como con sqlite



            //navegar hacia la sigueinte pantall de la aplicacion
            Intent i = new Intent(LoginActivity.this, MenuActivity.class);
            startActivity(i);
        }else {
            changeVisibilityLoginActivity(true);//volvemso a mostrar el formulario
            if(tryLogin){
                etPassword.setError("Email y/o contraseña incorrectos");
                etPassword.requestFocus();
            }
        }
    }

    private void changeVisibilityLoginActivity(boolean showForm) {
        progressBar.setVisibility(showForm ? View.GONE : View.VISIBLE);
        formLogin.setVisibility(showForm ? View.VISIBLE : View.GONE);
    }
//este metodo se ejecuta a lapar que el metodo onCreate
    @Override
    protected void onStart() {
        super.onStart();
        //comporbamos si preciamente el usuario ya ha iniciado sesion en el dispositov para mandarlo directamente al sigueinte activity si es afiramtivo
        // y asi no tener que logearse de nuevo [puede parecer enseguro, Repasar]
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }
}