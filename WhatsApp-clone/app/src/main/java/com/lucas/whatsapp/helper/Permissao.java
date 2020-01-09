package com.lucas.whatsapp.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {
    public static boolean validaPermissao(String[] permissoes, Activity activity, int requesteCode){

        if (Build.VERSION.SDK_INT >= 23){

            List<String> listaPermissoes = new ArrayList<>();
            /*
            Percorre as permissoes passadas perguntando se
            ja está liberada
             */
            for (String permissao: permissoes){
               Boolean temPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;

                if (!temPermissao) listaPermissoes.add(permissao);

            }

            if (listaPermissoes.isEmpty()) return true;
            String[] novasPermissoes = new String[ listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);

            //Solicita Permissoes
            ActivityCompat.requestPermissions(activity, novasPermissoes, requesteCode);

        }

        return true;
    }
}
