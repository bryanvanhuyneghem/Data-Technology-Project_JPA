package TestPackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import DBClasses.Coin;
import DBClasses.CoinAndQuantity;
import DBClasses.Exchange;
import DBClasses.Founder;
import DBClasses.Trader;
import WebData.CoinmarketcapDAO;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bryan & Michiel
 */
public class ProjectCoinTest {
    
    private static CoinmarketcapDAO dao;
    
    public ProjectCoinTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        dao = new CoinmarketcapDAO("https://api.coinmarketcap.com/v1/ticker/?limit=1000");
    }
    
    @AfterClass
    public static void tearDownClass() {
        dao.closeConnection(); // Close all connections.
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testAddAndFindCoin() {
        // Add a new Coin
        Coin pipoCoin = dao.addCoin("Pipocoin", "PLC"); // Add a Pipo Coin to the database
        assertEquals(dao.getCoinId("PLC").getSymbol(),"PLC"); // Find the Pipo Coin (getCoinId will find the Pipo Coin)
        // Create a test Coin with same characteristics
        Coin pipoCoinTest = new Coin(); 
        pipoCoinTest.setName("Pipocoin");
        pipoCoinTest.setId("PLC");
        pipoCoinTest.setSymbol("PLC");
        assertTrue(dao.getCoinId("PLC").equals(pipoCoinTest)); // These objects should be the same.
    }
    
    @Test 
    public void testExistingCoin(){
        // Add an existing Coin
        Coin btc = dao.addCoin("Bitcoin", "BTC"); // addCoin will execute its catch and add a random character to the given ID
        assertFalse(btc.getId().equals("BTC")); // The given ID of the user-created Coin should not be BTC, but something like BTCA or BTCB etc.
    }
    
    @Test
    public void testFindFakeCoin(){
        // Fake Coin
        Coin testCoin = new Coin(); // Create a dummy Coin called testCoin, which is not in the database
        testCoin.setName("Testcoin1"); 
        testCoin.setSymbol("TESTC1"); 
        assertNull(dao.getCoinId("TESTC1")); // Will return null if it is not in the database
    }
    
    @Test
    public void testFindRemovedCoin(){
        // Removed Coin
        Coin pipoCoin = dao.addCoin("Testcoin2", "TESTC2"); // Add a Test Coin to the database
        assertEquals(dao.getCoinId("TESTC2").getSymbol(),"TESTC2"); // Find the Pipo Coin (getCoinId will find the Pipo Coin)
        dao.removeCoin(pipoCoin); // Remove the Pipo Coin
        assertNull(dao.getCoinId("TESTC2")); // getCoinId won't find the Pipo Coin any more
    }
    
    @Test
    public void testRemoveCoinByID(){
        Coin pipoCoin = dao.addCoin("Testcoin3", "TESTC3"); // Add a Test Coin to the database
        assertEquals(dao.getCoinId("TESTC3").getSymbol(),"TESTC3"); // Find the Pipo Coin (getCoinId will find the Pipo Coin)
        assertTrue(dao.removeCoinById("TESTC3")); // Remove the Pipo Coin
    }
    
    @Test
    public void testUpdateCoin(){
        Coin pipoCoin = dao.addCoin("Testcoin4", "TESTC4"); // Add a Test Coin to the database
        assertEquals(dao.getCoinId("TESTC4").getSymbol(),"TESTC4"); // Find the Pipo Coin (getCoinId will find the Pipo Coin)
        assertTrue(dao.updateCoin("TESTC4",1000,1000000)); // Update the Coin
        assertEquals(pipoCoin.getPriceUSD(),1000,0.0001); // Is the price correct?
        assertEquals(pipoCoin.getAvailableSupply(),1000000,0.0001); // Is the supply correct?
    }
    
    @Test
    public void testCreateAndFindTrader(){
        Trader trader = dao.createTrader("James"); // Create a Trader
        Trader fetchedTrader = dao.getTraderByName("James"); // Fetch the Trader
        assertTrue(fetchedTrader.equals(trader)); // Are they the same?
    }
    
    @Test
    public void testCreateCoinAndQuantity(){
        Random r = new Random();
        double randomNumber = r.nextDouble()*1000;
        
        Trader trader = dao.createTrader("Igor"); // Create a Trader
        
        // Add Dual Coins
        Coin dualCoin = dao.addCoin("Dualcoin", "DLCN"); // Create a Dual Coin
        dualCoin.setAvailableSupply(randomNumber); // Set the supply to x amount of Dual Coin
        dao.createCoinAndQuantity(trader, dualCoin, randomNumber); // Give the Trader x amount of Dual Coins and create CAQ
        // Add Bitcoins
        Coin bitcoin = dao.getCoinId("BTC"); // Fetch Bitcoin
        dao.createCoinAndQuantity(trader, bitcoin, randomNumber); // Give the Trader x amount of Bitcoin and create CAQ
        
        Set<CoinAndQuantity> coinsInWallet = trader.getCoinsInWallet();
        
        for (CoinAndQuantity coinAndQuantity : coinsInWallet) {
            if (coinAndQuantity.getCoin().getId().equals("BTC") || coinAndQuantity.getCoin().getId().equals("DLCN")){
                assertEquals(coinAndQuantity.getQuantity(), randomNumber, 0.00001);
            }
            else{
                fail("You shouldn't have this Coin.");
            }
        }
    }
    
    @Test
    public void testRemoveTrader(){
        Trader trader = dao.createTrader("Michiel"); // Create a Trader
        Trader fetchedTrader = dao.getTraderByName("Michiel"); // Fetch the Trader
        assertTrue(fetchedTrader.equals(trader)); // Are they the same?
        assertTrue(dao.removeTraderByName("Michiel")); // Remove the Trader
        assertNull(dao.getTraderByName("Michiel")); // You shouldn't be here anymore
    }
    
    @Test
    public void testRemoveDummyTrader(){
        assertFalse(dao.removeTraderByName("Bryan")); // Remove a non-existing Trader
    }
    
    @Test
    public void testCreateandFindFounder(){
        Coin bitcoin = dao.getCoinId("BTC"); // Get Bitcoin
        dao.createFounder("Satoshi Nakamoto", bitcoin); // Create a Founder for Bitcoin
        Founder sat = dao.getFounderByName("Satoshi Nakamoto"); // Get the Founder
        Coin coin = sat.getFoundedCoin(); // Get the Founder's founded Coin
        assertTrue(coin.equals(bitcoin)); // Are the Coins the same?
    }
    
    @Test
    public void testRemoveFounder(){
        Coin frostycoin = dao.addCoin("FrostyCoin", "FRYC"); // Add a new Coin
        Founder frosty = dao.createFounder("Frosty", frostycoin); // Set its Founder 
        assertTrue(dao.removeCoin(frostycoin)); // Remove the Frostycoin
        assertNull(dao.getCoinId("FRYC")); // Frosty's Frostycoin should no longer be in the database
        assertNull(dao.getFounderByName("Frosty")); // Frosty should no longer be in the database either
    }
    
    @Test
    public void testCreateAndAddExchange(){
        Exchange exch = dao.createExchange("FrostyEx"); // Create an Exchange
        
        Coin smd = dao.addCoin("SUMD", "SMD"); // Create a Coin
        Coin ups = dao.addCoin("UPSY", "ups"); // Create a second Coin
        Set<Coin> coins = new HashSet<>(); // Create a Set
        coins.add(ups); // add a coin
        coins.add(smd); // add a coin
        exch.addCoins(coins); // add the set of coins to the Exchange
        
        for (Coin coin : exch.getCoins()) { // Are these coins the same coins that were added to the exchange?
            if (coin.equals(smd) || coin.equals(ups)){
                assertTrue(true);
            }
            else {
                fail("The Exchange has Coins it should'nt have.");
            }
        }
    }
    
}
