package com.example.proyectofinalgrupal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IniciarSesion extends AppCompatActivity {

    EditText etmail;
    EditText etpass;
    Button bregistrar ;
    Button blogin ;
    private String mail ;
    private String pass ;
    private FirebaseAuth mAuth;
    private DatabaseReference db;

    private String gRol ;
    private String gMail ;
    private String gPass;
    private String gName;
    private String checkRol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_sesion);

        mail= "";
        pass= "";
        gMail = "";
        gRol= "";
        gName = "";
        gPass = "";
        checkRol = "";



        db = FirebaseDatabase.getInstance().getReference();

        //Inicializo los campos editext y botones
        etmail = findViewById(R.id.CampoEmail);
        etpass = findViewById(R.id.CampoContraseña);
        bregistrar = findViewById(R.id.registrar);
        blogin = findViewById(R.id.iniciarSesion);
        //Inicializo mAuth
        mAuth = FirebaseAuth.getInstance();

        //Creo un onClick para el botón registrar que te lleva a la clase registro
        bregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail = etmail.getText().toString();
                pass = etpass.getText().toString();
                goMenu(mail);
            }

        });

        //Creo on Click para el botón iniciar sesión
        blogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                mail = etmail.getText().toString();
                pass = etpass.getText().toString();
                //En caso de que los campos NO ESTEN VACIOS te lleva al método logSesion
                if (!mail.isEmpty() && !pass.isEmpty()){
                    getUser(mail);
                    logSesion();
                }else{
                    //Si están vacíos te muestra este mensaje
                    Toast.makeText(IniciarSesion.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Método para iniciar sesión, primero mediante signInWithEmailAndPassword la base de datos introduce los datos para ver si son correctos.
    private void logSesion(){
        mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //En caso de que los datos sean correctos mediante el task.isSuccesful() te llevaría a la clase del menú principal.
                if(task.isSuccessful()){
                    Intent i = new Intent(IniciarSesion.this, MenuPrincipal.class);
                    i.putExtra("mail", gMail); //Te mete la variable del Mail para que en la otra clase la obtenga directamente
                    i.putExtra("rol", gRol);
                    i.putExtra("name", gName);
                    i.putExtra("pass", gPass);
                    startActivity(i);
                    finish();
                }else{
                    //Si no son correctos te muestra el siguiente mensaje.
                    Toast.makeText(IniciarSesion.this,"Datos erróneos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getUser(String pMail)
    { //Método para guardar los datos del usuario introducido (ROL y MAIL)
        db.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    for(DataSnapshot ds: snapshot.getChildren()){
                        String texto = ds.child("mail").getValue().toString();
                        if (texto.equals(pMail)){
                            gMail = ds.child("mail").getValue().toString();
                            gRol = ds.child("rol").getValue().toString();
                            gName = ds.child("name").getValue().toString();
                            gPass = ds.child("pass").getValue().toString();
                            Log.d("rellenando", gMail);
                            Log.d("rellenando", gRol);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void goMenu(String pMail)
    { //Método para guardar los datos del usuario introducido (ROL y MAIL)
        db.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    for(DataSnapshot ds: snapshot.getChildren()){
                        String texto = ds.child("mail").getValue().toString();
                        if (texto.equals(pMail)){
                            checkRol = ds.child("rol").getValue().toString();
                        }
                    }
                }
                //Mediante este if , en caso de ser usuario avanzado te mandará a la página de registro, en caso contrario no.
                if (checkRol.equals("avanzado")) {
                    Intent i = new Intent(IniciarSesion.this, Registro.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(IniciarSesion.this, "No tienes permisos para realizar dicha acción", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}


