package modeles;

import enumerations.Dish;
import java.io.Serializable;
import static java.lang.String.valueOf;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Herbert
 */
public class Participant extends Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String phone;
    private int nbPersons;
    private String comment;
    private final Map<Dish, Integer> dishes;

    public Participant() {
        dishes = new HashMap<>();
        phone = "          ";
        comment = "N/A";
    }

    public Participant(String name, String firstName) {
        super(name, firstName);
        dishes = new HashMap<>();
        phone = "          ";
        comment = "N/A";
    }

    /**
     * Associe un nombre de part à un type de plat amené par le particpant.
     *
     * @param dish le type de plat
     * @param qty lenombre de parts
     */
    public void addDish(Dish dish, int qty) {
        this.dishes.put(dish, qty);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getNbPersons() {
        return nbPersons;
    }

    public void setNbPersons(int nbPersons) {
        this.nbPersons = nbPersons;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Retourne le nombre de parts d'un type de plat apporté par le participant.
     *
     * @param dish le type de plat
     * @return le nombre de parts
     */
    public int getDishQuantity(Dish dish) {
        return this.dishes.getOrDefault(dish, 0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Participant{phone=").append(phone);
        sb.append(", nbPersons=").append(nbPersons);
        sb.append(", remark=").append(comment);
        sb.append(", dishes=").append(dishes);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Retourne la chaîne formattée pour l'affichage en console représentant le
     * participant.
     *
     * @param format le format d'affichage
     * @return la chaine formattée
     */
    public String consoleFormat(String format) {
        return String.format(format,
                this.getName() + " " + this.getFirstName(),
                this.phone,
                this.dishes.getOrDefault(Dish.ENTREE, 0),
                this.dishes.getOrDefault(Dish.PLAT, 0),
                this.dishes.getOrDefault(Dish.DESSERT, 0),
                this.dishes.getOrDefault(Dish.BOISSON, 0),
                this.nbPersons,
                this.comment);
    }

    public String formatSave() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName()).append("|")
                .append(this.getFirstName()).append("|")
                .append(this.phone).append("|")
                .append(this.dishes.getOrDefault(Dish.ENTREE, 0)).append("|")
                .append(this.dishes.getOrDefault(Dish.PLAT, 0)).append("|")
                .append(this.dishes.getOrDefault(Dish.DESSERT, 0)).append("|")
                .append(this.dishes.getOrDefault(Dish.BOISSON, 0)).append("|")
                .append(this.nbPersons).append("|")
                .append(this.comment);
        return sb.toString();
    }

    /**
     * Crée une instance de Participant à partir d'une String avec un format
     * spécifique. Format : nom|prénom[téléphone|nb Entrées|nb plats|nb
     * desserts|nb boissons|nb participants|commentaire.
     *
     * @param line une String formattée
     * @return un participant
     */
    public static Participant getFromFormattedString(String line) throws RuntimeException {
        if (line.equals("") || !line.contains("|")) {
            throw new RuntimeException("Format de participant incorrect.");
        }
        Participant p = new Participant();
        String[] data = line.split("\\|");
        p.name = data[0];
        p.firstName = data[1];
        p.phone = data[2].trim();
        p.dishes.put(Dish.ENTREE, Integer.parseInt(data[3]));
        p.dishes.put(Dish.PLAT, Integer.parseInt(data[4]));
        p.dishes.put(Dish.DESSERT, Integer.parseInt(data[5]));
        p.dishes.put(Dish.BOISSON, Integer.parseInt(data[6]));
        p.nbPersons = Integer.parseInt(data[7]);
        p.comment = data[8];
        return p;
    }
    
    public String[] createTabParticipants() {
        String[] tab = new String[9];
        tab[0] = getName();
        tab[1] = getFirstName();
        tab[2] = phone;
        tab[3] = valueOf(getDishQuantity(Dish.ENTREE));
        tab[4] = valueOf(getDishQuantity(Dish.PLAT));
        tab[5] = valueOf(getDishQuantity(Dish.DESSERT));
        tab[6] = valueOf(getDishQuantity(Dish.BOISSON));
        tab[7] = valueOf(nbPersons);
        tab[8] = comment;
        return tab;
    }

}
