package leiphotos.domain.albums;

import leiphotos.domain.core.MainLibrary;

/**
 * Representa um álbum normal de fotos.
 */
public class Album extends AAlbum {

    /**
     * Cria um novo álbum com o nome dado.
     *
     * @param name    o nome do álbum
     * @param library a biblioteca principal
     */
    public Album(String name, MainLibrary library) {
        super(name, library);
    }
}
