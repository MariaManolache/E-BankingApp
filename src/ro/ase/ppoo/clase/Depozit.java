package ro.ase.ppoo.clase;

import java.io.Serializable;

public class Depozit implements Serializable {

    private final String iban;
    private Client client;
    private String denumire;
    private Perioada perioada;
    private String ibanContDebit;
    private final double dobanda = 0.05;
    private double suma;

    public Depozit(Client client, String denumire, Perioada perioada, String ibanContDebit, double suma) {
        this.iban = "RO19MNL" + RandomIban.getAlphaNumericString(13);
        this.client = client;
        this.denumire = denumire;
        this.perioada = perioada;
        this.ibanContDebit = ibanContDebit;
        this.suma = suma;
    }

    public Depozit(String iban, Client client, String denumire, Perioada perioada, String ibanContDebit, double suma) {
        this.iban = iban;
        this.client = client;
        this.denumire = denumire;
        this.perioada = perioada;
        this.ibanContDebit = ibanContDebit;
        this.suma = suma;
    }

    public String getIban() {
        return iban;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public Perioada getPerioada() {
        return perioada;
    }

    public void setPerioada(Perioada perioada) {
        this.perioada = perioada;
    }

    public String getIbanContDebit() {
        return ibanContDebit;
    }

    public void setIbanContDebit(String ibanContDebit) {
        this.ibanContDebit = ibanContDebit;
    }

    public double getDobanda() {
        return dobanda;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    @Override
    public String toString() {
        return "Depozit: " +
                "iban: " + iban + ", " +
                "idClient: " + client.getIdClient() + ", " +
                "denumire: " + denumire + ", " +
                "perioada: " + perioada + ", " +
                "ibanContDebit: " + ibanContDebit + ", " +
                "dobanda: " + dobanda + ", " +
                "suma: " + suma;
    }
}
