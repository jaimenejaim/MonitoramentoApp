package br.com.crudjaimenejaim.app.monitoramentoapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.crudjaimenejaim.app.monitoramentoapp.R;
import br.com.crudjaimenejaim.app.monitoramentoapp.business.add;
import br.com.crudjaimenejaim.app.monitoramentoapp.db.DatabaseHandler;
import br.com.crudjaimenejaim.app.monitoramentoapp.model.Monitoramento;
import br.com.crudjaimenejaim.app.monitoramentoapp.util.MaskType;
import br.com.crudjaimenejaim.app.monitoramentoapp.util.MaskUtil;
import br.com.crudjaimenejaim.app.monitoramentoapp.util.Titelize;


/**
 * Created by jaimenejaim on 17/07/17.
 */


public class AddActivity extends AppCompatActivity {


    private static final int ACTIVITY_REQUEST_NUMBER_ADD = 1;

    private EditText name;
    private EditText cpf;
    private EditText phone;
    private EditText mail;

    private ImageView save;
    private ImageView back;


    DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        name = (EditText) findViewById(R.id.name);
        cpf = (EditText) findViewById(R.id.cpf);
        cpf.addTextChangedListener(MaskUtil.insert(cpf, MaskType.CPF)); // Mascara cpf
        phone = (EditText) findViewById(R.id.phone);
//        phone.addTextChangedListener(MaskUtil.insert(phone, MaskType.PHONE)); // Mascara telefone

        mail = (EditText) findViewById(R.id.mail);
        save = (ImageView) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });


        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }







    public void save(){


        Monitoramento monitoramento = new Monitoramento();

        monitoramento.setName(Titelize.titleize(name.getText().toString()));
        monitoramento.setCpf(cpf.getText().toString());
        monitoramento.setPhone(phone.getText().toString());
        monitoramento.setMail(mail.getText().toString());

        if(!validate(monitoramento)) {

            db = new DatabaseHandler(AddActivity.this);
            db.addMonitoramento(monitoramento);


            db = new DatabaseHandler(AddActivity.this);

            Toast.makeText(this, "Monitoramento cadastrado com sucesso!!", Toast.LENGTH_LONG).show();

            Intent returnIntent = new Intent();
            returnIntent.putExtra("object", db.getLastAddedMonitoramento());
            setResult(ACTIVITY_REQUEST_NUMBER_ADD, returnIntent);
            finish();
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        }
    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    /* Validações básicas do formulario */
    public boolean validate(Monitoramento monitoramento){
        if(monitoramento.getName().length() == 0){
            name.setError("Digite seu nome.");
            return true;
        }
        if(monitoramento.getCpf().length() == 0){
            cpf.setError("Digite seu CPF.");
            return true;
        }
        if(monitoramento.getPhone().length() == 0){
            phone.setError("Digite um número telefônico.");
            return true;
        }
        if(monitoramento.getMail().length() == 0){
            mail.setError("Digite um endereço de e-mail.");
            return true;
        }
        if(monitoramento.getCpf().length() < 14){
            cpf.setError("Verifique se seu CPF está completo.");
            return true;
        }
//        if(monitoramento.getPhone().length() < 14){
//            phone.setError("Verifique se o número telefônico informado está completo.");
//            return true;
//        }
        if(!add.isValidEmail(monitoramento.getMail())){
            mail.setError("Digite seu e-mail corretamente.");
            return true;
        }

        return false;

    }





}
