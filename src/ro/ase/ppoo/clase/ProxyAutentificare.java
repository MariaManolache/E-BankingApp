package ro.ase.ppoo.clase;

import ro.ase.ppoo.exceptii.ExceptieAutentificareEsuata;

import java.util.Map;

public class ProxyAutentificare implements ServiciuAutentificare {
    private Autentificare autentificare;
    private int nr = 0;

    public ProxyAutentificare(Autentificare autentificare) {
        this.autentificare = autentificare;
    }

    @Override
    public boolean login(String idClient, String parola, Map<String, String> listaUtilizatori) {
        nr++;

        if(nr > 4) {
            throw new ExceptieAutentificareEsuata();
        }

        boolean rezultat = autentificare.login(idClient, parola, listaUtilizatori);

        if(rezultat) {
            nr = 0;
            return true;
        }
        return false;
    }
}
