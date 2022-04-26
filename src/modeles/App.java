package modeles;

import enumerations.Dish;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.time.LocalDate;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 *
 * @author nathan
 */
public final class App extends JFrame {

    private JComboBox<Event> selector;
    private Vector<Event> events;
    private JPanel currentEventPanel, buttonPanel;
    private JButton addParticipant, createEvent;
    private int indexSelectedEvent;

    private JTable table;

    //private JTextArea currentEventContent;
    public App() {
        this("Miamle");
    }

    public App(String title) {
        super(title);
        this.indexSelectedEvent = 0;
        this.events = new Vector<>();
        this.loadEvents();

        //System.out.println(tableau.table);
        table = tabInit(events.get(indexSelectedEvent));
        this.buttonPanel = new JPanel(new BorderLayout());
        this.currentEventPanel = new JPanel(new BorderLayout());
        currentEventPanel.add(table, BorderLayout.CENTER);
        currentEventPanel.add(table.getTableHeader(), BorderLayout.NORTH);
        this.selector = new JComboBox<>(events);
        this.addParticipant = new JButton("Ajouter");
        this.createEvent = new JButton("Créer un event");
        buttonPanel.add(this.addParticipant, BorderLayout.EAST);
        buttonPanel.add(this.createEvent, BorderLayout.WEST);
        this.init();
    }

    private void init() {
        this.setLayout(new BorderLayout());

        this.add(this.selector, BorderLayout.NORTH);
        this.add(this.currentEventPanel, BorderLayout.CENTER);

//        this.add(this.addParticipant, BorderLayout.SOUTH);
//        this.add(this.createEvent, BorderLayout.SOUTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.addParticipant.addActionListener((ActionEvent e) -> {
            creationParticipant();
        });
        
        this.createEvent.addActionListener((ActionEvent e) -> {
            createEvent();
        });

        this.selector.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                //System.out.println(selector.getItemAt(selector.getSelectedIndex()).consoleFormat());
                indexSelectedEvent = selector.getSelectedIndex();
                updateTab();
            }
        });
        //this.pack();
        this.setSize(900, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    public void loadEvents() {
        File folder = new File(System.getProperty("user.dir"));
        File[] listOfFiles = folder.listFiles();

        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                if (listOfFile.getName().endsWith("mle")) {
                    this.events.add(Event.loadFromBin(listOfFile.getName()));
                }
            }
        }
    }

    public void afficherEvents() {
        for (Event e : events) {
            System.out.println(e.consoleFormat());
        }
    }

    public void creationParticipant() {
        Participant tmp = new Participant(JOptionPane.showInputDialog("Nom :"), JOptionPane.showInputDialog("Prénom :"));
        tmp.setPhone(JOptionPane.showInputDialog("Téléphone :"));
        tmp.addDish(Dish.ENTREE, Integer.parseInt(JOptionPane.showInputDialog("Nombre d'entrées :")));
        tmp.addDish(Dish.PLAT, Integer.parseInt(JOptionPane.showInputDialog("Nombre de plats :")));
        tmp.addDish(Dish.DESSERT, Integer.parseInt(JOptionPane.showInputDialog("Nombre de desserts :")));
        tmp.addDish(Dish.BOISSON, Integer.parseInt(JOptionPane.showInputDialog("Nombre de boissons :")));
        tmp.setComment(JOptionPane.showInputDialog("Remarques :"));
        tmp.setNbPersons(Integer.parseInt(JOptionPane.showInputDialog("Nombre de personnes :")));
        this.events.get(indexSelectedEvent).addParticipant(tmp);
        
        this.events.get(indexSelectedEvent).saveToBin(this.events.get(indexSelectedEvent).getName());
        updateTab();
    }

    private JTable tabInit(Event event) {

        String[][] participants = event.createTabEvent();

        return new JTable(participants, getColumnNames());

        //tableau.getViewport().add(table);
    }

    public String[] getColumnNames() {
        String[] c = {"Nom",
            "Prénom",
            "Téléphone",
            "Entrée",
            "Plat",
            "Dessert",
            "Boisson",
            "Nb participants",
            "Remarques"};
        return c;
    }

    public void updateTab() {
        this.table = tabInit(events.get(indexSelectedEvent));
        this.currentEventPanel.removeAll();
        currentEventPanel.add(table, BorderLayout.CENTER);
        currentEventPanel.add(table.getTableHeader(), BorderLayout.NORTH);
        this.currentEventPanel.updateUI();
    }
    
    public void createEvent() {
        System.out.println("ok");       
        Event e = new Event();
        e.setDate(LocalDate.parse(JOptionPane.showInputDialog("Date en format AAAA-MM-JJ :")));
        e.setName(JOptionPane.showInputDialog("Quel est le nom de votre event ?")+".mle");
        events.add(e); 
        e.saveToBin(e.getName());  
    }
    
}
