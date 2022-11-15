package ro.ase.ppoo.clase;

import java.io.Serializable;
import java.util.Date;

public abstract class Persoana implements Serializable {
    private String nume;
    private String prenume;
    private String cnp;
    private Date dataNasterii;
    private String sex;

    public Persoana() {
    }

    public Persoana(String nume, String prenume, String cnp, Date dataNasterii, String sex) {
        this.nume = nume;
        this.prenume = prenume;
        this.cnp = cnp;
        this.dataNasterii = dataNasterii;
        this.sex = sex;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public Date getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(Date dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
