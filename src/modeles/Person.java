package modeles;

import java.io.Serializable;

/**
 *
 * @author Herbert
 */
public abstract class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String name;
    protected String firstName;

    public Person() {
    }

    public Person(String name, String firstName) {
        this.name = name;
        this.firstName = firstName;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public String getFirstName() {
        return firstName == null ? "" : firstName;
    }

}
