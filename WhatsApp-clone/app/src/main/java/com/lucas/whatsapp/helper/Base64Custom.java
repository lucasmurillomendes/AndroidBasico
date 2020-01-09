package com.lucas.whatsapp.helper;

import android.util.Base64;

public class Base64Custom {

    public static String codigicarBase64(String texto){

        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT)
                                                    .replaceAll("(\\n|\\r)", "");


    }

    public static String decodigicarBase64(String textocodificado){
        Base64.decode(textocodificado, Base64.DEFAULT);
        return new String(  Base64.decode(textocodificado, Base64.DEFAULT));
    }

}
