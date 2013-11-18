/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myatm;

import org.junit.rules.ExpectedException;
import myatm.ATM.*;
import org.junit.Rule;
import static org.mockito.Mockito.*;
import org.junit.Ignore;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sebik
 */
public class ATMTest {
    public static Card card;
    public static Account acco;    
    public static ATM atm;
    
    public ATMTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        atm = new ATM(500);
        card = mock(Card.class);
        acco = mock(Account.class);
    }
    
    @After
    public void tearDown() {
    }
    
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test of validateCard method, of class ATM.
     */
    //@Ignore
    @Test
    //!!! Also this method is used to initialise normal condition of card and account.
    public void ValidateCard_valuesOK_validationTrue() throws NoCardInserted {
        int pinCodeOK = 1234;
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(pinCodeOK)).thenReturn(true);
        when(card.getAccount()).thenReturn(acco);
        System.out.println("ValidateCard_valuesOK_validationTrue");
        boolean result = atm.validateCard(card, pinCodeOK);
        assertTrue(result);
    }
    
    /**
     * Negative test of validateCard method, of class ATM.
     */
    //@Ignore
    @Test
    public void ValidateCard_valuesBLockedAndPaswWrong_validationFalse() throws NoCardInserted {
        int pinCodeOK = 1234;
        when(card.isBlocked()).thenReturn(true);
        when(card.checkPin(pinCodeOK)).thenReturn(true);
        when(card.getAccount()).thenReturn(acco);
        System.out.println("ValidateCard_valuesBLockedAndPaswWrong_validationFalse");
        int pinCodeBad = 12345;
        boolean result = atm.validateCard(card, pinCodeBad);
        assertFalse(result);
    }
     
    /**
     * Test of getMoneyInATM method, of class ATM.
     */
    //@Ignore
    @Test
    public void GetMoneyInATM_valuesOK_MoneyAreShown() {
        System.out.println("GetMoneyInATM_valuesOK_MoneyAreShown");
        double expResult = 500.0;
        double result = atm.getMoneyInATM();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of checkBalance method, of class ATM.
     */
    //@Ignore
    @Test
    public void CheckBalance_valuesOK_BalanceGiven() throws NoCardInserted {
        this.ValidateCard_valuesOK_validationTrue();
        when(acco.getBalance()).thenReturn(50.0);
        System.out.println("CheckBalance_valuesOK_BalanceGiven");
        double expResult = 50.0;
        double result = atm.checkBalance();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getCash method, of class ATM.
     */
    //@Ignore
    @Test
    public void GetCash_valuesOK_CashGot() throws NotEnoughMoneyInATM, NotEnoughMoneyInAccount, NoCardInserted {
        this.ValidateCard_valuesOK_validationTrue();
        when(acco.getBalance()).thenReturn(700.0).thenReturn(600.0);
        when(acco.withdrow(100.0)).thenReturn(100.0);
        System.out.println("GetCash_valuesOK_CashGot");
        double amount = 100.0;
        double expResult = 600.0;
        double result = atm.getCash(amount);
        assertEquals(expResult, result, 0.0);
        verify(acco, atLeastOnce()).getBalance();
    }
    
    /**
     * Test of getCash method, of class ATM.
     */
    //@Ignore
    @Test
    public void GetCash_valuesOK_getBalanceAtLeastOnce() throws NotEnoughMoneyInATM, NotEnoughMoneyInAccount, NoCardInserted {
        this.GetCash_valuesOK_CashGot();
        verify(acco, atLeastOnce()).getBalance();
    }
    
    /**
     * Test of getCash method, of class ATM.
     */
    //@Ignore
    @Test(expected=NotEnoughMoneyInATM.class)
    public void GetCash_noMoneyATM_ExceptionNotEnoughMoneyInATM() throws NotEnoughMoneyInATM, NotEnoughMoneyInAccount, NoCardInserted {
        this.ValidateCard_valuesOK_validationTrue();
        when(acco.getBalance()).thenReturn(700.0);
        System.out.println("GetCash_noMoneyATM_ExceptionNotEnoughMoneyInATM");
        double amount = 600.0;
        atm.getCash(amount);
    }
    
    /**
     * Test of getCash method, of class ATM.
     */
    //@Ignore
    @Test(expected=NotEnoughMoneyInAccount.class)
    public void GetCash_noMoneyAcc_ExceptionNotEnoughMoneyInAccount() throws NotEnoughMoneyInAccount, NotEnoughMoneyInATM, NoCardInserted {
        this.ValidateCard_valuesOK_validationTrue();
        when(acco.getBalance()).thenReturn(700.0);
        System.out.println("GetCash_noMoneyAcc_ExceptionNotEnoughMoneyInAccount");
        double amount = 800.0;
        atm.getCash(amount);
    }
      
    /**
     * Test of validateCard method, of class ATM.
     * Throwing NoCardInserted
     * cardBlockATM
     */
    //@Ignore
    @Test(expected=NoCardInserted.class)
    public void ValidateCard_cardIsBlocked_ExceptionNoCardInserted() throws NoCardInserted {
        when(card.isBlocked()).thenReturn(true);
        when(card.checkPin(1234)).thenReturn(true);
        when(card.getAccount()).thenReturn(acco);
        System.out.println("ValidateCard_cardIsBlocked_ExceptionNoCardInserted");
        int pinCode = 12345;
        atm.validateCard(card, pinCode);
        atm.validateCard(card, pinCode);
        atm.checkBalance();
    }
}
