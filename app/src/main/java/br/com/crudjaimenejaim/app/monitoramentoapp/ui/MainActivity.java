package br.com.crudjaimenejaim.app.monitoramentoapp.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import br.com.crudjaimenejaim.app.monitoramentoapp.R;
import br.com.crudjaimenejaim.app.monitoramentoapp.db.DatabaseHandler;
import br.com.crudjaimenejaim.app.monitoramentoapp.model.Monitoramento;
import br.com.crudjaimenejaim.app.monitoramentoapp.ui.adapters.MonitoramentoListViewAdapter;


/**
 * Created by jaimenejaim on 17/07/17.
 */


public class MainActivity extends AppCompatActivity {


    private ListView mListView;
    private MonitoramentoListViewAdapter mListViewAdapter;
    private DatabaseHandler db;

    private ImageView add;

    private static final int ACTIVITY_REQUEST_NUMBER_ADD = 1;
    private static final int ACTIVITY_REQUEST_NUMBER_DETAIL_EDIT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        add = (ImageView) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                MainActivity.this.startActivityForResult(intent,ACTIVITY_REQUEST_NUMBER_ADD);
                overridePendingTransition(R.anim.activity_in,R.anim.activity_out);

            }
        });



        mListView = (ListView) findViewById(R.id.listview);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Monitoramento monitoramento = (Monitoramento) parent.getAdapter().getItem(position);

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("object", monitoramento);
                startActivityForResult(intent, ACTIVITY_REQUEST_NUMBER_DETAIL_EDIT);
                overridePendingTransition(R.anim.activity_in,R.anim.activity_out);


            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                removeMonitoramento((Monitoramento) parent.getItemAtPosition(position));
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Deseja remover este monitoramento?").setPositiveButton("Sim", dialogClickListener)
                        .setNegativeButton("Não", dialogClickListener).show();


                return true;
            }
        });


        new AsyncTask<Void, Void, List<Monitoramento>>() {

            public ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                dialog = ProgressDialog.show(MainActivity.this,"Aguarde...","listando monitoramentos.",true,true);
            }

            @Override
            protected List<Monitoramento> doInBackground(Void... params) {

                db = new DatabaseHandler(MainActivity.this);

                return db.listMonitoramento();
            }

            @Override
            protected void onPostExecute(List<Monitoramento> monitoramentos) {
                dialog.dismiss();

                mListViewAdapter = new MonitoramentoListViewAdapter(MainActivity.this, getLayoutInflater(), monitoramentos);

                mListView.setAdapter(mListViewAdapter);

            }
        }.execute();


    }



    public void removeMonitoramento(Monitoramento monitoramento){

        db = new DatabaseHandler(MainActivity.this);
        db.removeMonitoramento(monitoramento);
        mListViewAdapter.removeObject(monitoramento);
        mListViewAdapter.notifyDataSetChanged();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if(resultCode == ACTIVITY_REQUEST_NUMBER_ADD){
                Monitoramento monitoramento = data.getParcelableExtra("object");


                mListViewAdapter.appendObject(monitoramento);
                mListView.setAdapter(mListViewAdapter);
            }
            if (resultCode == ACTIVITY_REQUEST_NUMBER_DETAIL_EDIT) {
                Monitoramento monitoramento = data.getParcelableExtra("object");

                Log.i("edited",""+monitoramento.getId());
                Log.i("edited",""+monitoramento.getName());
                mListViewAdapter.updateSingleData(monitoramento);
                mListView.setAdapter(mListViewAdapter);
            }

    }


}
