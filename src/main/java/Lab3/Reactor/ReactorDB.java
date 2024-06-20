package Lab3.Reactor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReactorDB {
    private String name;
    private Reactor reactor;
    private String country;
    private String operator;
    private String owner;
    private int thermalCapacity;
    private String region;
    private int shutdownYear;
    private int connectionYear;
    private Map<Integer, Double> loadFactor;
    private final Map<Integer, Double> fuelLoad = new HashMap<>();

    public int getConnectionYear() {
        return connectionYear;
    }

    public void setConnectionYear(int connectionYear) {
        this.connectionYear = connectionYear;
    }

    public int getShutdownYear() {
        return shutdownYear;
    }

    public void setShutdownYear(int shutdownYear) {
        this.shutdownYear = shutdownYear;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Reactor getReactor() {
        return reactor;
    }

    public void setType(String type_name, ArrayList<Reactor> reactorTypes) {
        chooseType(type_name, reactorTypes);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getThermalCapacity() {
        return thermalCapacity;
    }

    public void setThermalCapacity(int thermalCapacity) {
        this.thermalCapacity = thermalCapacity;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Map<Integer, Double> getLoadFactor() {
        return loadFactor;
    }

    public void setLoadFactor(Map<Integer, Double> loadFactor) {
        this.loadFactor = loadFactor;
    }

    public Map<Integer, Double> getFuelLoad() {
        return fuelLoad;
    }

    private void chooseType(String type_name, ArrayList<Reactor> reactorTypes) {
        switch (type_name) {
            case "PWR" -> findReactor(reactorTypes, "PWR");
            case "PHWR" -> findReactor(reactorTypes, "PHWR");
            case "BWR" -> findReactor(reactorTypes, "BWR");
            case "LWGR" -> findReactor(reactorTypes, "RBMK");
            case "GCR" -> findReactor(reactorTypes, "MAGNOX");
            case "FBR" -> findReactor(reactorTypes, "BN");
            case "RBMK" -> findReactor(reactorTypes, "RBMK");
            case "VVER" -> findReactor(reactorTypes, "VVER-1200");
        }
    }

    private void findReactor(ArrayList<Reactor> reactors, String typeName) {
        for (Reactor reactor : reactors) {
            if (typeName.equals(reactor.type)) {
                this.reactor = reactor;
                break;
            }
        }
    }
}
