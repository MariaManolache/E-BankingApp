package ro.ase.ppoo.clase;

public interface IBeneficii {
    Depozit creeazaDepozit(Client client, String denumire, Perioada perioada, String ibanContDebitor, double suma);
}
