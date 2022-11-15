package ro.ase.ppoo.clase;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Fisier {
    File fisier;
    String numeFisier;

    public Fisier(String numeFisier) {
        this.numeFisier = numeFisier;
    }

    public File getFisier() {
        return fisier;
    }

    public boolean deschide() {
        fisier = new File(numeFisier);
        if (fisier.exists())
            return true;
        else
            return false;
    }

    public String caleFisier() throws IOException {
        fisier = new File(numeFisier);
        if (fisier.exists())
            return fisier.getCanonicalPath();
        else
            return null;
    }

    public String citireLinieFisier() {
        String result = null;
        try {
            FileInputStream fluxIn = new FileInputStream(fisier);
            InputStreamReader isr = new InputStreamReader(fluxIn);
            BufferedReader bufferIn = new BufferedReader(isr);
            StringBuffer sbuf = new StringBuffer();
            while ((bufferIn.readLine()) != null)
                sbuf.append(bufferIn.readLine());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public boolean copiereFisier() {
        if (fisier != null) {
            try {
                FileInputStream fluxIn = new FileInputStream(fisier);
                File outFile = new File("CopyOf" + numeFisier);
                FileOutputStream fluxOut = new FileOutputStream(outFile);

                byte b;
                while ((b = (byte) fluxIn.read()) != -1) {
                    fluxOut.write(b);
                }
                fluxIn.close();
                fluxOut.close();
                return true;
            } catch (IOException ex) {
                ex.printStackTrace();
                return false;
            }
        } else return false;
    }

    public String descriere() {
        if (fisier.canRead()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Nume fisier = " + fisier.getName() + "\n");
            sb.append("Cale completa = " + fisier.getAbsolutePath() + "\n");
            Date df = new Date(fisier.lastModified());
            sb.append("Modificat la data = " + df + "\n");
            sb.append("Dimensiune = " + fisier.length() + "\n");

            return sb.toString();
        } else return null;
    }

    public void serializare(Clienti listaClienti) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(this.numeFisier);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(listaClienti);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Clienti deserializare(Clienti clienti) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(this.numeFisier);
            ois = new ObjectInputStream(fis);
            clienti = (Clienti) ois.readObject();

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return clienti;
    }

    public void serializareTranzactii(Map<String, List<Transfer>> transferuri) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(this.numeFisier);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(transferuri);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, List<Transfer>> deserializareTranzactii(Map<String, List<Transfer>> transferuri) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(this.numeFisier);
            ois = new ObjectInputStream(fis);
            transferuri = (Map<String, List<Transfer>>) ois.readObject();

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return transferuri;
    }

    public void salveazaDateInFisier(Cont[] conturi) {
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        if(conturi != null && conturi.length > 0) {
            try {
                this.deschide();
                fileWriter = new FileWriter(this.numeFisier);
                printWriter = new PrintWriter(fileWriter);
                for (int i = 0; i < conturi.length; i++) {
                    printWriter.println(conturi[i].toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                        printWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void salveazaColectiaDeConturiInFisier(Set<Cont> conturi) {
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        if(conturi != null && conturi.size() > 0) {
            try {
                this.deschide();
                fileWriter = new FileWriter(this.numeFisier);
                printWriter = new PrintWriter(fileWriter);
                printWriter.println("Id Client        IBAN         Valuta  Balanta   Tip  Limita" );
                for (Cont c : conturi) {
                    if(c.getTip().equals("Credit")) {
                        printWriter.println(c.getIdClient() + "   " + c.getIban() + "   " + c.getValuta() + "   " +
                                c.getBalanta() + "   " + c.getTip() + "   " + c.getLimita());
                    } else if(c.getTip().equals("Debit")) {
                        printWriter.println(c.getIdClient() + "   " + c.getIban() + "   " + c.getValuta() + "   " +
                                c.getBalanta() + "   " + c.getTip());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                        printWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void salveazaColectiaDeTranzactiiInFisierText(List<Transfer> tranzactii) {
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        if(tranzactii != null && tranzactii.size() > 0) {
            try {
                this.deschide();
                fileWriter = new FileWriter(this.numeFisier);
                printWriter = new PrintWriter(fileWriter);
                printWriter.println("\t\t\t\tIBAN Destinatar      \t\t\t\t\t\t\t\t\tIBAN Destinatie       \t\t\t\t\t\t\t\t\t   Suma" );
                for (Transfer t : tranzactii) {
                    printWriter.println(t.getContDestinatar() + "   " + t.getContDestinatie() + "   " + t.getSuma());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                        printWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void salveazaColectiaDeDepoziteInFisier(LinkedList<Depozit> depozite) {
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        if(depozite != null && depozite.size() > 0) {
            try {
                this.deschide();
                fileWriter = new FileWriter(this.numeFisier);
                printWriter = new PrintWriter(fileWriter);
                printWriter.println("      IBAN           Id client      Denumire       Perioada      IBAN depozit     Dobanda  Suma");
                for (Depozit d : depozite) {
                    printWriter.println(d.getIban() + "   " + d.getClient().getIdClient() + "   " +
                            d.getDenumire() + "   " + d.getPerioada() + "   " + d.getIbanContDebit() +
                            "   " + d.getDobanda() + "   " + d.getSuma());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                        printWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static Set<Cont> citesteDateDinTxt() {
        Set<Cont> colectieConturi = new HashSet<>();
        try {
            File fisierConturi = new File("listaConturi.txt");
            Scanner reader = new Scanner(fisierConturi);
            reader.nextLine();
            while (reader.hasNext()) {
                String[] split = reader.nextLine().split("   ");
                if(split[4].equals("Debit")) {
                    colectieConturi.add(new ContDebitor(split[0], split[1], Valuta.valueOf(split[2]), Double.parseDouble(split[3]), split[4]));
                } else if(split[4].equals("Credit")){
                    colectieConturi.add(new ContCreditor(split[0], split[1], Valuta.valueOf(split[2]), Double.parseDouble(split[3]), split[4], Double.parseDouble(split[5])));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return colectieConturi;
    }

    public static LinkedList<Depozit> citesteDepoziteDinTxt(List<Client> listaClienti) {
        LinkedList<Depozit> colectieDepozite = new LinkedList<>();
        try {
            File fisierDepozite = new File("listaDepozite.txt");
            Scanner reader = new Scanner(fisierDepozite);
            if(reader.hasNextLine()) {
                reader.nextLine();
                while (reader.hasNext()) {
                    String[] split = reader.nextLine().split("   ");
                    Client client = null;
                    for(Client c : listaClienti) {
                        if(c.getIdClient().equals(split[1])) {
                            client = c;
                        }
                    }
                    colectieDepozite.add(new Depozit(split[0], client, split[2], Perioada.valueOf(split[3]), split[4], Double.parseDouble(split[6])));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return colectieDepozite;
    }

}
