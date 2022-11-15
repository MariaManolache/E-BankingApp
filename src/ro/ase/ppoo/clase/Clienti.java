package ro.ase.ppoo.clase;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Clienti implements Serializable {
    private List<Client> listaClienti;
    private Map<String, String> listaUtilizatori;

    private static Clienti clienti = null;
    private static final long serialVersionUID = 1L;


    private Clienti() {
        this.listaClienti = new ArrayList<>();
        this.listaUtilizatori = new HashMap<>();
    }

    public static synchronized Clienti getInstance() {
        if(clienti == null) {
            clienti = new Clienti();
        }
        return clienti;
    }

    public List<Client> getListaClienti() {
        return listaClienti;
    }

    public void setListaClienti(List<Client> listaClienti) {
        this.listaClienti = listaClienti;
    }

    public Map<String, String> getListaUtilizatori() {
        return listaUtilizatori;
    }

    public void setListaUtilizatori(Map<String, String> listaUtilizatori) {
        this.listaUtilizatori = listaUtilizatori;
    }

    public void salveazaLista(String numeFisier) {
        Fisier fisier = new Fisier(numeFisier);
        fisier.deschide();
        fisier.serializare(this);
    }

    public void populeazaLista(String numeFisier) {
        Fisier fisier = new Fisier(numeFisier);
        fisier.deschide();
        clienti = fisier.deserializare(clienti);
        setListaClienti(clienti.listaClienti);
        for(Client c : clienti.listaClienti) {
            listaUtilizatori.put(c.getIdClient(), c.getParola());
        }
    }

    public void citesteDinTxt(String numeFisier) {
        try {
            Scanner s = new Scanner(new File(numeFisier));
            s.nextLine();
            while (s.hasNext()) {
                String[] split = s.nextLine().split("   ");
                listaClienti.add(new Client(split[0], split[1], split[2], new SimpleDateFormat("dd.MM.yyyy").parse(split[3]), split[4], split[5], split[6]));
                listaUtilizatori.put(split[5], split[6]);
            }
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Clienti{ ");
        for(Client client : this.listaClienti) {
            sb.append(client);
            sb.append('\n');
        }
        return sb.toString();
    }
}
