package test;

import model.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieGoerTest {

    private Movie m1, m2, m3;
    private MovieGoer mg1, mg2;
    private Ticket t1, t2;
    private TicketKiosk tk;

    @Before
    public void setUp() {
        tk = new TicketKiosk("Scotiabank Theatre");
        mg1 = new MovieGoer("Tom Hanks", 28, tk);
        mg2 = new MovieGoer("Sarah Johnson", 17, tk);
        m1 = new Movie("Arrival", 19, 50);
        m2 = new Movie("Ratatouille", 0, 100);
        m3 = new Movie("Ratatouille1", 0, 0);
        t1 = new Ticket(m1);
        t2 = new Ticket(m2);
    }

    @Test
    public void testGetters() {
        assertEquals(mg1.getName(), "Tom Hanks");
        assertEquals(mg2.getName(), "Sarah Johnson");
        assertEquals(mg1.getAge(), 28);
        assertEquals(mg2.getAge(), 17);
        assertEquals(mg1.getTicketKiosk(), tk);
        assertEquals(mg2.getTicketKiosk(), tk);
    }

    @Test
    public void testBuyTicketNoUnderAgeException() {
        try {
            mg1.buyTicket(m1);
        } catch (Exception e) {
            fail("Exception not expected");
        }
    }

    @Test
    public void testBuyTicketUnderAgeException() {
        try {
            mg2.buyTicket(m1);
            fail("Exception expected!");
        } catch (UnderAgeException e) {
            System.out.println(e.getMessage());
        } catch (ShowingFullException e) {
           fail("Under age exception expected");
        }
    }

    @Test
    public void testBuyTicketShowingFullException() {
        try {
            mg2.buyTicket(m3);
            fail("Exception expected!");
        } catch (UnderAgeException e) {
            fail("Showing full exception expected");
        } catch (ShowingFullException e) {
            System.out.println(e.getMessage());
        }

    }


}