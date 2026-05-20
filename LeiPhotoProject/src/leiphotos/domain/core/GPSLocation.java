package leiphotos.domain.core;

import leiphotos.domain.facade.GPSCoordinates;
import leiphotos.utils.RegExpMatchable;

/**
 * Representa uma localização GPS com latitude, longitude e descrição.
 * Implementa comparação por expressão regular sobre a descrição.
 */

public record GPSLocation(double latitude, double longitude, String description)
        implements GPSCoordinates, RegExpMatchable {

    /**
     * Verifica se a descrição da localização corresponde à expressão regular.
     * 
     * @param regexp expressão regular
     * @return true se fizer match, false caso contrário
     * @requires regexp != null
     */
    @Override
    public boolean matches(String regexp) {
        return description.matches(regexp);
    }

    /**
     * Devolve uma representação textual da localização.
     * 
     * @return string no formato {Lat:x Long:y Desc:z}
     */
    @Override
    public String toString() {
        return "{Lat:" + String.format("%.2f", latitude)
                + " Long:" + String.format("%.2f", longitude)
                + " Desc:" + description + "}";
    }
}
