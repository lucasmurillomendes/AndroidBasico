package com.lucas.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    /*
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth usuario = FirebaseAuth.getInstance();*/

    private ImageView imageFoto;
    private Button buttonUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageFoto = findViewById(R.id.imageFoto);
        buttonUpload = findViewById(R.id.buttonUpload);

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //SAlvar a imagem em memória
                imageFoto.setDrawingCacheEnabled(true);
                imageFoto.buildDrawingCache();

                //Recupera bitmap da imagem (image a ser carregada)
                Bitmap bitmap = imageFoto.getDrawingCache();

                //comprimir bitmap para um formato png/jpeg
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos);

                //Converte o baos para pixel bruto em uma matriz de bytes
                //(Dados da imagem)
                byte[] dadosImagem = baos.toByteArray();

                //Define nós para o storage
                StorageReference reference = FirebaseStorage.getInstance().getReference();
                //Cria a pasta
                StorageReference imagens = reference.child("Imagens");
                //nome do arquivo
                StorageReference imagemRef = imagens.child("celular.jpeg");

                //Baixando imagem do banco
                Glide.with(MainActivity.this)
                        .using(new FirebaseImageLoader())
                        .load(imagemRef)
                        .into(imageFoto);

                /*DELETANTO IMAGEM DO BANCO
                StorageReference imagemRef = imagens.child("celular.jpeg");
                imagemRef.delete().addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Erro ao deletar",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(MainActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Sucesso ao deletar imagem",
                                Toast.LENGTH_SHORT).show();
                    }
                });*/



                //Cria o arquivo em si
                //Define o nome da imagem randomico
               // String nomeArquivo = UUID.randomUUID().toString();


                //Retorna o objeto que controlará o upload
              /*  UploadTask uploadTask = imagemRef.putBytes(dadosImagem);

                uploadTask.addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Upload Da imagem falhou: "
                                        + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(MainActivity.this, "Upload Da imagem feito com sucesso",
                                                    Toast.LENGTH_SHORT).show();
                    }
                });*/


            }
        });




       /* DatabaseReference usuarios = referencia.child("usuarios");

        Pesquisas (Select)
        //Para um id específico
       // DatabaseReference usuarioPesquisa = usuarios.child("-Lwd6tEIY9Q6pqzqltTH");

        //Utilizando query e buscando por nome
        //Query usuarioPesquisa = usuarios.orderByChild("nome").equalTo("Marcelo");

        //limitando pesquisa aos primeiros
        //Query usuarioPesquisa = usuarios.orderByKey().limitToFirst(3);

        //limitando pesquisa aos ultimos
        //Query usuarioPesquisa = usuarios.orderByKey().limitToLast(3);

        /*Utilizando >= (Maior ou igual)
        Query usuarioPesquisa = usuarios.orderByChild("idade").startAt(35);*/

        /*Utilizando <= (Menor ou igual)
        Query usuarioPesquisa = usuarios.orderByChild("idade").endAt(30);*/

        /*Entre dois valores
        Query usuarioPesquisa = usuarios.orderByChild("idade")
                                    .startAt(18)
                                    .endAt(35);*/
         /* Entre dois valores
        Query usuarioPesquisa = usuarios.orderByChild("nome")
                                    .startAt("M")
                                    .endAt("M" + "\uf8ff");


        usuarioPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               // Usuario dadosUsuario = dataSnapshot.getValue(Usuario.class);

               // Log.i("Dados usuario", "nome: " + dadosUsuario.getNome() +
                // "idade: " + dadosUsuario.getIdade());

                Log.i("Dados usuario", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        /*Gerando identificador unico (ID);
        Usuario usuario = new Usuario();
        usuario.setNome("Rodrigo");
        usuario.setSobrenome("Matos");
        usuario.setIdade(35);

        usuarios.push().setValue(usuario);*/

        /*Login de usuario
        usuario.signInWithEmailAndPassword("lucas2@hotmail.com", "lucas12345")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("FIREBASE", "Usuario logado com sucesso");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("FIREBASE", "Erro ao logar usuario");
                        }
                    }
                });
                    */

        /*Deslogar usuario
        usuario.signOut();
            */

        /*verifica usuario logado
        if (usuario.getCurrentUser() != null){
            Log.i("FIREBASE", "Usuario Logado");
        }else{
            Log.i("FIREBASE", "Usuario nao esta Logado");
        }
           */

        /*Cadastrado usuario
        usuario.createUserWithEmailAndPassword("lucas2@hotmail.com", "lucas12345")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("FIREBASE", "Usuario criado com sucesso");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("FIREBASE", "Erro ao criar usuario");
                        }
                    }
                });
              */



        /*
        DatabaseReference usuarios = referencia.child("usuarios").child("001");
        DatabaseReference produtos = referencia.child("produtos");

        Usuario usuario = new Usuario();
        usuario.setNome("Maria");
        usuario.setSobrenome("Oliviero");
        usuario.setIdade(30);

       usuarios.child("002").setValue(usuario);

        Produto produto = new Produto();
        produto.setDescricao("Notebook Acer");
        produto.setMarca("Acer");
        produto.setPreco(1200.30);

        produtos.child("001").setValue(produto);

       usuarios.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

               Log.i("FIREBASE", dataSnapshot.getValue().toString());
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
               System.out.println("The read failed: " + databaseError.getCode());
           }
       });*/


    }
        }