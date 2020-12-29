package config;

import java.util.HashMap;
import java.util.Map;

public enum Behaviour {
    IMPULSIVO(0), EXIGENTE(1), CAUTELOSO(2), ALEATORIO(3);

    private int value;
    private static Map<Integer, Behaviour> map = new HashMap<Integer, Behaviour>();

    private Behaviour(int value) {
        this.value = value;
    }

    static {
        for (Behaviour behaviour : Behaviour.values()) {
            map.put(behaviour.value, behaviour);
        }
    }

    public static Behaviour valueOf(int behaviour) {
        return (Behaviour) map.get(behaviour);
    }

    public int getValue() {
        return value;
    }
}
