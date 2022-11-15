package ro.ase.ppoo.main;

import ro.ase.ppoo.clase.*;
import ro.ase.ppoo.exceptii.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean terminare = false;
        boolean autentificareValidata = false;
        int optiune = 0;

        Clienti listaClienti = Clienti.getInstance();
        listaClienti.citesteDinTxt("listaClienti.txt");
        //listaClienti.populeazaLista("listaClienti.txt");
        //System.out.println(listaClienti.getListaClienti());

        try {
            File fisierConturi = new File("listaConturi.txt");
            Scanner reader = new Scanner(fisierConturi);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Set<Cont> colectieConturi = new HashSet<>();
        colectieConturi = Fisier.citesteDateDinTxt();

        for (Client client : listaClienti.getListaClienti()) {
            for (Cont cont : colectieConturi)
                if (client.getIdClient().equals(cont.getIdClient())) {
                    client.adaugaCont(cont);
                }
        }

        LinkedList<Depozit> colectieDepozite = new LinkedList<>();
        colectieDepozite = Fisier.citesteDepoziteDinTxt(listaClienti.getListaClienti());
        Fisier fisierTransferuriCitite = new Fisier("transferuri.dat");
        Map<String, List<Transfer>> transferuri = new HashMap<>();
        for (Client c : listaClienti.getListaClienti()) {
            transferuri.put(c.getIdClient(), new ArrayList<>());
        }
        Map<String, List<Transfer>> transferuriPersonale = new HashMap<>();
        transferuri = fisierTransferuriCitite.deserializareTranzactii(transferuri);
        //System.out.println(transferuri);

        while (terminare == false) {
            afiseazaMesajInitial();
            ServiciuAutentificare serviciuAutentificare = new ProxyAutentificare(new Autentificare());
            while (!autentificareValidata) {
                System.out.print("Id= ");
                String idClient = scanner.nextLine();
                if (idClient.equals("x")) {
                    terminare = true;
                    break;
                }
                System.out.print("parola= ");
                String parola = scanner.nextLine();
                if (parola.equals("x")) {
                    terminare = true;
                    break;
                }
                try {
                    autentificareValidata = serviciuAutentificare.login(idClient, parola, listaClienti.getListaUtilizatori());
                } catch (ExceptieAutentificareEsuata e) {
                    System.out.println("Ati introdus de prea multe ori parola gresita!");
                    break;
                }

                System.out.println(autentificareValidata ? "V-ati autentificat cu succes!" : "Utilizatorul sau parola introdusa nu sunt corecte.");
                while (autentificareValidata == true) {

                    Cont[] listaConturiPersonale = new Cont[0];
                    List<Cont> listaConturiPersonaleArray = new ArrayList<>(Arrays.asList(listaConturiPersonale));
                    for (Cont c : colectieConturi) {
                        if (c.getIdClient().equals(idClient)) {
                            listaConturiPersonaleArray.add(c);
                        }
                    }
                    listaConturiPersonale = listaConturiPersonaleArray.toArray(listaConturiPersonale);

                    List<Transfer> listaTransferuriPersonale = new ArrayList<>();
                    for (Map.Entry<String, List<Transfer>> listaTransferuri : transferuri.entrySet()) {
                        for (Transfer transfer : listaTransferuri.getValue()) {
                            if (transfer.getContDestinatar().getIdClient().equals(idClient) ||
                                    transfer.getContDestinatie().getIdClient().equals(idClient)) {
                                listaTransferuriPersonale.add(transfer);
                            }
                        }
                    }

                    transferuriPersonale.put(idClient, listaTransferuriPersonale);

                    System.out.println('\n');
                    afiseazaOptiuni();
                    try {
                        optiune = scanner.nextInt();
                    } catch (Exception e) {
                        scanner.nextLine();
                    }
                    if (optiune == 1) {
                        vizualizareConturi(idClient, colectieConturi);
                    } else if (optiune == 2) {
                        Cont c = deschideCont(idClient);
                        if(c != null) {
                            for (int i = 0; i < listaClienti.getListaClienti().size(); i++) {
                                if (listaClienti.getListaClienti().get(i).getIdClient().equals(idClient)) {
                                    listaClienti.getListaClienti().get(i).adaugaCont(c);
                                    listaConturiPersonaleArray.add(c);
                                    colectieConturi.add(c);
                                }
                            }
                        }
                        listaConturiPersonale = listaConturiPersonaleArray.toArray(listaConturiPersonale);

                    } else if (optiune == 3) {
                        Depozit depozit = deschideDepozit(idClient, listaConturiPersonale, listaClienti.getListaClienti());
                        if (depozit != null) {
                            colectieDepozite.add(depozit);
                            for (Cont c : colectieConturi) {
                                if (c.getIban().equals(depozit.getIbanContDebit())) {
                                    c.setBalanta(c.getBalanta() - depozit.getSuma());
                                }
                            }
                            for (int i = 0; i < listaConturiPersonale.length; i++) {
                                if (listaConturiPersonale[i].getIban().equals(depozit.getIban())) {
                                    listaConturiPersonale[i].setBalanta(listaConturiPersonale[i].getBalanta() - depozit.getSuma());
                                }
                            }
                        }

                    } else if (optiune == 4) {
                        Transfer transfer = realizeazaTransfer(idClient, listaConturiPersonale, colectieConturi, listaClienti.getListaClienti(), transferuri, transferuriPersonale);

                    } else if (optiune == 5) {
                        if (listaConturiPersonale != null && listaConturiPersonale.length > 0) {
                            Fisier fisier = new Fisier("conturi " + idClient + ".txt");
                            fisier.salveazaDateInFisier(listaConturiPersonale);
                            try {
                                System.out.println("Raportul s-a descarcat! Il puteti gasi la calea " + fisier.caleFisier());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("Nu aveti conturi deschise la aceasta banca. Nu s-a putut genera un raport!");
                        }

                    } else if (optiune == 6) {
                        Fisier fisierTranzactiiTxt = new Fisier("tranzactii " + idClient + ".txt");
                        if (transferuri.get(idClient) != null && !transferuri.get(idClient).isEmpty()) {
                            try {
                                fisierTranzactiiTxt.salveazaColectiaDeTranzactiiInFisierText(transferuri.get(idClient));
                                System.out.println("Raportul s-a descarcat! Il puteti gasi la calea " + fisierTranzactiiTxt.caleFisier());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("Nu aveti tranzactii efectuate pe niciunul dintre conturi.");
                        }
                    } else if(optiune == 7) {
                        vizualizareDepozite(idClient, colectieDepozite);
                    }else if(optiune == 8) {
                        LinkedList<Depozit> depozitePersonale = new LinkedList<>();
                        for(Depozit depozit : colectieDepozite) {
                            if(depozit.getClient().getIdClient().equals(idClient)) {
                                depozitePersonale.add(depozit);
                            }
                        }
                        if (depozitePersonale != null && !(depozitePersonale.isEmpty())) {
                            Fisier fisier = new Fisier("depozite " + idClient + ".txt");
                            fisier.salveazaColectiaDeDepoziteInFisier(depozitePersonale);
                            try {
                                System.out.println("Raportul s-a descarcat! Il puteti gasi la calea " + fisier.caleFisier());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("Nu aveti depozite deschise la aceasta banca. Nu s-a putut genera un raport!");
                        }
                    }else if (optiune == 9) {
                        Fisier fisier = new Fisier("listaConturi.txt");
                        fisier.salveazaColectiaDeConturiInFisier(colectieConturi);
                        Fisier fisierDepozite = new Fisier("listaDepozite.txt");
                        fisierDepozite.salveazaColectiaDeDepoziteInFisier(colectieDepozite);
                        Fisier fisierTransferuri = new Fisier("transferuri.dat");
                        fisierTransferuri.serializareTranzactii(transferuri);
                        terminare = true;
                        break;
                    } else {
                        System.out.println("Optiune invalida");
                    }
                }
            }
        }
    }

    private static void vizualizareDepozite(String idClient, LinkedList<Depozit> colectieDepozite) {
        for(Depozit d : colectieDepozite) {
            if(d.getClient().getIdClient().equals(idClient)) {
                System.out.println(d.toString());
            }
        }
    }

    private static Transfer realizeazaTransfer(String idClient, Cont[] listaConturiPersonale, Set<Cont> colectieConturi, List<Client> listaClienti, Map<String, List<Transfer>> transferuri, Map<String, List<Transfer>> transferuriPersonale) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Din ce cont doriti sa realizati transferul?");
        String line = scanner.nextLine();
        boolean contDestinatarValid = false;
        boolean contDestinatieValid = false;
        for (int i = 0; i < listaConturiPersonale.length; i++) {
            if (listaConturiPersonale[i].getIban().equals(line)) {
                contDestinatarValid = true;
                System.out.println("Catre ce cont doriti sa realizati transferul?");
                String ibanContDestinatie = scanner.nextLine();
                for (Cont c : colectieConturi) {
                    if (c.getIban().equals(ibanContDestinatie)) {
                        contDestinatieValid = true;
                        if (listaConturiPersonale[i].getValuta().equals(c.getValuta())) {
                            System.out.println("Ce suma doriti sa transferati?");
                            try {
                                double suma = scanner.nextDouble();
                                Transfer transfer = new Transfer(listaConturiPersonale[i], c, suma);
                                transferuri.get(idClient).add(transfer);
                                transferuriPersonale.get(idClient).add(transfer);
                                transferuri.get(c.getIdClient()).add(transfer);
                                listaConturiPersonale[i].transfer(suma, c);
                                return transfer;
                            } catch (Exception e) {
                                System.out.println("Suma introdusa nu este valida.");
                            }
                        } else {
                            System.out.println("Conturile sunt in valute diferite. Transferul nu se poate realiza!");
                        }
                    }
                }
            }
        }
        if (contDestinatarValid = false) {
            System.out.println("Contul din care doriti sa realizati transferul nu este valid");
        }
        if (contDestinatieValid = false) {
            System.out.println("Contul catre care doriti sa realizati transferul nu este valid");
        }
        return null;
    }

    private static Depozit deschideDepozit(String idClient, Cont[] listaConturiPersonale, List<Client> listaClienti) {
        Client client = null;
        for (Client c : listaClienti) {
            if (idClient.equals(c.getIdClient())) {
                client = c;
            }
        }

        Depozit depozit = null;
        String iban = null;
        boolean valid = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pe baza carui cont de debit doriti sa deschideti un depozit? Introduceti ibanul: ");
        String line = scanner.nextLine();
        for (int i = 0; i < listaConturiPersonale.length; i++) {
            System.out.println(" ");
            if (line.equals(listaConturiPersonale[i].getIban()) && listaConturiPersonale[i].getTip().equals("Debit")) {
                valid = true;
                System.out.println("Ibanul introdus este valid! Ce denumire doriti sa aiba depozitul?");
                String line2 = scanner.nextLine();
                String denumire = line2;
                System.out.println("Pe ce perioada doriti sa deschideti depozitul? Alegeti dintre: Sase luni, Noua luni sau Un an?");
                String line3 = scanner.nextLine();
                Perioada perioada = null;
                double luni = 0.0;
                switch (line3) {
                    case "Sase luni":
                        perioada = Perioada.SASE_LUNI;
                        luni = 0.5;
                        break;
                    case "Noua luni":
                        perioada = Perioada.NOUA_LUNI;
                        luni = 0.75;
                        break;
                    case "Un an":
                        perioada = Perioada.UN_AN;
                        luni = 1;
                        break;
                    default:
                        System.out.println("Perioada introdusa nu este valida");
                        break;

                }
                System.out.println("Ce suma doriti sa depuneti?");
                double suma = scanner.nextDouble();
                if (listaConturiPersonale[i].getBalanta() >= suma) {
                    if (perioada != null) {

                        depozit = ((ContDebitor) (listaConturiPersonale[i])).creeazaDepozit(client, denumire, perioada, line, suma);
                        System.out.println("S-a creat depozitul " + depozit);

                        System.out.println("La finalul perioadei veti avea un castig de " + (suma * depozit.getDobanda() * luni));
                    }
                } else {
                    System.out.println("Suma introdusa este mai mare decat balanta contului! Nu se poate crea depozitul!");
                    try {
                        throw new ExceptieFonduriInsuficiente("Fondurile nu sunt suficiente pentru a realiza aceasta operatie");
                    } catch (ExceptieFonduriInsuficiente e) {
                        System.out.println("Fonduri insuficiente");
                    }
                }
            }
        }
        if (valid == true) {
            return depozit;
        } else {
            System.out.println("Ibanul introdus nu este valid! Nu se poate crea un depozit pe baza acestuia!");
            return null;
        }
    }

    private static Cont deschideCont(String idClient) {
        Scanner scanner = new Scanner(System.in);
        String tipCont = null;
        double limita = 0.0;
        Valuta valuta = null;
        System.out.println("Ce tip de cont doriti sa deschideti? Credit sau Debit?");
        String line = scanner.nextLine();
        if (line.equals("Credit")) {
            tipCont = "Credit";
        } else if (line.equals("Debit")) {
            tipCont = "Debit";
        } else {
            try {
                throw new ExceptieTipContInexistent("Acest tip de cont nu exista.");
            } catch (ExceptieTipContInexistent e) {
                System.out.println("Acest tip de cont nu exista");
                return null;
            }
        }
        System.out.println("In ce valuta doriti sa deschideti contul? RON, EUR sau USD?");
        String line2 = scanner.nextLine();
        if (line2.equals("RON")) {
            valuta = Valuta.RON;
        } else if (line2.equals("EUR")) {
            valuta = Valuta.EUR;
        } else if (line2.equals("USD")) {
            valuta = Valuta.USD;
        } else {
            try {
                throw new ExceptieTipContInexistent("Acest tip de cont nu exista");
            } catch (ExceptieTipContInexistent e) {
                System.out.println("Acest tip de cont nu exista");
            }
        }

        String line3 = "";
        if (line.equals("Credit")) {
            System.out.println("Ce limita doriti la cardul de credit? Introduceti suma: (ex: 5000)");
            line3 = scanner.nextLine();
            limita = Double.parseDouble(line3);
        }

        if (tipCont != null && valuta != null) {
            ContBancar cont = new ContBancar();
            Cont c = cont.deschideContBancar(idClient, tipCont, valuta, limita);
            if (c != null) {
                System.out.println("Ati deschis contul " + c.getIban() + " cu id-ul de client " + c.getIdClient() +
                        " in valuta " + c.getValuta());
            }
            return c;
        }
        return null;
    }

    private static void vizualizareConturi(String idClient, Set<Cont> listaConturi) {
        for (Cont c : listaConturi) {
            if (c.getIdClient().equals(idClient)) {
                System.out.println(c);
            }
        }
    }

    public static void afiseazaMesajInitial() {
        System.out.println("Bine ati venit!" + '\n' + '\n' +
                "Introduceti id-ul de client, impreuna cu parola pentru a va conecta" + '\n' + '\n' +
                "Daca doriti sa va deconectati, introduceti x de la tastatura.");
    }

    public static void afiseazaOptiuni() {
        System.out.println("Alege una din următoarele opțiuni: " + '\n' +
                "1 - Vizualizeaza conturile tale" + '\n' +
                "2 - Deschide un cont nou" + '\n' +
                "3 - Deschide un depozit" + '\n' +
                "4 - Realizeaza un transfer" + '\n' +
                "5 - Descarca raport pentru toate conturile detinute" + '\n' +
                "6 - Descarca raport pentru tranzactiile efectuate" + '\n' +
                "7 - Vizualizeaza depozitele tale" + '\n' +
                "8 - Descarca raport pentru depozitele tale" + '\n' +
                "9 - Iesi din aplicatie");
    }
}
