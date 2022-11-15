package ro.ase.ppoo.clase;

import java.util.Map;

public interface ServiciuAutentificare {
    boolean login(String idClient, String parola, Map<String, String> listaUtilizatori);
}
