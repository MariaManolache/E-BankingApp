package ro.ase.ppoo.clase;

import java.io.Serializable;

public class ContCreditor extends Cont {


    public ContCreditor(String idClient, Valuta valuta, double limita) {
        super(idClient, valuta, limita);
        setTip("Credit");
    }

    public ContCreditor(String idClient, String iban, Valuta valuta, double balanta, String tip, double limita) {
        super(idClient, iban, valuta, balanta, tip, limita);
    }

    @Override
    protected boolean depunere(double suma) {
        if(this.getBalanta() - suma >= 0) {
            setBalanta(this.getBalanta() - suma);
            return true;
        } else {
            System.out.println("S-a depus mai mult decat s-a imprumutat! Nu s-a putut realiza transferul.");
            return false;
        }
    }

    @Override
    public boolean retragere(double suma) {
        if(this.getBalanta() + suma < this.getLimita()) {
            setBalanta(this.getBalanta() + suma);
            return true;
        } else {
            System.out.println("Imprumutul selectat depaseste limita cardului de credit");
            return false;
        }
    }

}
