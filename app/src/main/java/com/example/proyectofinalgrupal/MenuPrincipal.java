package com.example.proyectofinalgrupal;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MenuPrincipal extends AppCompatActivity {



    private FirebaseAuth mAuth;

    private View informacion;
    private View Cerrarsesion;
    private Button contratarProductos;
    private  View MostrarTiendas;
    private View Consumo;
    String getMail;
    String getRol;
    String getPass;
    String getName;
    private DatabaseReference db;
    //private TextView mTextViewData;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        getMail = "";
        getPass = "";
        getRol = "";
        getName = "";

        //Instanciamos firebase & variables
        Cerrarsesion = (View) findViewById(R.id.Cerrarsesion);
        informacion = (View) findViewById(R.id.informacion);
        MostrarTiendas = (View) findViewById(R.id.MostrarTiendas);
        mAuth = FirebaseAuth.getInstance();
        contratarProductos = (Button) findViewById(R.id.webView);
       // mTextViewData = (TextView) findViewById(R.id.textViewData);
        Consumo = (View) findViewById(R.id.consumo);

        //Guardamos valores pasados de la otra pantalla
        getMail = getIntent().getStringExtra("mail");
        getPass = getIntent().getStringExtra("pass");
        getName = getIntent().getStringExtra("name");
        getRol = getIntent().getStringExtra("rol");

        contratarProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuPrincipal.this, RedireccionExterna.class);
                i.putExtra("mail", getMail); //Te mete la variable del Mail para que en la otra clase la obtenga directamente
                i.putExtra("rol", getRol);
                i.putExtra("pass", getPass);
                i.putExtra("name", getName);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.informacion) {
            Intent i = new Intent(MenuPrincipal.this,Informacion.class);
            i.putExtra("mail", getMail); //Te mete la variable del Mail para que en la otra clase la obtenga directamente
            i.putExtra("rol", getRol);
            i.putExtra("pass", getPass);
            i.putExtra("name", getName);
            startActivity(i);
            finish();
            return true;
        }
        if (id == R.id.consumo) {
            Intent i = new Intent(MenuPrincipal.this, MenuConsumos.class);
            i.putExtra("mail", getMail);//Te mete la variable del Mail para que en la otra clase la obtenga directamente
            i.putExtra("rol", getRol);
            i.putExtra("pass", getPass);
            i.putExtra("name", getName);
            startActivity(i);
            finish();
            return true;
        }
        if (id == R.id.MostrarTiendas) {
            Intent i = new Intent(MenuPrincipal.this,MapaTiendas.class);
            i.putExtra("mail", getMail);//Te mete la variable del Mail para que en la otra clase la obtenga directamente
            i.putExtra("rol", getRol);
            i.putExtra("pass", getPass);
            i.putExtra("name", getName);
            startActivity(i);
            finish();
            return true;
        }
        if (id == R.id.Cerrarsesion) {
            mAuth.signOut();
            startActivity(new Intent(MenuPrincipal.this, IniciarSesion.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}

