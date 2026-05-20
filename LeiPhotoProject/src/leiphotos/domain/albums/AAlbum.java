package leiphotos.domain.albums;

import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.events.PhotoLibraryEvent;
import leiphotos.domain.core.events.PhotoRemovedLibraryEvent;
import leiphotos.domain.facade.IPhoto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementação esqueleto abstrata de IAlbum.
 * Fornece implementações por omissão de todos os métodos de IAlbum.
 * Escuta os eventos da biblioteca para remover fotos que sejam
 * apagadas da biblioteca.
 */
public abstract class AAlbum implements IAlbum {

    private final String name;
    private final MainLibrary library;
    private final Set<IPhoto> photos;

    /**
     * Cria um novo álbum associado à biblioteca dada.
     *
     * @param name    o nome do álbum
     * @param library a biblioteca principal
     */
    public AAlbum(String name, MainLibrary library) {
        this.name = name;
        this.library = library;
        this.photos = new LinkedHashSet<>();
        library.registerListener(this);
    }
    
    /**
     * Devolve o nome deste álbum.
     *
     * @return o nome do álbum
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Devolve a biblioteca associada a este álbum.
     *
     * @return a biblioteca principal
     */
    protected MainLibrary getLibrary() {
        return library;
    }

    /**
     * Devolve o conjunto interno de fotos (para uso pelas subclasses).
     *
     * @return o conjunto mutável de fotos
     */
    protected Set<IPhoto> getPhotosSet() {
        return photos;
    }

    /**
     * Devolve o número de fotos neste álbum.
     *
     * @return o número de fotos
     */
    @Override
    public int numberOfPhotos() {
        return photos.size();
    }
    
    /**
     * Adiciona ao álbum as fotos dadas que existam na biblioteca.
     *
     * @param photosToAdd as fotos a adicionar
     * @return true se alguma foto foi adicionada
     */
    @Override
    public boolean addPhotos(Set<IPhoto> photosToAdd) {
        if (photosToAdd == null) {
            return false;
        }
        boolean changed = false;
        for (IPhoto p : photosToAdd) {
            if (library.getPhotos().contains(p)) {
                changed |= photos.add(p);
            }
        }
        return changed;
    }

    /**
     * Remove do álbum as fotos dadas.
     *
     * @param photosToRemove as fotos a remover
     * @return true se alguma foto foi removida
     */
    @Override
    public boolean removePhotos(Set<IPhoto> photosToRemove) {
        if (photosToRemove == null) {
            return false;
        }
        return photos.removeAll(photosToRemove);
    }

    /**
     * Devolve as fotos do álbum ordenadas por tamanho crescente.
     *
     * @return a lista de fotos ordenada
     */
    public List<IPhoto> getPhotos() {
        List<IPhoto> result = new ArrayList<>(photos);
        result.sort(Comparator.comparingLong(IPhoto::size));
        return Collections.unmodifiableList(result);
    }

    /**
     * Processa eventos da biblioteca. Quando uma foto é removida
     * da biblioteca, é também removida deste álbum.
     *
     * @param e o evento da biblioteca
     */
    @Override
    public void processEvent(PhotoLibraryEvent e) {
        if (e instanceof PhotoRemovedLibraryEvent) {
            photos.remove(e.getPhoto());
        }
    }
    
    /**
     * Devolve uma representação textual do álbum com o nome,
     * o número de fotos e os ficheiros de cada foto.
     *
     * @return representação textual
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("***** Album ").append(name).append(": ")
          .append(numberOfPhotos()).append(" photos *****\n");
        for (IPhoto p : getPhotos()) {
            sb.append(p.file()).append("\n");
        }
        return sb.toString();
    }
}
