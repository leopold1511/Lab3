package Lab3;

import Lab3.Reactor.ReactorDB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Calculator {
    private static final int START_YEAR = 2014;
    private static final int END_YEAR = 2025;
    private static final double DEFAULT_LOAD_FACTOR = 85;

    public static void calculateFuelLoadsForReactors(List<ReactorDB> reactors) {
        for (ReactorDB reactor : reactors) {
            int year = START_YEAR;
            while (year < END_YEAR) {
                double annualFuelLoad = 0;
                if (reactor.getLoadFactor().containsKey(year)) {
                    annualFuelLoad = reactor.getThermalCapacity() * reactor.getLoadFactor().get(year) / 100 / reactor.getReactor().burnup;
                } else if (reactor.getShutdownYear() >= year && reactor.getConnectionYear() <= year) {
                    annualFuelLoad = (double) (reactor.getThermalCapacity() * DEFAULT_LOAD_FACTOR) / 100 / reactor.getReactor().burnup;
                }
                reactor.getFuelLoad().put(year, annualFuelLoad);
                year++;
            }
        }
    }

    public static <K> Map<K, Map<Integer, Double>> aggregateFuelLoads(List<ReactorDB> reactors, Function<ReactorDB, K> keyExtractor) {
        Map<K, Map<Integer, Double>> map = new HashMap<>();
        for (ReactorDB reactor : reactors) {
            K key = keyExtractor.apply(reactor);
            if (map.containsKey(key)) {
                Map<Integer, Double> fuelLoad = map.get(key);
                for (int year = START_YEAR; year < END_YEAR; year++) {
                    fuelLoad.put(year, reactor.getFuelLoad().get(year) + fuelLoad.get(year));
                }
            } else {
                map.put(key, new HashMap<>());
                Map<Integer, Double> fuelLoad = map.get(key);
                fuelLoad.putAll(reactor.getFuelLoad());
            }
        }
        return map;
    }

    public static Map<String, Map<Integer, Double>> aggregateByOperator(List<ReactorDB> reactors) {
        return aggregateFuelLoads(reactors, ReactorDB::getOperator);
    }

    public static Map<String, Map<Integer, Double>> aggregateByCountry(List<ReactorDB> reactors) {
        return aggregateFuelLoads(reactors, ReactorDB::getCountry);
    }

    public static Map<String, Map<Integer, Double>> aggregateByRegion(List<ReactorDB> reactors) {
        return aggregateFuelLoads(reactors, ReactorDB::getRegion);
    }
}
