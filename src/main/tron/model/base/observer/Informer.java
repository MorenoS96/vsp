package tron.model.base.observer;

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
