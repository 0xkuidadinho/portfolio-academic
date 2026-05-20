package leiphotos.domain.controllers;

import leiphotos.domain.albums.IAlbum;
import leiphotos.domain.albums.IAlbumsCatalog;
import leiphotos.domain.facade.IAlbumsController;
import leiphotos.domain.facade.IPhoto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Controlador responsável pela gestão das interações com os álbuns.
 * Mantém um álbum atualmente selecionado para as operações.
 * Implementa IAlbumsController.
 */
public class AlbumsController implements IAlbumsController {

    private final IAlbumsCatalog albumsCatalog;
    private String selectedAlbumName;

    /**
     * Cria um novo AlbumsController.
     *
     * @param albumsCatalog o catálogo de álbuns
     */
    public AlbumsController(IAlbumsCatalog albumsCatalog) {
        this.albumsCatalog = albumsCatalog;
        this.selectedAlbumName = null;
    }

    /**
     * Cria um novo álbum com o nome dado.
     *
     * @param name o nome do álbum
     * @return true se o álbum foi criado
     */
    @Override
    public boolean createAlbum(String name) {
        return albumsCatalog.createAlbum(name);
    }

    /**
     * Cria um novo smart album com o nome e critério dados.
     *
     * @param name     o nome do álbum
     * @param criteria o predicado de seleção automática
     * @return true se o álbum foi criado
     */
    @Override
    public boolean createSmartAlbum(String name, Predicate<IPhoto> criteria) {
        return albumsCatalog.createSmartAlbum(name, criteria);
    }

    /**
     * Remove o álbum atualmente selecionado.
     * Após a remoção, nenhum álbum fica selecionado.
     */
    @Override
    public void removeAlbum() {
        if (selectedAlbumName != null) {
            albumsCatalog.deleteAlbum(selectedAlbumName);
            selectedAlbumName = null;
        }
    }

    /**
     * Seleciona o álbum com o nome dado, se existir.
     *
     * @param name o nome do álbum a selecionar
     */
    @Override
    public void selectAlbum(String name) {
        if (name != null && albumsCatalog.containsAlbum(name)) {
            selectedAlbumName = name;
        }
    }

    /**
     * Adiciona as fotos dadas ao álbum atualmente selecionado.
     * Se nenhum álbum estiver selecionado, não faz nada.
     *
     * @param selectedPhotos as fotos a adicionar
     */
    @Override
    public void addPhotos(Set<IPhoto> selectedPhotos) {
        if (selectedAlbumName != null) {
            IAlbum album = albumsCatalog.getAlbum(selectedAlbumName);
            if (album != null) {
                album.addPhotos(selectedPhotos);
            }
        }
    }

    /**
     * Remove as fotos dadas do álbum atualmente selecionado.
     * Se nenhum álbum estiver selecionado, não faz nada.
     *
     * @param selectedPhotos as fotos a remover
     */
    @Override
    public void removePhotos(Set<IPhoto> selectedPhotos) {
        if (selectedAlbumName != null) {
            IAlbum album = albumsCatalog.getAlbum(selectedAlbumName);
            if (album != null) {
                album.removePhotos(selectedPhotos);
            }
        }
    }

    /**
     * Devolve as fotos do álbum atualmente selecionado.
     * Se nenhum álbum estiver selecionado, devolve lista vazia.
     *
     * @return a lista de fotos
     */
    @Override
    public List<IPhoto> getPhotos() {
        if (selectedAlbumName != null) {
            IAlbum album = albumsCatalog.getAlbum(selectedAlbumName);
            if (album != null) {
                return album.getPhotos();
            }
        }
        return Collections.emptyList();
    }

    /**
     * Devolve o nome do álbum atualmente selecionado, se existir.
     *
     * @return Optional com o nome, ou vazio se nenhum estiver selecionado
     */
    @Override
    public Optional<String> getSelectedAlbum() {
        return Optional.ofNullable(selectedAlbumName);
    }

    /**
     * Devolve os nomes de todos os álbuns existentes.
     *
     * @return o conjunto de nomes
     */
    @Override
    public Set<String> getAlbumNames() {
        return albumsCatalog.getAlbumNames();
    }

    /**
     * Devolve uma representação textual dos álbuns.
     *
     * @return representação textual
     */
    @Override
    public String toString() {
        return albumsCatalog.toString();
    }
}
