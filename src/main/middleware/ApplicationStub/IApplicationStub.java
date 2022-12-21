package middleware.ApplicationStub;

import org.json.simple.JSONObject;

public interface IApplicationStub {

    public abstract void receiveAsynchronous(JSONObject json);

    JSONObject receiveSynchronous(JSONObject json);

    void handle(JSONObject response, String objectName);
}
