package com.example.capturahoras;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.Exceptions;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.controlador.Controlador;
import com.example.capturahoras.controlador.ImportarDatos;
import com.example.capturahoras.controlador.SesionControl;
import com.example.capturahoras.modelo.Settings;
import com.example.capturahoras.ui.dialogos.DialogCargarCatalogos;
import com.example.capturahoras.ui.dialogos.DialogUrl;
import com.example.capturahoras.ui.dialogos.DialogoProgreso;
import com.example.capturahoras.ui.dialogos.Dialogs;

public class inicioActivity extends AppCompatActivity implements DialogoProgreso.actualizacionCatalogos, DialogCargarCatalogos.IDialogo{
    private EditText etUsuario;
    private Button btniniciar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etUsuario = findViewById(R.id.et_usuario);
        btniniciar = findViewById(R.id.btn_iniciar);

        new Controlador(this.getBaseContext());

        btniniciar.setOnClickListener(v->iniciar());
        etUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().equals(""))
                    btniniciar.setEnabled(false);
                else
                    btniniciar.setEnabled(true);
            }
        });

        FileLog.open(this.getBaseContext(), Log.VERBOSE, 3000000);
        FileLog.i(Complementos.TAG_LOGIN, "Log iniciado");

        SesionControl.InicializarContext(this);
    }

    @Override
    protected void onResume() {
        super.onResume();


        if(!SesionControl.inicializarSesion()){
            Dialogs.DialogError("Error al iniciar sesion");
            return;
        }

        boolean b = new SesionControl().actualizarInicioSesion();
        if(!b)
            Dialogs.DialogError(Exceptions.exception.getMessage());

        btniniciar.setEnabled(SesionControl.getUsuario());
        etUsuario.setText(Settings.USUARIO);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                //mostrar cuadro de dialogo de url
                showDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogUrl newFragment = new DialogUrl();

        // The device is smaller, so show the fragment fullscreen
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // For a little polish, specify a transition animation
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        // To make it fullscreen, use the 'content' root view as the container
        // for the fragment, which is always the root view for the activity
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();

    }

    private void iniciar(){
        //guardar usuario y continuar

        if(Settings.URL.length()==0){
            Dialogs.DialogError("Falta configuracion");
            FileLog.i(Complementos.TAG_LOGIN, "falta configurar catalogos");
            return;
        }

        boolean b = SesionControl.actualizarUsuario(etUsuario.getText().toString());
        if(b) {
            if(!SesionControl.validarCatalogos()){
                FileLog.i(Complementos.TAG_LOGIN, "actualizar catalogos");
                new DialogoProgreso(inicioActivity.this,this, ImportarDatos.KEY_CATALOGOS,ImportarDatos.TODOS,this);
            }else{
                irMain();
            }
        }
    }

    private void irMain(){
        iniciarDialog();
        new DialogCargarCatalogos(this);
    }

    public void iniciarDialog() {
        progressDialog = new ProgressDialog(inicioActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.msn_titulo_cargaCatalog));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void finilazacion(String error) {
        if(error.length()==0){
            irMain();
        }else{
            Dialogs.DialogError(error);
            FileLog.i(Complementos.TAG_LOGIN, error);
        }
    }

    @Override
    public void finalizarDialog(boolean b) {
        progressDialog.dismiss();
        if(!b){
            FileLog.i(Complementos.TAG_LOGIN, Exceptions.exception.getMessage());
            return;
        }

        FileLog.i(Complementos.TAG_LOGIN, "ir a MainActivity");
        Intent mainIntent = new Intent().setClass(inicioActivity.this, MainActivity.class);
        startActivity(mainIntent);

        // cierra la actividad
        finish();
    }
}