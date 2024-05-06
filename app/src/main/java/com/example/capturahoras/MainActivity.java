package com.example.capturahoras;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.Exceptions;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.controlador.ImportarDatos;
import com.example.capturahoras.controlador.SesionControl;
import com.example.capturahoras.databinding.ActivityMainBinding;
import com.example.capturahoras.modelo.Settings;
import com.example.capturahoras.ui.dialogos.DialogCargarCatalogos;
import com.example.capturahoras.ui.dialogos.DialogoProgreso;
import com.example.capturahoras.ui.dialogos.Dialogs;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity implements DialogoProgreso.actualizacionCatalogos, DialogCargarCatalogos.IDialogo{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FileLog.e(Complementos.TAG_SETTINGS, "inicia Main");


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.subtitulo);
        navUsername.setText(Settings.USUARIO);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_fin_jornada:
                FileLog.e(Complementos.TAG_MAIN, "preciono menu fin jornada "+ Settings.USUARIO);
                if (SesionControl.validarSesion()) {
                    //SesionControl.finalizarSesion();
                    //dialogoInf("Session finalizada");
                    dialogoFinaliar();
                    break;
                }

                dialogoInf("Session finalizada");
                break;
            case R.id.action_enviar:
                FileLog.e(Complementos.TAG_MAIN, "preciono menu enviar ");
                new DialogoProgreso(MainActivity.this,this, ImportarDatos.KEY_CATALOGOS,ImportarDatos.REGISTROS_SIN_ENVIAR,this);

                //dialogoInf("Session no finalizada\nfinalize sesion para enviar los registros");
                break;
            /*case R.id.action_actualizarCampos:
                FileLog.e(Complementos.TAG_MAIN, "preciono menu actualizar campos ");
                new DialogoProgreso(MainActivity.this,this, ImportarDatos.KEY_CATALOGOS,ImportarDatos.CAMPOS_UPDATE,this);
                //    break;
                //}
                dialogoInf("Session no finalizada\nfinalize sesion para enviar los registros");
                break;*/
            case R.id.action_importar:
                    FileLog.i(Complementos.TAG_MAIN, "actualizar catalogos");
                    new DialogoProgreso(MainActivity.this,this, ImportarDatos.KEY_CATALOGOS,ImportarDatos.TODOS,this);
                    break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void dialogoInf(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
// 2. Chain together various setter methods to set the dialog characteristics.
        builder.setMessage(mensaje)
                .setTitle("Captura de asistencia")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void dialogoFinaliar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
// 2. Chain together various setter methods to set the dialog characteristics.
        builder.setMessage("Al finalizar la jornada, no podra realizar modificaciones a los registros \n¿Esta seguro de finalizar la jornada?")
                .setTitle("Captura de asistencia")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SesionControl.finalizarSesion();
                        dialogInterface.dismiss();
                        dialogoInf("Session finalizada");
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void finalizarDialog(boolean resultado) {
       return;
    }

    @Override
    public void finilazacion(String error) {
        if(error.length()==0){
            return;
        }else{
            Dialogs.DialogError(error);
            FileLog.i(Complementos.TAG_LOGIN, error);
        }
    }
}