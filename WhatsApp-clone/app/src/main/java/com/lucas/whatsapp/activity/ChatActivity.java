package com.lucas.whatsapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lucas.whatsapp.R;
import com.lucas.whatsapp.adapter.MensagensAdapter;
import com.lucas.whatsapp.config.ConfiguracaoFirebase;
import com.lucas.whatsapp.helper.Base64Custom;
import com.lucas.whatsapp.helper.UsuarioFirebase;
import com.lucas.whatsapp.model.Conversa;
import com.lucas.whatsapp.model.Mensagem;
import com.lucas.whatsapp.model.Usuario;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private TextView textViewNome;
    private CircleImageView fotoPerfil;
    private Usuario usuarioDestinatario;
    private EditText editMensagem;

    private ImageView imageCamera;
    private static final int SELECAO_CAMERA = 100;

    //Identificador usuário remetente e destinatário
    private String idUsuarioRemetente;
    private String idUsuarioDestinatario;

    private RecyclerView recyclerMensagem;
    private MensagensAdapter adapter;
    private List<Mensagem> listaMensagens = new ArrayList<>();

    private DatabaseReference database;
    private DatabaseReference mensagensRef;
    private StorageReference storageReference;
    private ChildEventListener childEventListenermensagens;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Configurações iniciais
        textViewNome = findViewById(R.id.textViewNomeChat);
        fotoPerfil = findViewById(R.id.circleImageFotoChat);
        editMensagem = findViewById(R.id.editMensagem);
        recyclerMensagem = findViewById(R.id.recyclerMensagem);
        imageCamera = findViewById(R.id.imageCamera);

        storageReference = ConfiguracaoFirebase.getFirebaseStorage();

        //Recupera id usuario remetente
        idUsuarioRemetente = UsuarioFirebase.getIdentificadorUsuario();

        //Recuperar dados do usuário Destinatário
        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            usuarioDestinatario = (Usuario) bundle.getSerializable("chatContato");
            textViewNome.setText(usuarioDestinatario.getNome());

            String foto = usuarioDestinatario.getFoto();

            if (foto != null) {
                Uri url = Uri.parse(foto);
                Glide.with(ChatActivity.this)
                        .load(url)
                        .into(fotoPerfil);
            }else{
                fotoPerfil.setImageResource(R.drawable.padrao);
            }

            //recupera os dados do destinatário
            idUsuarioDestinatario = Base64Custom.codigicarBase64(usuarioDestinatario.getEmail());
        }

        //Configurar o adapter
        adapter = new MensagensAdapter(listaMensagens, getApplicationContext());

        //Configurar recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerMensagem.setLayoutManager(layoutManager);
        recyclerMensagem.setHasFixedSize(true);
        recyclerMensagem.setAdapter(adapter);

        database = ConfiguracaoFirebase.getFirebaseDataBase();
        mensagensRef = database.child("mensagens")
                .child(idUsuarioRemetente)
                .child(idUsuarioDestinatario);

        //Evento de clique na camera
        imageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //abrindo camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //se existe camera/software de foto
                if (intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent, SELECAO_CAMERA);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Bitmap imagem = null;

            try {

                switch (requestCode) {
                    case SELECAO_CAMERA:
                        imagem = (Bitmap) data.getExtras().get("data");
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            if (imagem != null){

                //Recuperando dados da imagem p/ firebase
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imagem.compress(Bitmap.CompressFormat.JPEG, 75, baos);
                byte[] dadosImagem = baos.toByteArray();

                //Criar nome da imagem
                String nomeImagem = UUID.randomUUID().toString();

                //Salvando imagem no firebase
                StorageReference imagemRef = storageReference
                        .child("imagens")
                        .child("fotos")
                        .child(idUsuarioRemetente)
                        .child(nomeImagem + ".jpeg");

                UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Erro", "Erro ao fazer upload");
                        Toast.makeText(ChatActivity.this,
                                "Erro ao fazer Upload de imagem",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> url = taskSnapshot.getStorage().getDownloadUrl();
                        while (!url.isSuccessful());
                        Uri X = url.getResult();
                        String caminho = X.toString();

                        Mensagem mensagem = new Mensagem();
                        mensagem.setIdusuario(idUsuarioRemetente);
                        mensagem.setMensagem("imagem.jpeg");
                        mensagem.setImagem(caminho);

                        //Salvar para remetente
                        salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem);
                        //Salvar para o destinatario
                        salvarMensagem(idUsuarioDestinatario, idUsuarioRemetente, mensagem);

                    }
                });
            }

            }
    }

    public void enviarMensagem(View view){

        String textoMensagem = editMensagem.getText().toString();

        if (!textoMensagem.isEmpty()){

            Mensagem mensagem = new Mensagem();
            mensagem.setIdusuario(idUsuarioRemetente);
            mensagem.setMensagem(textoMensagem);

            //Salvar mensagem para o remetente
            salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem);

            //Salvar mensagem para o Destinatario
            salvarMensagem(idUsuarioDestinatario, idUsuarioRemetente, mensagem);

            //Salvar conversa na tela inicial
            salvarConversa(mensagem);

        }else{
            Toast.makeText(ChatActivity.this, "Digite uma mensagem para enviar.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void salvarMensagem(String idRemetente, String idDestinatario, Mensagem msg){

        DatabaseReference database = ConfiguracaoFirebase.getFirebaseDataBase();
        DatabaseReference mensagemRef = database.child("mensagens");

        mensagemRef.child(idRemetente)
                .child(idDestinatario)
                .push()
                .setValue(msg);
        //Limpa texto
        editMensagem.setText("");

    }

    private void salvarConversa(Mensagem msg){
        Conversa conversaRemetente = new Conversa();
        conversaRemetente.setIdDestinatario(idUsuarioDestinatario);
        conversaRemetente.setIdRemetente(idUsuarioRemetente);
        conversaRemetente.setUltimaMensagem(msg.getMensagem());
        conversaRemetente.setUsuarioExibicao(usuarioDestinatario);

        conversaRemetente.salvar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarMensagem();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mensagensRef.removeEventListener(childEventListenermensagens);
    }

    private void recuperarMensagem(){

        childEventListenermensagens = mensagensRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Mensagem mensagem = dataSnapshot.getValue(Mensagem.class);
                listaMensagens.add(mensagem);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
