package modeles;

import enumerations.Dish;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Herbert
 */
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    // Une liste à cause des homonymes
    private List<Participant> participants;
    private LocalDate date;
    private String name;

    public Event() {
        this.participants = new ArrayList<>();
    }

    public Event(LocalDate date) {
        this();
        this.date = date;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String newName){
        this.name = newName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Calcule et retourne le nombre de participant à l'événement.
     *
     * @return le nombre de participant à l'événement
     */
    private int countParticipants() {
        int counter = 0;
        for (Participant p : participants) {
            counter += p.getNbPersons();
        }
        return counter;
    }

    /**
     * Calcule et retourne le nombre de parts d'un même type de plat pour
     * l'événement.
     *
     * @param dish le type de plat à évaluer
     * @return le nombre de parts du type de plat
     */
    private int countDish(Dish dish) {
        int counter = 0;
        for (Participant p : participants) {
            counter += p.getDishQuantity(dish);
        }
        return counter;
    }

    /**
     * Retourne la chaîne formattée pour l'affichage en console représentant
     * l'événement.
     *
     * @return événement formaté pour la console
     */
    public String consoleFormat() {
        // Afficher la date
        // Afficher les entêtes de colonnes :
        // Nom  	|  Téléphone	|  Entrée  	|  Plat  	|  Dessert  |  Boisson	|  Nombre de participant	|  Remarque
        // Pour chaque participant, afficher le participant sous la bonne forme
        String eol = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        // Création d'un format de ligne de séparation constituée de 104 fois
        // le caractère '-'
        String separator = new String(new char[104]).replace("\0", "-") + eol;

        // Création du format d'une ligne : 25 caractères pour le nom sous forme
        // de String (%25s) alignés à gauche (%-25s), 10 pour le téléphone,
        // 7 pour chaque type de plat, 21 pour le nb de personnes et autant
        // qu'on veut pour les commentaires.
        // Le %n est un retour à la ligne
        String format = "%-25s | %-10s | %-7s | %-7s | %-7s | %-7s | %-21s | %s %n";

        // Ajout de la date en première ligne
        // Création de la date au bon format de sortie
        String sDate = date.format(DateTimeFormatter.ofPattern("dd LLLL yyyy"));
        sb.append(sDate).append(eol);
        // Ajout d'une ligne de séparation
        sb.append(separator);
        // Ajout des entêtes de colonnes
        sb.append(String.format(format,
                "Nom",
                "Téléphone",
                "Entrée",
                "Plat",
                "Dessert",
                "Boisson",
                "Nombre de participant",
                "Remarque"));
        // Ajout d'une ligne de séparation
        sb.append(separator);
        for (Participant p : participants) {
            sb.append(p.consoleFormat(format));
        }
        // Ajout d'une ligne de séparation
        sb.append(separator);
        // Ajouter la dernière ligne avec les manques
        int nbParticipants = countParticipants();
        sb.append(String.format(format,
                "Manque",
                "N/A",
                Math.max(nbParticipants - countDish(Dish.ENTREE), 0),
                Math.max(nbParticipants - countDish(Dish.PLAT), 0),
                Math.max(nbParticipants - countDish(Dish.DESSERT), 0),
                Math.max(nbParticipants - countDish(Dish.BOISSON), 0),
                "N/A",
                "N/A"));
        // Ajout d'une ligne de séparation
        sb.append(separator);
        return sb.toString();
    }

    /**
     * Ajoute un participant à l'événement.
     *
     * @param p le participant à ajouter
     */
    public void addParticipant(Participant p) {
        this.participants.add(p);
    }

    public void saveToText(String fileName) {
        // ouvrir le fichier en écriture texte
        try ( FileWriter fw = new FileWriter(fileName)) {
            // y insérer la date et un retour à la ligne
            // boucler sur les participants
            // insérer dans le fichier une ligne par participant
            // en utilisant une méthode de Participant qui me retourne le
            // participant formatté pour la sauvegarde
            String eol = System.lineSeparator();
            fw.write(date.toString());
            fw.write(eol);
            for (Participant p : participants) {
                fw.write(p.formatSave());
                fw.write(eol);
            }
        } catch (IOException ex) {
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Event loadFromText(String fileName) throws RuntimeException {
        Event e = new Event();
        try ( FileReader fr = new FileReader(fileName)) {
            java.util.Scanner sc = new java.util.Scanner(fr);
            if (sc.hasNextLine()) {
                String sDate = sc.nextLine();
                e.setDate(LocalDate.parse(sDate));
            } else {
                throw new RuntimeException("Fichier de sauvegarde vide !!!");
            }
            // Boucler sur les lignes du fr
            while (sc.hasNextLine()) {
                // Lire une ligne complète
                String line = sc.nextLine();
                // L'interpréter (participant)
                Participant p = Participant.getFromFormattedString(line);
                e.participants.add(p);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }

    public void saveToBin(String filename) {
        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (IOException ex) {
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Event loadFromBin(String filename) {
        Event ev = null;
        try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            ev = (Event) ois.readObject();
            ev.name = filename;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ev;
    }
    
    @Override
    public String toString(){
        return this.getDate().toString();
    }
    
    public String[][] createTabEvent() {
        String[][] tab = new String[participants.size()][9];
        for (int i = 0; i < participants.size(); i++) {
            tab[i] = participants.get(i).createTabParticipants();
        }
        return tab;
    }
}