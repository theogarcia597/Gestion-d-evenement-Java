package miamle;

import modeles.App;

/**
 *
 * @author Herbert
 */
public class Miamle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //HashMap<LocalDate, Event> mesEvents = new HashMap<>();
        
        // Créer un événement
        //Event anniv = new Event();
        //LocalDate d = LocalDate.of(2006, 4, 11);
        //anniv.setDate(d);

        // Créer 2 participants : herbert et alf
        //Participant jj = new Participant("Goldman", "JJ");
        //Participant johnny = new Participant("Haliday", "Johnny");

        // Ajouter les plats amenés par ces deux personnes à leur liste de plats
        //jj.addDish(Dish.ENTREE, 3);
        //jj.addDish(Dish.PLAT, 1);
        //jj.addDish(Dish.DESSERT, 4);
        //jj.addDish(Dish.BOISSON, 6);
        //jj.setNbPersons(2);

        //johnny.addDish(Dish.ENTREE, 0);
        //johnny.addDish(Dish.PLAT, 0);
        //johnny.addDish(Dish.DESSERT, 0);
        //johnny.setNbPersons(3);
        //johnny.setComment("Gourmands");

        // Ajouter ces deux personnes à l'événement
        //anniv.addParticipant(jj);
        //anniv.addParticipant(johnny);
        //anniv.addParticipant(new Participant());

        // Afficher l'événement
        //System.out.println(anniv.consoleFormat());

        // Sauvegarder l'événement en texte
        //anniv.saveToText("anniv.txt");

        // Récupérer l'événement en texte dans un autre objet
        //Event barbecue = Event.loadFromText("anniv.txt");
        // On l'affiche pour voir si c'est la même chose que anniv
        //System.out.println("L'événement chargé depuis le fichier texte : ");
        //System.out.println(e.consoleFormat());

        // Sauvegarder l'événement en binaire
        //anniv.saveToBin("anniv.mle");
        //barbecue.saveToBin("barbecue.mle");

        // Restaurer l'anniversaire dans un autre objet
        //Event newEvent = Event.loadFromBin("anniv.bin");
        // On l'affiche pour voir si c'est la même chose que anniv
        //System.out.println("L'événement chargé depuis le fichier binaire : ");
        //System.out.println(newEvent.consoleFormat());
        
        //mesEvents.put(barbecue.getDate(),barbecue);
        //mesEvents.put(anniv.getDate(), anniv);
        
        App test = new App("Miamle");
        //test.afficherEvents();
    }

}
