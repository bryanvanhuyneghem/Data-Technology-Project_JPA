package DBClasses;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Michiel & Bryan
 */

/**
 *
 * Exchange is connected with Coin in a ManyToMany relation, which has been made
 * possible through the join table below.
 *
 */

@Entity
@Table(name = "EXCHANGE")
public class Exchange implements Serializable {

    @Id
    @Column(name = "idExchange")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    //ManyToMany association
    @JoinTable(name = "Exchange_has_coins",
            joinColumns = {
                @JoinColumn(name = "Exchange_id", referencedColumnName = "idExchange")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "coin_id") // Use default id of Coin
            }
    )
    @ManyToMany (fetch = FetchType.LAZY)
    private Set<Coin> coins = new HashSet<>();

    public Exchange() {
    }

    // Add a Coin
    public void addCoin(Coin coinToBeAdded) {
        //System.out.println(coinToBeAdded.getName());
        this.coins.add(coinToBeAdded);
        if (coinToBeAdded.getExchanges() != null) { // check if there is a Set yet or not (~ null))
            coinToBeAdded.getExchanges().add(this);
        } else {
            Set<Exchange> exchanges = new HashSet<>();
            coinToBeAdded.setExchanges(exchanges);
            coinToBeAdded.getExchanges().add(this);
        }

    }

    // Add a Set of Coins
    public void addCoins(Set<Coin> coinsToBeAdded) {
        for (Coin coin : coinsToBeAdded) {
            addCoin(coin);
        }
    }

    // **** GETTERS AND SETTERS ****
    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public Set<Coin> getCoins() {
        return coins;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCoins(Set<Coin> coins) {
        this.coins = coins;
    }

    // Generated hashCode and equals methodes
    
    @Override
    public int hashCode() {
        int hash = 5;
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
        final Exchange other = (Exchange) obj;
        return this.getId() == other.getId();
    }

}
