package com.lucas.organizze.helper;

import java.text.SimpleDateFormat;

public class DateCustom {
    public static String dataAtual(){

        long date = System.currentTimeMillis();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String data = df.format(date);

        return data;
    }

    public static String mesAnoDataEscolhida(String data){

        String[] retornodata = data.split("/");
        String dia = retornodata[0];
        String mes = retornodata[1];
        String ano = retornodata[2];

        String mesAno = mes + ano;

        return mesAno;

    }
}
