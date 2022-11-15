package ro.ase.ppoo.clase;

import ro.ase.ppoo.exceptii.ExceptieFonduriInsuficiente;

import java.io.Serializable;

public abstract class Cont implements Serializable {

    private String idClient;
    private String iban;
    private Valuta valuta;
    private double balanta;
    private String tip;
    private double limita;

    public Cont(String idClient, Valuta valuta) {
        this.idClient = idClient;
        this.iban = "RO19MNL" + RandomIban.getAlphaNumericString(13);
        this.valuta = valuta;
        this.balanta = 0.0;
    }

    public Cont(String idClient, Valuta valuta, double limita) {
        this.idClient = idClient;
        this.iban = "RO19MNL" + RandomIban.getAlphaNumericString(13);
        this.valuta = valuta;
        this.balanta = 0.0;
        this.limita = limita;
    }

    public Cont(String idClient, String iban, Valuta valuta, double balanta, String tip) {
        this.idClient = idClient;
        this.iban = iban;
        this.valuta = valuta;
        this.balanta = balanta;
        this.tip = tip;
    }

    public Cont(String idClient, String iban, Valuta valuta, double balanta, String tip, double limita) {
        this.idClient = idClient;
        this.iban = iban;
        this.valuta = valuta;
        this.balanta = balanta;
        this.tip = tip;
        this.limita = limita;
    }

    public String getIdClient() {
        return idClient;
    }
    public double getBalanta() {
        return balanta;
    }

    public void setBalanta(double balanta) {
        this.balanta = balanta;
    }

    public String getIban() {
        return iban;
    }

    public Valuta getValuta() {
        return valuta;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public double getLimita() {
        return limita;
    }

    public void setLimita(double limita) {
        this.limita = limita;
    }


    protected abstract boolean depunere(double suma);
    public abstract boolean retragere(double suma) throws ExceptieFonduriInsuficiente;
    public void interogareSold() {
        System.out.println("Soldul contului " + iban + " este " + balanta);
    }

    public void transfer(double suma, Cont contDestinatie) throws ExceptieFonduriInsuficiente {
        if(contDestinatie == this)
        {
            System.out.println("Nu se pot transfera bani in acelasi cont.");
        }
        else
        {
            if(retragere(suma) == true) {
                if(contDestinatie.depunere(suma) == false) {
                    depunere(suma);
                }
            }
            interogareSold();
        }
    }

    @Override
    public String toString() {
        String result =  "Cont: " +
                "idClient: " + idClient  +
                ", iban: " + iban +
                ", valuta: " + valuta +
                ", balanta: " + balanta +
                ", tip: " + tip;
                ;
        if(tip.equals("Credit")) {
            result += ", limita: " + getLimita();
        }
        result +=  "; ";
        return result;
    }
}
