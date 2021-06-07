package com.master.mipasapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.master.mipasapp.R;
import com.master.mipasapp.model.User;

public class RegisterActivity extends AppCompatActivity {
    EditText etName, etEmail, etPass;
    Button btnRegister;
    ProgressBar pbRegister;
    ScrollView formRegister;
    //inicializamos nuestro servicio de firebaseAuth
    FirebaseAuth firebaseAuth;
    //inicializamos nuestro servico la base de datos más reciente de Firebase
    FirebaseFirestore dbfirebaseFirestore;

    //deajr registro del usuario en el servicio de firestorm: tener una lista con los usuarios y sus puntuaciones

    String name, email, password;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.editTextName);
        etEmail = findViewById(R.id.editTextEmail);
        etPass = findViewById(R.id.editTextPassword);
        btnRegister = findViewById(R.id.buttonRegister);
        pbRegister = findViewById(R.id.progressBarRegister);
        formRegister = findViewById(R.id.formRegister);
        firebaseAuth = FirebaseAuth.getInstance();
        dbfirebaseFirestore = FirebaseFirestore.getInstance();

        changeVisibilityRegisterActivity(true); //mostramos el formulario de registro nada mas empezar esta actividad
        signals();
    }

    private void signals() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString();
                email = etEmail.getText().toString();
                password = etPass.getText().toString();

                if(name.isEmpty()){
                    etName.setError("Se debe introducir un Nombre");
                }else if(email.isEmpty()){
                    etEmail.setError("Se debe introducir un Email");
                }else if(password.isEmpty()){
                    etPass.setError("Se debe introducir una Contraseña");
                }else{
                    //se han introducido unos datos, procedemos con el registro en Firebase
                    createUser();
                }

            }
        });
    }

    private void createUser() {
        changeVisibilityRegisterActivity(false);//oculatmos el formulario
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser(); //obtenemos los datos del usuario registrado
                    updateUI(user);//actualizamos la interfaz de usuario
                }else {
                    Toast.makeText(RegisterActivity.this,"Error en el registro",Toast.LENGTH_SHORT).show();
                    updateUI(null);

                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            //alamacenar la inforamcion del usuario en firebase-firestorm o base da tos de tiempo real
            User nuevoUsuario = new User(name,email,password, 0,0);
            dbfirebaseFirestore.collection("users")
                    .document(user.getUid())
                    .set(nuevoUsuario)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            //despues de haberse registrado el usuario y de haberse alamacenado en la base de datos de firebase, firestorm, el usuario, procedemos...
                            /** Como se contempla almacenar los datos de la App (Nivel Organizacion):
                             * Users --> FireStorm Database
                             * DataSensors --> Firebase RealTime DataBase
                             * DataApiWebservice --> sqlite (Local Databse)
                             * */
                            //terminamos la actual Activity y cambiamos a la siguiente Activity,

                            finish();
                            Intent i = new Intent(RegisterActivity.this, MenuActivity.class);
                            i.putExtra("CurrentFireBaseUser", user);
                            startActivity(i);

                        }
                    });

        }else {
            changeVisibilityRegisterActivity(true);//volvemso a mostrar el formulario
            etPass.setError("Nombre, Email y/o contraseña incorrectos");
            etPass.requestFocus();
        }

    }

    private void changeVisibilityRegisterActivity(boolean showForm) {
        pbRegister.setVisibility(showForm ? View.GONE : View.VISIBLE);
        formRegister.setVisibility(showForm ? View.VISIBLE : View.GONE);
    }


}