package middleware.ApplicationStub;

import org.json.simple.JSONObject;

public interface IApplicationStub {

    //TODO: Implementiert alle aus den Interfaces der anderen Klassen
    //TODO: Implementiert call und send
    //TODO: funktioniert mit switch case
    public abstract void receiveAsynchronous(JSONObject json);

    JSONObject receiveSynchronous(JSONObject json);

    void handle(JSONObject response, String objectName);
}
