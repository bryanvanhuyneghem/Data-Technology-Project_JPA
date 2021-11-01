package DBClasses;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Michiel & Bryan
 */

@Entity
@Table(name="COIN")
public class Coin implements Serializable {
    
    @Id
    @Column(name="ID")
    private String id;
    private String name;
    private String symbol;
    private int rank;
    private double priceUSD;
    private double priceBTC;
    private double volumeUSD24h;
    private double marketCapUSD;
    private double availableSupply;
    private double totalSupply;
    private double maxSupply;
    private double percentChange1h;
    private double percentChange24h;
    private double percentChange7d;    
    
    //Bidirectional OneToOne
    @OneToOne(mappedBy = "FoundedCoin", cascade = CascadeType.ALL,orphanRemoval = true) // If a Coin is deleted, then the Founder is deleted too.
    private Founder coinFounder;
    
    //ManyToMany association
    @ManyToMany(mappedBy = "coins") // Bidirectional
    private Set<Exchange> exchanges;

    public Coin() {
    }

    public Coin(String id, String name, String symbol, int rank, double priceUSD, double priceBTC, double volumeUSD24h, double marketCapUSD, double availableSupply, double totalSupply, double maxSupply, double percentChange1h, double percentChange24h, double percentChange7d) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.rank = rank;
        this.priceUSD = priceUSD;
        this.priceBTC = priceBTC;
        this.volumeUSD24h = volumeUSD24h;
        this.marketCapUSD = marketCapUSD;
        this.availableSupply = availableSupply;
        this.totalSupply = totalSupply;
        this.maxSupply = maxSupply;
        this.percentChange1h = percentChange1h;
        this.percentChange24h = percentChange24h;
        this.percentChange7d = percentChange7d;
    }
    
    // **** GETTERS AND SETTERS ****
    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getRank() {
        return rank;
    }

    public double getPriceUSD() {
        return priceUSD;
    }

    public double getPriceBTC() {
        return priceBTC;
    }

    public double getVolumeUSD24h() {
        return volumeUSD24h;
    }

    public double getMarketCapUSD() {
        return marketCapUSD;
    }

    public double getAvailableSupply() {
        return availableSupply;
    }

    public double getTotalSupply() {
        return totalSupply;
    }

    public double getMaxSupply() {
        return maxSupply;
    }

    public double getPercentChange1h() {
        return percentChange1h;
    }

    public double getPercentChange24h() {
        return percentChange24h;
    }

    public double getPercentChange7d() {
        return percentChange7d;
    }
    
    public Set<Exchange> getExchanges() {
        return exchanges;
    }

    public Founder getCoinFounder() {
        return coinFounder;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setPriceUSD(double priceUSD) {
        this.priceUSD = priceUSD;
    }

    public void setPriceBTC(double priceBTC) {
        this.priceBTC = priceBTC;
    }

    public void setVolumeUSD24h(double volumeUSD24h) {
        this.volumeUSD24h = volumeUSD24h;
    }

    public void setMarketCapUSD(double marketCapUSD) {
        this.marketCapUSD = marketCapUSD;
    }

    public void setAvailableSupply(double availableSupply) {
        this.availableSupply = availableSupply;
    }

    public void setTotalSupply(double totalSupply) {
        this.totalSupply = totalSupply;
    }

    public void setMaxSupply(double maxSupply) {
        this.maxSupply = maxSupply;
    }

    public void setPercentChange1h(double percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    public void setPercentChange24h(double percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public void setPercentChange7d(double percentChange7d) {
        this.percentChange7d = percentChange7d;
    }
    
    public void setExchanges(Set<Exchange> Exchanges) {
        this.exchanges = Exchanges;
    }

    public void setCoinFounder(Founder CoinFounder) {
        this.coinFounder = CoinFounder;
        if(CoinFounder.getFoundedCoin() == null)
            CoinFounder.setFoundedCoin(this);
    }

    @Override
    public String toString() {
        return "Coin{" + "id=" + id + ", name=" + name + ", symbol=" + symbol + ", rank=" + rank + ", priceUSD=" + priceUSD + ", priceBTC=" + priceBTC + ", volumeUSD24h=" + volumeUSD24h + ", marketCapUSD=" + marketCapUSD + ", availablieSupply=" + availableSupply + ", totalSupply=" + totalSupply + ", maxSupply=" + maxSupply + ", percentChange1h=" + percentChange1h + ", percentChange24h=" + percentChange24h + ", percentChange7d=" + percentChange7d + ", coinFounder=" + coinFounder + ", exchanges=" + exchanges + '}';
    }

    // Generated hashCode and equals methodes
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.symbol);
        hash = 79 * hash + this.rank;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.priceUSD) ^ (Double.doubleToLongBits(this.priceUSD) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.priceBTC) ^ (Double.doubleToLongBits(this.priceBTC) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.volumeUSD24h) ^ (Double.doubleToLongBits(this.volumeUSD24h) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.marketCapUSD) ^ (Double.doubleToLongBits(this.marketCapUSD) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.availableSupply) ^ (Double.doubleToLongBits(this.availableSupply) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.totalSupply) ^ (Double.doubleToLongBits(this.totalSupply) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.maxSupply) ^ (Double.doubleToLongBits(this.maxSupply) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.percentChange1h) ^ (Double.doubleToLongBits(this.percentChange1h) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.percentChange24h) ^ (Double.doubleToLongBits(this.percentChange24h) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.percentChange7d) ^ (Double.doubleToLongBits(this.percentChange7d) >>> 32));
        hash = 79 * hash + Objects.hashCode(this.exchanges);
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
        final Coin other = (Coin) obj;
        return (other.getId().equals(this.getId()));
    }
    
    
    
}
