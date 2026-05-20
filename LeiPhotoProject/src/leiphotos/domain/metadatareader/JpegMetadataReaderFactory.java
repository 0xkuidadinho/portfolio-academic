package leiphotos.domain.metadatareader;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Fábrica singleton de leitores de metadados JPEG.
 * Esta classe é responsável por criar o leitor de metadados
 * apropriado para um dado ficheiro.
 */
public class JpegMetadataReaderFactory {

     /**
     * Única instância da fábrica.
     */
    public static final JpegMetadataReaderFactory INSTANCE = new JpegMetadataReaderFactory();

    /**
     * Construtor privado para garantir o padrão singleton.
     */
    private JpegMetadataReaderFactory() {
    }

     /**
     * Cria um leitor de metadados para o ficheiro dado.
     * 
     * @param file ficheiro JPEG
     * @return leitor de metadados
     * @throws JpegMetadataException se ocorrer erro na leitura dos metadados
     * @throws FileNotFoundException se o ficheiro não existir
     * @requires file != null
     */
    public JpegMetadataReader createMetadataReader(File file)
            throws JpegMetadataException, FileNotFoundException {
        return new JavaXTMetadataReaderAdapter(file);
    }
}