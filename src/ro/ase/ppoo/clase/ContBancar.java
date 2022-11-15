package ro.ase.ppoo.clase;

public final class ContBancar {

    public Cont deschideContBancar(String idClient, String tip, Valuta valuta, double limita) {
        Cont cont;
        if (tip.equals("Debit")) cont = new ContDebitor(idClient, valuta);
        else if (tip.equals("Credit")) cont = new ContCreditor(idClient, valuta, limita);
        else return null;
        return cont;
    }
}
