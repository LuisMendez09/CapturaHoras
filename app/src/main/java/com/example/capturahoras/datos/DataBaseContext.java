package com.example.capturahoras.datos;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.capturahoras.complemento.Complementos;

import java.io.File;

public class DataBaseContext extends ContextWrapper {
    public DataBaseContext(Context base) {
        super(base);
    }

    @Override
    public File getDatabasePath(String name) {
        File storageDir = Complementos.rutaAlmacenamiento(getBaseContext());
        File result = null;

        String dbfile = storageDir.getAbsolutePath() + File.separator+ "databases" + File.separator + name;

        result = new File(dbfile);

        if (!result.getParentFile().exists()) {
            result.getParentFile().mkdirs();
        }

        return result;
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, @Nullable DatabaseErrorHandler errorHandler) {
        return openOrCreateDatabase(name,mode, factory);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
    }
}
