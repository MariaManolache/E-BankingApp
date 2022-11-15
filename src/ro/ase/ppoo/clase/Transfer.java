package ro.ase.ppoo.clase;

import java.io.Serializable;

public class Transfer implements Serializable {

    private Cont contDestinatar;
    private Cont contDestinatie;
    private double suma;

    public Transfer() {

    }

    public Transfer(Cont contDestinatar, Cont contDestinatie, double suma) {
        this.contDestinatar = contDestinatar;
        this.contDestinatie = contDestinatie;
        this.suma = suma;
    }

    public Cont getContDestinatar() {
        return contDestinatar;
    }

    public void setContDestinatar(Cont contDestinatar) {
        this.contDestinatar = contDestinatar;
    }

    public Cont getContDestinatie() {
        return contDestinatie;
    }

    public void setContDestinatie(Cont contDestinatie) {
        this.contDestinatie = contDestinatie;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    @Override
    public String toString() {
        return "Din contul " + contDestinatar.getIban() +
                " detinut de clientul cu id-ul " + contDestinatar.getIdClient() +
                " a fost transferata contului " + contDestinatie.getIban() +
                " detinut de clientul cu id-ul " + contDestinatie.getIdClient() +
                " suma de " + suma +
                '}';
    }
}
