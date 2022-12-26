package middleware.nameservice;

import java.util.*;

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
        // Find all registered services with the given function name
        List<ServiceInfo> matchingServices = new ArrayList<>();
        for (ServiceInfo serviceInfo : registeredServices.values()) {
            if (serviceInfo.getFuncName().equals(funcName) && serviceInfo.isAvailable()) {
                matchingServices.add(serviceInfo);
            }
        }

        // Return the parameter types for each matching service
        String[] paramTypes = new String[matchingServices.size()];
        for (int i = 0; i < matchingServices.size(); i++) {
            paramTypes[i] = Arrays.toString(matchingServices.get(i).getParamTypes());
        }
        return paramTypes;
    }

    public String[] lookup() {
        //find all registered services
        List<ServiceInfo> services = new ArrayList<>();
        for (ServiceInfo serviceInfo : registeredServices.values()) {
            if (serviceInfo.isAvailable()) {
                services.add(serviceInfo);
            }
        }
        return services.toString().split(",");
    }

    public void heartbeatReq(int maxResponseTime) {
        long currentTime = System.currentTimeMillis();
        for (ServiceInfo serviceInfo : registeredServices.values()) {
            // Check if the service has sent a heartbeat within the specified time window
            if (currentTime - serviceInfo.getLastHeartbeat() > maxResponseTime) {
                // The service has not sent a heartbeat within the time window, mark it as not available
                serviceInfo.setAvailable(false);
            } else {
                // The service has sent a heartbeat within the time window, mark it as available
                serviceInfo.setAvailable(true);
            }
        }
    }

    public void updateLastHeartbeat(int serviceId) {
        ServiceInfo serviceInfo = registeredServices.get(serviceId);
        if (serviceInfo != null) {
            serviceInfo.setLastHeartbeat(System.currentTimeMillis());
        }
    }
}
