package tech.itpark.project_delivery_web.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum Category {
    FOOD(1.35);

    private static final Map<Double, Category> BY_COEFFICIENT = new HashMap<>();

    static {
        for (Category c : values()) {
            BY_COEFFICIENT.put(c.coefficient, c);
        }
    }

    public final Double coefficient;

    Category(Double coefficient) {
        this.coefficient = coefficient;
    }

    public static Category valueOfCoefficient(Long coefficient){
        return BY_COEFFICIENT.get(coefficient);
    }
}
