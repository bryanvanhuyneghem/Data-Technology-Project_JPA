package DBClasses;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Michiel & Bryan
 */
@Entity
@Table(name = "CoinAndQuantity")
public class CoinAndQuantity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double quantity;

    @OneToOne   //This class knows its Coins, but the Coins don't know to which CoinAndQuantity they belong.
    @JoinColumn(name="Coin")
    private Coin coin;

    @ManyToOne // A Trader can hold many CoinAndQuantities (i.e. multiple Coins, each with their amount).
    @JoinColumn(name="Trader")
    private Trader trader;

    public CoinAndQuantity() {
    }

    // **** GETTERS AND SETTERS ****
    public Coin getCoin() {
        return coin;
    }

    public long getId() {
        return id;
    }

    public Trader getTrader() {
        return trader;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
        trader.addCoinAndQuantity(this);
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
