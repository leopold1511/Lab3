package Lab3;

import Lab3.Reactor.ReactorDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Calculator {

    public static void calculateFuelLoad(ArrayList<ReactorDB> reactors) {

        for (ReactorDB reactor : reactors) {
            int year = 2014;
            while (year < 2025) {
                double fuelLoad = 0;
                if (reactor.getLoadFactor().containsKey(year)) {
                    fuelLoad = reactor.getThermalCapacity() * reactor.getLoadFactor().get(year) / 100 / reactor.getReactorClass().burnup;
                } else if (reactor.getShutdownYear() >= year & reactor.getConnectionYear() <= year) {
                    fuelLoad = (double) (reactor.getThermalCapacity() * 85) / 100 / reactor.getReactorClass().burnup;
                }
                reactor.getFuelLoad().put(year, fuelLoad);
                year++;
            }
        }
    }

    public static Map<String, Map<Integer, Double>> aggregateByOperator(ArrayList<ReactorDB> reactors) {
        Map<String, Map<Integer, Double>> map = new HashMap<>();
        for (ReactorDB reactor : reactors) {
            if (map.containsKey(reactor.getOperator())) {
                Map<Integer, Double> fuelLoad = map.get(reactor.getOperator());
                for (int year = 2014; year < 2025; year++) {
                    fuelLoad.put(year, reactor.getFuelLoad().get(year) + fuelLoad.get(year));
                }
            } else {
                map.put(reactor.getOperator(), new HashMap<>());
                Map<Integer, Double> fuelLoad = map.get(reactor.getOperator());
                fuelLoad.putAll(reactor.getFuelLoad());
            }
        }
        return map;
    }

    public static Map<String, Map<Integer, Double>> aggregateByCountry(ArrayList<ReactorDB> reactors) {
        Map<String, Map<Integer, Double>> map = new HashMap<>();
        for (ReactorDB reactor : reactors) {
            if (map.containsKey(reactor.getCountry())) {
                Map<Integer, Double> fuelLoad = map.get(reactor.getCountry());
                for (int year = 2014; year < 2025; year++) {
                    fuelLoad.put(year, reactor.getFuelLoad().get(year) + fuelLoad.get(year));
                }
            } else {
                map.put(reactor.getCountry(), new HashMap<>());
                Map<Integer, Double> fuelLoad = map.get(reactor.getCountry());
                fuelLoad.putAll(reactor.getFuelLoad());
            }
        }
        return map;
    }

    public static Map<String, Map<Integer, Double>> aggregateByRegion(ArrayList<ReactorDB> reactors) {
        Map<String, Map<Integer, Double>> map = new HashMap<>();
        for (ReactorDB reactor : reactors) {
            if (map.containsKey(reactor.getRegion())) {
                Map<Integer, Double> fuelLoad = map.get(reactor.getRegion());
                for (int year = 2014; year < 2025; year++) {
                    fuelLoad.put(year, reactor.getFuelLoad().get(year) + fuelLoad.get(year));
                }
            } else {
                map.put(reactor.getRegion(), new HashMap<>());
                Map<Integer, Double> fuelLoad = map.get(reactor.getRegion());
                fuelLoad.putAll(reactor.getFuelLoad());
            }
        }
        return map;
    }
}
