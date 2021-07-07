package tech.itpark.project_delivery_web.service;

import org.springframework.stereotype.Component;

import static java.lang.Math.*;

@Component
public class DeliveryUtil {
    private static final int EARTH_RADIUS = 6372795;

    public double getDistance(double lon1, double lat1, double lon2, double lat2) {

        lon1 = lon1 * Math.PI / 180;
        lat1 = lat1 * Math.PI / 180;
        lon2 = lon2 * Math.PI / 180;
        lat2 = lat2 * Math.PI / 180;

        double sin1 = sin((lat1 - lat2) / 2);
        double sin2 = sin((lon1 - lon2) / 2);

        return 2 * EARTH_RADIUS * asin(sqrt(sin1 * sin1 + sin2 * sin2 * cos(lat1) * cos(lat2)));

    }
}
