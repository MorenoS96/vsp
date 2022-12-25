package middleware.nameservice;

import java.util.HashMap;
import java.util.Map;

public class NameService {

    //TODO was tut der NameService? Soll dieser nicht nur die Lobbys verwalten? (IP, port, id)
    //TODO brauchen wir ApplicationStub für View?
    //TODO id bei register ist für welche Lobby

    private static final int DEFAULT_SERVICE_ID = -1;
    private static final int INVALID_SERVICE_ID = -2;

    private static NameService instance;
    private int nextAvailableId;
    private Map<Integer, ServiceInfo> registeredServices;

    private NameService() {
        nextAvailableId = 0;
        registeredServices = new HashMap<>();
    }

    public static NameService getInstance() {
        if (instance == null) {
            instance = new NameService();
        }
        return instance;
    }

    public int register(String funcName, String[] paramTypes, int id) {
        // Check if the service is already registered
        if (id >= 0) {
            ServiceInfo existingService = registeredServices.get(id);
            if (existingService != null) {
                // Check if the existing service is still available
                if (existingService.isAvailable()) {
                    // The service is already registered and available, return the existing service ID
                    return id;
                } else {
                    // The service is already registered but not available, assign a new ID
                    id = DEFAULT_SERVICE_ID;
                }
            }
        }

        // Assign a new ID if necessary
        if (id == DEFAULT_SERVICE_ID) {
            id = nextAvailableId++;
        }

        // Register the service
        ServiceInfo serviceInfo = new ServiceInfo(funcName, paramTypes, id);
        registeredServices.put(id, serviceInfo);

        // Return the assigned service ID
        return id;
    }

    public String[] lookup(String funcName) {
        return null;
    }

    void heartbeatReq(int maxResponseTime) {

    }
}
