package leiphotos.domain.core;

import leiphotos.domain.facade.IPhoto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação esqueleto abstrata de TrashLibrary.
 * Deixa em aberto o critério de limpeza através dos métodos
 * abstratos clean() e cleaningTime().
 */
public abstract class ATrashLibrary implements TrashLibrary {

    private final List<IPhoto> trashedPhotos;

    /**
     * Cria uma nova biblioteca de lixo vazia.
     */
    protected ATrashLibrary() {
        this.trashedPhotos = new ArrayList<>();
    }

    /**
     * Efetua a limpeza das fotos expiradas.
     */
    protected abstract void clean();

    /**
     * Determina se é altura de efetuar a verificação de limpeza.
     *
     * @return true se a limpeza deve ser verificada
     */
    protected abstract boolean cleaningTime();

    /**
     * Devolve o número de fotos no lixo.
     *
     * @return o número de fotos
     */
    protected List<IPhoto> getTrashedPhotos() {
        return trashedPhotos;
    }

    /**
     * Adiciona uma foto ao lixo, se ainda não existir.
     *
     * @param photo a foto a adicionar
     * @return true se a foto foi adicionada
     */
    @Override
    public int getNumberOfPhotos() {
        return trashedPhotos.size();
    }

    /**
     * Remove uma foto do lixo.
     *
     * @param photo a foto a remover
     * @return true se a foto foi removida
     */
    @Override
    public boolean addPhoto(IPhoto photo) {
        if (photo != null && !trashedPhotos.contains(photo)) {
            trashedPhotos.add(photo);
            return true;
        }
        return false;
    }

    /**
     * Devolve as fotos no lixo, efetuando limpeza se apropriado.
     *
     * @return a coleção de fotos no lixo
     */
    @Override
    public boolean deletePhoto(IPhoto photo) {
        return trashedPhotos.remove(photo);
    }

    /**
     * Returns the photos in the trash, performing cleaning if appropriate.
     *
     * @return the collection of trashed photos
     */
    @Override
    public Collection<IPhoto> getPhotos() {
        if (cleaningTime()) {
            clean();
        }
        return Collections.unmodifiableList(new ArrayList<>(trashedPhotos));
    }

    @Override
    public Collection<IPhoto> getMatches(String regexp) {
        return trashedPhotos.stream()
                .filter(p -> p.matches(regexp))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteAll() {
        if (trashedPhotos.isEmpty()) {
            return false;
        }
        trashedPhotos.clear();
        return true;
    }

    @Override
    public String toString() {
        // Trigger cleaning before displaying
        if (cleaningTime()) {
            clean();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("***** TRASH PHOTO LIBRARY: ")
          .append(trashedPhotos.size()).append(" photos ****\n");
        List<IPhoto> sorted = new ArrayList<>(trashedPhotos);
        sorted.sort((a, b) -> a.file().getPath().compareTo(b.file().getPath()));
        for (IPhoto p : sorted) {
            sb.append(p.toString()).append("\n");
        }
        return sb.toString();
    }
}
