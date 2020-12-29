package config;

import java.util.HashMap;
import java.util.Map;

public enum Behavior {
    IMPULSIVO(0), EXIGENTE(1), CAUTELOSO(2), ALEATORIO(3);

    private int value;
    private static Map<Integer, Behavior> map = new HashMap<Integer, Behavior>();

    private Behavior(int value) {
        this.value = value;
    }

    static {
        for (Behavior behaviour : Behavior.values()) {
            map.put(behaviour.value, behaviour);
        }
    }

    public static Behavior valueOf(int behaviour) {
        return (Behavior) map.get(behaviour);
    }

    public int getValue() {
        return value;
    }
}
