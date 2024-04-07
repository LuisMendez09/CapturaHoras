package com.example.capturahoras.ui.dialogos;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;


import com.example.capturahoras.R;
import com.example.capturahoras.complemento.Complementos;
import com.example.capturahoras.complemento.FileLog;
import com.example.capturahoras.controlador.ImportarDatos;

import java.util.UUID;

public class DialogoProgreso {
    public interface actualizacionCatalogos{
        void finilazacion(String error);
    }

    private String ID_TAG = "101";

    private ProgressDialog progressDialog;
    private Context context;
    private LifecycleOwner observador;
    private actualizacionCatalogos actualizacion;

    public DialogoProgreso(Context context, LifecycleOwner observador, String tipoImportacion, String valorAimportar, actualizacionCatalogos actualizacion) {
        this.context = context;
        this.observador = observador;
        this.actualizacion = actualizacion;

        Data data = new Data.Builder().putString(tipoImportacion,valorAimportar).build();
        progreso(data);
    }

    private void progreso(Data data){
        progressDialog();
        FileLog.i(Complementos.TAG_CATALOGOS,"inicia la importacion de catalogos");
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(ImportarDatos.class)
                .addTag(ID_TAG)
                .setInputData(data)
                .build();

        WorkManager instance = WorkManager.getInstance(context);
        instance.enqueueUniqueWork(ID_TAG, ExistingWorkPolicy.KEEP, request);

        observadorServicio(request.getId());
    }

    private void observadorServicio(UUID id){

        WorkManager.getInstance(context)
                .getWorkInfoByIdLiveData(id)
                .observe(observador, workInfo -> {
                    if(workInfo!=null){
                        Data progress = workInfo.getProgress();
                        int increment =progress.getInt(ImportarDatos.PROGRESS,-1);
                        int total = progress.getInt(ImportarDatos.TOTAL,-1);
                        boolean finalizar = progress.getBoolean(ImportarDatos.FINISH,false);
                        String msn = progress.getString(ImportarDatos.MENSAjE);
                        String error = progress.getString(ImportarDatos.ERROR);

                        if(total>-1)
                            inicializarprogressDialog(total);

                        if(msn!=null)
                            actualziarMensaje(msn);

                        if(error!=null){
                            actualziarError(error);
                            return;
                        }

                        switch (workInfo.getState()){
                            case RUNNING:
                                if(increment!=-1)
                                    incrementarAvance(increment);

                                if(finalizar)
                                    finalizar();
                                break;
                            case SUCCEEDED:
                                Toast.makeText(context,"Actualizacion exitosa",Toast.LENGTH_LONG);
                                //Snackbar.make(view, "Actualizacion exitosa", Snackbar.LENGTH_LONG).show();
                                break;

                        }
                    }
                });
    }

    private void progressDialog(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(R.string.msn_titulo_descargaCatalog);
        progressDialog.setMessage(" ");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(0);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void inicializarprogressDialog(int totalRegistros){
        progressDialog.setMax(totalRegistros);
        progressDialog.setProgress(0);
    }

    public void actualziarMensaje(String msn){
        progressDialog.setMessage(msn);
    }

    public void actualziarError(String error){
        progressDialog.dismiss();
        actualizacion.finilazacion(error);
        FileLog.i(Complementos.TAG_CATALOGOS,error);
    }

    public void incrementarAvance(int incremento) {
        progressDialog.setProgress(incremento);
    }

    public void finalizar(){
        progressDialog.dismiss();
        actualizacion.finilazacion("");
        FileLog.i(Complementos.TAG_CATALOGOS,"finalizo la importacion de catalogos");
    }
}
