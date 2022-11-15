package ro.ase.ppoo.clase;

import java.util.Map;

public class Autentificare implements ServiciuAutentificare {

    @Override
    public boolean login(String idClient, String parola, Map<String, String> listaUtilizatori) {
        boolean exista = false;
        for(Map.Entry<String, String> utilizator : listaUtilizatori.entrySet()) {
            if(idClient.equals(utilizator.getKey()) && parola.equals(utilizator.getValue())) {
                exista = true;
            }
        }
        return exista;
    }
}
