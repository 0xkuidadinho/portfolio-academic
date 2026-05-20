package leiphotos.domain.albums;

import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.facade.IPhoto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Catálogo de álbuns que usa um mapa.
 * Cada álbum está associado à biblioteca principal.
 */
public class AlbumsCatalog implements IAlbumsCatalog {

    private final MainLibrary library;
    private final Map<String, IAlbum> albums;

    /**
     * Cria um novo catálogo de álbuns.
     *
     * @param library a biblioteca principal
     */
    public AlbumsCatalog(MainLibrary library) {
        this.library = library;
        this.albums = new LinkedHashMap<>();
    }
    
    /**
     * Cria um novo álbum com o nome dado, se não existir.
     *
     * @param albumName o nome do álbum
     * @return true se o álbum foi criado
     */
    @Override
    public boolean createAlbum(String albumName) {
        if (albumName != null && !albums.containsKey(albumName)) {
            albums.put(albumName, new Album(albumName, library));
            return true;
        }
        return false;
    }

    /**
     * Cria um novo smart album com o nome e critério dados.
     * Nota: a implementação completa faz parte da segunda parte do trabalho.
     *
     * @param albumName o nome do álbum
     * @param criteria  o predicado de seleção automática
     * @return true se o álbum foi criado
     */
    @Override
    public boolean createSmartAlbum(String albumName, Predicate<IPhoto> criteria) {
        if (albumName != null && !albums.containsKey(albumName)) {
            albums.put(albumName, new Album(albumName, library));
            return true;
        }
        return false;
    }

    /**
     * Remove o álbum com o nome dado, se existir.
     *
     * @param albumName o nome do álbum a remover
     * @return true se o álbum foi removido
     */
    @Override
    public boolean deleteAlbum(String albumName) {
        return albums.remove(albumName) != null;
    }

    /**
     * Indica se existe um álbum com o nome dado.
     *
     * @param albumName o nome a verificar
     * @return true se existir
     */
    @Override
    public boolean containsAlbum(String albumName) {
        return albums.containsKey(albumName);
    }

    /**
     * Devolve o álbum com o nome dado, ou null se não existir.
     *
     * @param albumName o nome do álbum
     * @return o álbum, ou null
     */
    @Override
    public IAlbum getAlbum(String albumName) {
        return albums.get(albumName);
    }

    /**
     * Devolve os nomes de todos os álbuns existentes.
     *
     * @return o conjunto de nomes
     */
    @Override
    public Set<String> getAlbumNames() {
        return Collections.unmodifiableSet(albums.keySet());
    }

    /**
     * Devolve uma representação textual do catálogo com todos os álbuns.
     *
     * @return representação textual
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("***** ALBUMS *****\n");
        for (IAlbum album : albums.values()) {
            sb.append(album.toString());
        }
        return sb.toString();
    }
}
