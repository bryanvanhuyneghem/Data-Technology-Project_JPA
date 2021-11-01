package DBClasses;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 *
 * @author Michiel & Bryan
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="PERSON")
@DiscriminatorColumn(name = "PERSOON_TYPE") // As such, we know which Person Type we are dealing with.
public abstract class Person implements Serializable {

    @Id
    @Column(name="idPerson")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    public Person() {
    }
    
    // **** GETTERS AND SETTERS ****
    
    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }
    
}
