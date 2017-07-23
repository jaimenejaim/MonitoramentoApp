package br.com.crudjaimenejaim.app.monitoramentoapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


/**
 * Created by jaimenejaim on 17/07/17.
 */


public class Monitoramento implements Parcelable {


    private int id;
    private String name;
    private String cpf;
    private String phone;
    private String mail;
    private String created_at;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Monitoramento(Parcel in) {
        id = in.readInt();
        name = in.readString();
        cpf = in.readString();
        phone = in.readString();
        mail = in.readString();
        created_at = in.readString();

    }

    public Monitoramento(){

    }

    public static final Creator<Monitoramento> CREATOR = new Creator<Monitoramento>() {


        @Override
        public Monitoramento createFromParcel(Parcel in) {
            return new Monitoramento(in);
        }

        @Override
        public Monitoramento[] newArray(int size) {
            return new Monitoramento[size];
        }
    };




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(cpf);
        dest.writeString(phone);
        dest.writeString(mail);
        dest.writeString(created_at);

    }
}




