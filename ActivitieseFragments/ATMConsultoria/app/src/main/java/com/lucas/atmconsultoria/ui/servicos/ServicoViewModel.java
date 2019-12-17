package com.lucas.atmconsultoria.ui.servicos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ServicoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ServicoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Fragmento Servico");
    }

    public LiveData<String> getText() {
        return mText;
    }
}