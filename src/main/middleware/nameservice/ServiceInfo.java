package middleware.nameservice;

import java.util.Arrays;

class ServiceInfo {
    private String funcName;
    private String[] paramTypes;
    private int id;
    private boolean available;

    private long lastHeartbeat;
    public ServiceInfo(String funcName, String[] paramTypes, int id) {
        this.funcName = funcName;
        this.paramTypes = Arrays.copyOf(paramTypes, paramTypes.length);
        this.id = id;
        this.available = true;
    }

    public String getFuncName() {
        return funcName;
    }

    public String[] getParamTypes() {
        return Arrays.copyOf(paramTypes, paramTypes.length);
    }

    public int getId() {
        return id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public long getLastHeartbeat() {
        return lastHeartbeat;
    }

    public void setLastHeartbeat(long lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }
}