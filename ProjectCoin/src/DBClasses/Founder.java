package DBClasses;

import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Michiel & Bryan
 */

@Entity
@Table(name="FOUNDER")
@PrimaryKeyJoinColumn(name="ID" , referencedColumnName = "idPerson")// Points to the idPerson of Person class
@DiscriminatorValue("F")
public class Founder extends Person{
    
    @OneToOne
    @JoinColumn(name="FoundedCoin")
    private Coin FoundedCoin;
    
    // No ID field needed due to its reference to Person's ID field (similar to Trader).
    
    public Founder() {
    }
    
    // **** GETTERS AND SETTERS ****
    
    public Coin getFoundedCoin() {
        return FoundedCoin;
    }
    
    public void setFoundedCoin(Coin FoundedCoin) {
        this.FoundedCoin = FoundedCoin;
        if(FoundedCoin.getCoinFounder() == null)
            FoundedCoin.setCoinFounder(this);
    }  

    // Generated hashCode and equals methodes
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + getName().hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Founder other = (Founder) obj;
        return this.getId() == other.getId();
    }
    
    
}
