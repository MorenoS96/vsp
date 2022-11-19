package tron.model.base.observer;

// Evtl. brauche ich das garnicht aber die View deswegen noch drin
public class Informer implements Observer {

    private Subject subject;

    public Informer(Subject subject) {
        this.subject = subject;
        subject.attach(this);
    }

    @Override
    public void update() { // Kommt noch
        System.out.println("Wurde informiert");
    }
}
