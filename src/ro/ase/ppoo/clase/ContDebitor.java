package ro.ase.ppoo.clase;

import ro.ase.ppoo.exceptii.ExceptieFonduriInsuficiente;

import java.io.Serializable;

public class ContDebitor extends Cont implements IBeneficii, Serializable {


    public ContDebitor(String idClient, Valuta valuta) {
        super(idClient, valuta);
        setTip("Debit");
    }

    public ContDebitor(String idClient, String iban, Valuta valuta, double balanta, String tip) {
        super(idClient, iban, valuta, balanta, tip);
    }

    public ContDebitor(String idClient, String iban, Valuta valuta, double balanta, String tip, double limita) {
        super(idClient, iban, valuta, balanta, tip, limita);
    }


    @Override
    protected boolean depunere(double suma) {
        setBalanta(getBalanta() + suma);
        return true;
    }

    @Override
    public boolean retragere(double suma) {
        if ((getBalanta() - suma) < 0.0)
        {
            try {
                throw new ExceptieFonduriInsuficiente("Fonduri insuficiente");
            } catch (ExceptieFonduriInsuficiente e) {
                System.out.println("Fonduri insuficiente");
            }
        } else {
            setBalanta(getBalanta() - suma);
            return true;
        }
        return false;
    }

    @Override
    public Depozit creeazaDepozit(Client client, String denumire, Perioada perioada, String ibanContDebit, double suma) {
        Depozit depozit = new Depozit(client, denumire, perioada, ibanContDebit, suma);
        System.out.println("S-a creat depozitul " + denumire);
        return depozit;
    }


}
