package br.com.crudjaimenejaim.app.monitoramentoapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.crudjaimenejaim.app.monitoramentoapp.R;
import br.com.crudjaimenejaim.app.monitoramentoapp.model.Monitoramento;



/**
 * Created by jaimenejaim on 17/07/17.
 */


public class MonitoramentoListViewAdapter extends BaseAdapter {


    Context mContext;
    LayoutInflater mInflater;
    List<Monitoramento> mMonitoramentos;

    public MonitoramentoListViewAdapter(Context context, LayoutInflater inflater, List<Monitoramento> mMonitoramentos) {
        mContext = context;
        mInflater = inflater;
        this.mMonitoramentos = mMonitoramentos;
    }

    @Override
    public int getCount() {
        return mMonitoramentos.size();
    }

    @Override
    public Monitoramento getItem(int position) {
        return mMonitoramentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.row_listview_monitoramento, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.cpf = (TextView) convertView.findViewById(R.id.cpf);
            holder.phone = (TextView) convertView.findViewById(R.id.phone);
            holder.mail = (TextView) convertView.findViewById(R.id.mail);
            holder.created_at = (TextView) convertView.findViewById(R.id.created_at);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Monitoramento monitoramento = (Monitoramento) getItem(position);

            holder.name.setText(monitoramento.getName());
            holder.cpf.setText(monitoramento.getCpf());
            holder.phone.setText(monitoramento.getPhone());
            holder.mail.setText(monitoramento.getMail());

        try {


            holder.created_at.setText(formatDate(monitoramento.getCreated_at(),"yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy") + " às "+ formatDate(monitoramento.getCreated_at(),"yyyy-MM-dd HH:mm:ss", "HH:mm"));


        } catch (ParseException e) {
            e.printStackTrace();
        }


        return convertView;
    }



    public static class ViewHolder{
        public TextView name;
        public TextView cpf;
        public TextView phone;
        public TextView mail;
        public TextView created_at;
    }

    public void updateData(ArrayList<Monitoramento> monitoramentos) {
        mMonitoramentos = monitoramentos;
        notifyDataSetChanged();
    }

    public void updateSingleData(Monitoramento monitoramento) {
        List<Monitoramento> monitoramentosNew = new ArrayList<>();

        for(int i = 0; i <mMonitoramentos.size(); i++){
            if(mMonitoramentos.get(i).getId() == monitoramento.getId()){
                monitoramentosNew.add(monitoramento);
            }else{
                monitoramentosNew.add(mMonitoramentos.get(i));
            }
        }
        mMonitoramentos = monitoramentosNew;
        notifyDataSetChanged();
    }

    public void appendData(ArrayList<Monitoramento> monitoramentos) {
        if (mMonitoramentos.size() == 0){
            mMonitoramentos = monitoramentos;
            notifyDataSetChanged();
        }else{
                for (int i = 0; i < monitoramentos.size(); i++) {
                    mMonitoramentos.add(monitoramentos.get(i));
                }

            notifyDataSetChanged();
        }
    }



    public void appendObject(Monitoramento monitoramento) {

        List<Monitoramento> monitoramentosNew = new ArrayList<>();

        monitoramentosNew.add(0,monitoramento);

        for(int i = 0; i < mMonitoramentos.size(); i++){
            monitoramentosNew.add(mMonitoramentos.get(i));
        }
        mMonitoramentos = monitoramentosNew;
        notifyDataSetChanged();
    }

    public void removeObject(Monitoramento monitoramento) {
        mMonitoramentos.remove(monitoramento);
        notifyDataSetChanged();
    }


    public void clearData() {
        mMonitoramentos = new ArrayList<Monitoramento>();
    }




    public static String formatDate (String date, String initDateFormat, String endDateFormat) throws ParseException {

        Date initDate = new SimpleDateFormat(initDateFormat).parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
        String parsedDate = formatter.format(initDate);

        return parsedDate;
    }



}
