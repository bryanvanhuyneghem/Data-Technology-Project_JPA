package DBClasses;

import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Michiel & Bryan
 */

@Entity
@Table(name = "TRADER")
@PrimaryKeyJoinColumn(name = "ID", referencedColumnName = "idPerson") // Points to the idPerson of Person class
@DiscriminatorValue("T")
public class Trader extends Person {

    @JoinColumn(name = "wallet")
    @OneToMany(mappedBy = "Trader", cascade = CascadeType.ALL, orphanRemoval = true)// If the Trader is deleted, its children are deleted as well.
    
    private Set<CoinAndQuantity> CoinsInWallet;
    
    // A Trader's ID is kept in Person's ID field. As such, no field for an ID is needed here.

    public Trader(){
    }
    
    public void addCoinAndQuantity(CoinAndQuantity CAQ) {
        getCoinsInWallet().add(CAQ);
        if (CAQ.getTrader() != this) {
            CAQ.setTrader(this);
        }
    }
    
    // **** GETTERS AND SETTERS ****

    public void setCoinsInWallet(Set<CoinAndQuantity> coins) {
        this.CoinsInWallet = coins;
    }

    public Set<CoinAndQuantity> getCoinsInWallet() {
        return CoinsInWallet;
    }

    // Generated hashCode and equals methodes
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.CoinsInWallet);
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
        final Trader other = (Trader) obj;
        return this.getId() == other.getId();
    }

}
