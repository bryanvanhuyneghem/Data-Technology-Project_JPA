package WebData;

import DBClasses.Coin;
import DBClasses.CoinAndQuantity;
import DBClasses.Exchange;
import DBClasses.Founder;
import DBClasses.Trader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.json.JSONException;
import org.json.*;

/**
 *
 * @author Michiel & Bryan
 */
public class CoinmarketcapDAO {

//<editor-fold defaultstate="collapsed" desc="Fields">
    // Declare fields
    private URL url;
    private String jsonData;
    // JSONArray in which JSON objects will be stored
    private JSONArray coins;
    private Set<Coin> coinsToBeAdded;

    // Declare Entity Factory and Manager
    private final EntityManagerFactory emFactory;
    private final EntityManager entityManager;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Constructor">
    // Constructor
    public CoinmarketcapDAO(String path) {
        coinsToBeAdded = new HashSet<>();
        // Try to establish a stream with a given path url
        try {
            url = new URL(path);
            try (InputStream is = url.openStream()) {
                jsonData = convertStreamToString(is);   // Converts the stream to one String
            } catch (IOException ex) {
                System.out.println("Cannot open stream");
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(CoinmarketcapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Create Factory and Manager
        emFactory = Persistence.createEntityManagerFactory("ProjectCoinPU");
        entityManager = emFactory.createEntityManager();

        // Fetch Coins
        fetchCoins();

        // Create Exchanges
        createExchanges();

    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="****** API Fetching ******">
    // ****** API Fetching ******
    public final void fetchCoins() {
        // Create a JSONArray with the given String
        try {

            coins = new JSONArray(jsonData);
            // Loop the JSONArray
            for (int i = 0; i < coins.length(); i++) {
                beginTransaction();
                // Create a Coin object from every item in the JSONArray
                Coin coin = new Coin();
                try {

                    // Create JSON Objects from the JSON Array
                    JSONObject jsonobject = coins.getJSONObject(i);

                    // Map JSONObject data into the Coin object.
                    if (getCoinId(jsonobject.getString("symbol")) == null) { 
                        coin.setId(jsonobject.getString("symbol"));
                    } else { // Potential rare occurence where two coins have the same symbol: use the ID
                        coin.setId(jsonobject.getString("id"));
                    }
                    coin.setName(jsonobject.getString("name"));
                    coin.setSymbol(jsonobject.getString("symbol"));
                    coin.setRank(Integer.parseInt(jsonobject.getString("rank")));
                    coin.setPriceUSD(Double.parseDouble(jsonobject.isNull("price_usd") ? "0" : jsonobject.getString("price_usd")));
                    coin.setPriceBTC(Double.parseDouble(jsonobject.isNull("price_btc") ? "0" : jsonobject.getString("price_btc")));
                    coin.setVolumeUSD24h(Double.parseDouble(jsonobject.isNull("24h_volume_usd") ? "0" : jsonobject.getString("24h_volume_usd")));
                    coin.setMarketCapUSD(Double.parseDouble(jsonobject.isNull("market_cap_usd") ? "0" : jsonobject.getString("market_cap_usd")));
                    coin.setAvailableSupply(Double.parseDouble(jsonobject.isNull("available_supply") ? "0" : jsonobject.getString("available_supply")));
                    coin.setTotalSupply(Double.parseDouble(jsonobject.isNull("total_supply") ? "0" : jsonobject.getString("total_supply")));
                    coin.setMaxSupply(Double.parseDouble(jsonobject.isNull("max_supply") ? "0" : jsonobject.getString("max_supply")));
                    coin.setPercentChange1h(Double.parseDouble(jsonobject.isNull("percent_change_1h") ? "0" : jsonobject.getString("percent_change_1h")));
                    coin.setPercentChange24h(Double.parseDouble(jsonobject.isNull("percent_change_24h") ? "0" : jsonobject.getString("percent_change_24h")));
                    coin.setPercentChange7d(Double.parseDouble(jsonobject.isNull("percent_change_7d") ? "0" : jsonobject.getString("percent_change_7d")));

                    persistObject(coin);
                    commitTransaction();
                    coinsToBeAdded.add(coin);

                } catch (JSONException ex) {
                    Logger.getLogger(CoinmarketcapDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

            }   // end of loop

        } catch (JSONException ex) {
            Logger.getLogger(CoinmarketcapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="****** Create three Exchanges ******">
    // ****** Create three Exchanges ******
    public Exchange createExchange(String name) {
        beginTransaction();
        Exchange exchange = new Exchange();
        exchange.setName(name);
        persistObject(exchange);
        commitTransaction();
        return exchange;
    }

    public final void createExchanges() {
        Exchange gdax = createExchange("GDAX");
        Exchange bitfinex = createExchange("Bitfinex");
        Exchange pipo = createExchange("PipEx");
        // Initialise GDAX
        beginTransaction();
        gdax.addCoin(getCoinId("BTC"));
        gdax.addCoin(getCoinId("ETH"));
        gdax.addCoin(getCoinId("LTC"));
        gdax.addCoin(getCoinId("BCH"));
        // Initialise Bitfinex
        bitfinex.addCoin(getCoinId("BTC"));
        bitfinex.addCoin(getCoinId("ETH"));
        bitfinex.addCoin(getCoinId("LTC"));
        bitfinex.addCoin(getCoinId("ETC"));
        bitfinex.addCoin(getCoinId("ZEC"));
        bitfinex.addCoin(getCoinId("XMR"));
        bitfinex.addCoin(getCoinId("DASH"));
        bitfinex.addCoin(getCoinId("XRP"));
        bitfinex.addCoin(getCoinId("MIOTA"));
        bitfinex.addCoin(getCoinId("EOS"));
        bitfinex.addCoin(getCoinId("SAN"));
        bitfinex.addCoin(getCoinId("OMG"));
        bitfinex.addCoin(getCoinId("BCH"));
        bitfinex.addCoin(getCoinId("NEO"));
        bitfinex.addCoin(getCoinId("ETP"));
        bitfinex.addCoin(getCoinId("QTUM"));
        bitfinex.addCoin(getCoinId("AVT"));
        bitfinex.addCoin(getCoinId("EDO"));
        bitfinex.addCoin(getCoinId("BTG"));
        bitfinex.addCoin(getCoinId("DATA"));
        bitfinex.addCoin(getCoinId("YOYOW"));
        bitfinex.addCoin(getCoinId("QASH"));
        // Initialise PipEx
        pipo.addCoin(getCoinId("BTC"));
        pipo.addCoin(getCoinId("ETH"));
        pipo.addCoin(getCoinId("LTC"));
        pipo.addCoin(getCoinId("KNC"));
        pipo.addCoin(getCoinId("TRX"));
        pipo.addCoin(getCoinId("MTH"));
        pipo.addCoin(getCoinId("XMR"));
        pipo.addCoin(getCoinId("ADA"));
        pipo.addCoin(getCoinId("XLM"));
        pipo.addCoin(getCoinId("MIOTA"));
        pipo.addCoin(getCoinId("BTS"));
        pipo.addCoin(getCoinId("VTC"));
        pipo.addCoin(getCoinId("EMC2"));
        pipo.addCoin(getCoinId("MANA"));
        pipo.addCoin(getCoinId("EDG"));
        pipo.addCoin(getCoinId("PRG"));
        pipo.addCoin(getCoinId("ZEC"));
        commitTransaction(); // Important to commit here, else the table Exchange_has_coins will not be filled.
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="****** DAO interactions with Database for Coins ******">
    // ****** DAO interactions with Database for Coins ******
    public Coin addCoin(String name, String id) {
        beginTransaction();
        try {
            Coin coin = new Coin();
            coin.setName(name);
            coin.setSymbol(id);
            coin.setId(id);
            persistObject(coin);
            commitTransaction();
            return coin;
        } catch (Exception e) {
            System.out.println("ID van " + name + " bestaat al er wordt een nieuwe voor u gegenereerd.");
            beginTransaction();
            // If the Coin's Id already exists in the Database, it is assumed that the user
            // wants to add the new Coin nonetheless. Therefore an Id is generated for the user,
            // based on the Id provided by the user.
            Random r = new Random();
            Coin coin = new Coin();
            coin.setName(name);
            while (getCoinId(id) != null) {
                // Concatinate the given Id with a random capital letter from the ASCII-table
                id += Character.toString((char) (r.nextInt(26) + 65));
            }
            coin.setId(id);
            persistObject(coin);
            commitTransaction();
            return coin;
        }
    }

    // Remove a Coin from the Database
    public boolean removeCoin(Coin coin) {
        beginTransaction();
        try {
            entityManager.remove(coin);
            commitTransaction();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Remove a Coin from the Database by Id
    public boolean removeCoinById(String id) {
        try {
            Coin coin = entityManager.find(Coin.class, id);
            if (coin == null) {
                throw new IllegalArgumentException();
            }
            return removeCoin(coin);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // Update a Coin to your choosing
    public boolean updateCoin(String id, double price, double supply) {
        beginTransaction();
        try {
            Coin coin = entityManager.find(Coin.class, id);
            if (price != -1) {
                coin.setPriceUSD(price);
            }
            if (supply != -1) {
                coin.setAvailableSupply(supply);
            }
            commitTransaction();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Find the Id of a Coin in the Databasee.
    public Coin getCoinId(String id) {
        return entityManager.find(Coin.class, id);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="****** Trader and Founder methods ******">
    
    // ****** Trader methods ******
    
    //CoinAndQuantity
    public boolean createCoinAndQuantity(Trader trader, Coin coin, double value) {
        beginTransaction();
        try {
            CoinAndQuantity CAQ = new CoinAndQuantity();
            CAQ.setCoin(coin);
            CAQ.setQuantity(value);
            CAQ.setTrader(trader);
            persistObject(CAQ);
            commitTransaction();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    //Founder methodes
    public Founder createFounder(String name, Coin coin) {
        beginTransaction();
        try {
            Founder founder = new Founder();
            founder.setName(name);
            founder.setFoundedCoin(coin);
            persistObject(founder);
            commitTransaction();
            return founder;
        } catch (Exception e) {
            return null; //No founder when a fault occurs
        }
    }

    // Find a Founder by name
    public Founder getFounderByName(String name) {
        try {
            TypedQuery<Founder> query = entityManager.createQuery("Select f From Person p join Founder f on f.id = p.id where p.name=:naam", Founder.class);
            query.setParameter("naam", name);
            return query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Founder met naam " + name + " werd niet gevonden in onze database");
            return null;
        }
    }

    // Remove a Founder by name
    public boolean removeFounderByName(String name) {
        try {
            Founder founder = getFounderByName(name);
            if (founder == null) {
                throw new IllegalArgumentException();
            }
            beginTransaction();
            entityManager.remove(founder);
            commitTransaction();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // Create a new Trader
    public Trader createTrader(String name) {
        try {
            Trader trader = new Trader();
            trader.setName(name);
            beginTransaction();
            persistObject(trader);
            commitTransaction();
            return trader;
        } catch (Exception e) {
            return null;
        }
    }

    // Find a Trader by name
    public Trader getTraderByName(String name) {
        try {
            TypedQuery<Trader> query = entityManager.createQuery("Select t From Person p join Trader t on t.id = p.id where p.name=:naam", Trader.class);
            query.setParameter("naam", name);
            return query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Trader met naam " + name + " werd niet gevonden in onze database.");
            return null;
        }
    }

    // Remove a Trader by name
    public boolean removeTraderByName(String name) {
        try {
            Trader trader = getTraderByName(name);
            if (trader == null) {
                throw new IllegalArgumentException();
            }
            beginTransaction();
            entityManager.remove(trader);
            commitTransaction();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
        /*try {
            /*Query query = entityManager.createQuery("DELETE From Person p where (select max(p.name) from Person p join Trader t on t.id = p.id where p.name = :naam) = :naam");
            query.setParameter("naam", name);
            beginTransaction();
            query.executeUpdate();
            commitTransaction();
            return  true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Trader met naam " + name + " werd niet gevonden in onze database");
            return false;
        } Werkt niet :'( */
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="****** Transaction Methods ******">
    // ****** Transaction Methods ******
    // Begin the Transaction
    public void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    // Commit the Transaction
    public void commitTransaction() {
        entityManager.getTransaction().commit();
    }

    // Persist an Object to the Database
    public void persistObject(Object o) {
        entityManager.persist(o);
    }
    
    public void closeConnection(){
        entityManager.close();
        emFactory.close();
    }
//</editor-fold>
}
