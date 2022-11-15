package ro.ase.ppoo.clase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Client extends Persoana implements Serializable, ISerializare {

    private String idClient;
    private String parola;
    private Cont[] listaConturi;
    private Map<String, List<Transfer>> tranzactii = new HashMap<>();

    public Client(String idClient, String parola, Cont[] listaConturi) {
        this.idClient = idClient;
        this.parola = parola;
        this.listaConturi = listaConturi;
        tranzactii.put(idClient, new ArrayList<>());
    }

    public Client(String nume, String prenume, String cnp, Date dataNasterii, String sex, String idClient, String parola, Cont[] listaConturi) {
        super(nume, prenume, cnp, dataNasterii, sex);
        this.idClient = idClient;
        this.parola = parola;
        this.listaConturi = listaConturi;
        tranzactii.put(idClient, new ArrayList<>());
    }

    public Client(String nume, String prenume, String cnp, Date dataNasterii, String sex, String idClient, String parola) {
        super(nume, prenume, cnp, dataNasterii, sex);
        this.idClient = idClient;
        this.parola = parola;
        tranzactii.put(idClient, new ArrayList<>());
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public Cont[] getListaConturi() {
        return listaConturi;
    }

    public void setListaConturi(Cont[] listaConturi) {
        this.listaConturi = listaConturi;
    }

    public Map<String, List<Transfer>> getTranzactii() {
        return tranzactii;
    }

    public void setTranzactii(Map<String, List<Transfer>> tranzactii) {
        this.tranzactii = tranzactii;
    }

    public Cont[] adaugaCont(Cont cont) {
        if(this.listaConturi != null && this.listaConturi.length > 0 ) {
            Cont[] listaConturiActualizata = new Cont[this.listaConturi.length + 1];
            for(int i = 0; i < this.listaConturi.length; i++) {
                listaConturiActualizata[i] = listaConturi[i];
            }
            listaConturiActualizata[this.listaConturi.length] = cont;
            return listaConturiActualizata;
        } else {
            listaConturi = new Cont[1];
            listaConturi[0] = cont;
            return listaConturi;
        }
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    @Override
    public String toString() {
        String rezultat =  "Client{" +
                "idClient='" + idClient + '\'' +
                ", parola='" + parola + '\'' +
                ", nume='" + getNume() + '\'' +
                ", prenume='" + getPrenume() + '\'' +
                ", CNP='" + getCnp() + '\'' +
                ", data nasterii='" + getDataNasterii() + '\'' +
                ", sex='" + getSex() + '\'';
        if(listaConturi != null && listaConturi.length > 0) {
            for(int i = 0; i < listaConturi.length; i++) {
                rezultat += listaConturi[i].toString();
            }
        }
        return rezultat;
    }

    @Override
    public void SerializareObiect(DataOutputStream dos) throws IOException {
        dos.writeUTF(getNume());
        dos.writeUTF(getPrenume());
        dos.writeUTF(getCnp());
        dos.writeUTF(String.valueOf(getDataNasterii()));
        dos.writeUTF(getSex());
        dos.writeUTF(idClient);
        dos.writeUTF(parola);
    }

    @Override
    public void DeserializareObiect(DataInputStream dis) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        setNume(dis.readUTF());
        setPrenume(dis.readUTF());
        setCnp(dis.readUTF());
        try {
            setDataNasterii(sdf.parse(dis.readUTF()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setSex(dis.readUTF());
        idClient = dis.readUTF();
        parola = dis.readUTF();

    }
}
