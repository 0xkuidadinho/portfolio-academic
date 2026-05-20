package leiphotos.domain.metadatareader;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import leiphotos.services.JavaXTJpegMetadataReader;
/**
 * Adaptador entre a interface JpegMetadataReader e a classe
 * JavaXTJpegMetadataReader fornecida pelo pacote services.
 * Converte a informação lida para o formato usado no domínio.
 */
public class JavaXTMetadataReaderAdapter implements JpegMetadataReader {

    private JavaXTJpegMetadataReader reader;

    /**
     * Cria um adaptador para leitura de metadados do ficheiro dado.
     * 
     * @param file ficheiro JPEG
     * @throws JpegMetadataException se ocorrer erro na leitura dos metadados
     * @throws FileNotFoundException se o ficheiro não existir
     * @requires file != null
     */
    public JavaXTMetadataReaderAdapter(File file) throws JpegMetadataException, FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException();
        }

        try {
            reader = new JavaXTJpegMetadataReader(file);
        } catch (Exception e) {
            throw new JpegMetadataException("Error reading jpeg metadata", e);
        }
    }

    /**
     * Devolve o modelo da câmara.
     * 
     * @return modelo da câmara
     */
    @Override
    public String getCamera() {
        String camera = reader.getCamera();
        return camera == null ? "" : camera;
    }

    /**
     * Devolve o fabricante da câmara.
     * 
     * @return fabricante da câmara
     */
    @Override
    public String getManufacturer() {
        String manufacturer = reader.getManufacturer();
        return manufacturer == null ? "" : manufacturer;
    }

    /**
     * Devolve a data em que a fotografia foi tirada.
     * 
     * @return data da fotografia
     */
    @Override
    public LocalDateTime getDate() {
        String date = reader.getDate();

        if (date == null || date.isBlank()) {
            return LocalDateTime.of(1970, 1, 1, 0, 0);
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
            return LocalDateTime.parse(date, formatter);
        } catch (Exception e) {
            return LocalDateTime.of(1970, 1, 1, 0, 0);
        }
    }

    /**
     * Devolve a abertura da câmara.
     * 
     * @return abertura
     */
    @Override
    public String getAperture() {
        String aperture = reader.getAperture();
        return aperture == null ? "" : aperture;
    }

    /**
     * Devolve a localização GPS da fotografia.
     * 
     * @return array com longitude e latitude, ou null se não existir
     */
    @Override
    public double[] getGpsLocation() {
        return reader.getGPS();
    }
}
