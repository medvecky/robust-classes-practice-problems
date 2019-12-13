package model;

public class MovieGoer {

    private String name;
    private int age;
    private Ticket ticket;
    private TicketKiosk tk;

    public MovieGoer(String name, int age, TicketKiosk tk) {
        this.name = name;
        this.age = age;
        this.tk = tk;
        ticket = null;
    }

    // getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public TicketKiosk getTicketKiosk() {
        return tk;
    }

    public Ticket getTicket() {
        return ticket;
    }

    // REQUIRES: the movie (m) must not be at full capacity, i.e. more people can watch the movie
    //           this moviegoer must be of appropriate age to watch the movie (age > m.ageRestriction)
    // MODIFIES: this
    // EFFECTS: a new ticket associated with the given movie is created, and
    //           becomes this MovieGoer's ticket
    public void buyTicket(Movie m) throws UnderAgeException, ShowingFullException {
        if (age <= m.getAgeRestriction()) {
            throw new UnderAgeException();
        }

        if (m.isFull()) {
            throw new ShowingFullException();
        }

        ticket = new Ticket(m);
        m.addViewer();
    }


}
