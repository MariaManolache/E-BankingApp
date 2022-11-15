package ro.ase.ppoo.clase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaConturi {

    Map<String, List<Cont>> listaConturi = new HashMap<>();

    private void afisareConturi(String cnp) {
        for(Map.Entry<String, List<Cont>> entry : listaConturi.entrySet()) {
            String key = entry.getKey();
            List<Cont> value = entry.getValue();
            if(cnp.equals(key)) {
                System.out.println("Conturile dumneavoastra sunt urmatoarele :" + '\n');
                for(Cont c : value) {
                    System.out.println(c);
                }
            }

        }
    }
}
