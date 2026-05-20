package leiphotos.domain.views;

import leiphotos.domain.core.Library;
import leiphotos.domain.facade.IPhoto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Implementação esqueleto abstrata de ILibraryView.
 * Baseia-se num predicado que determina quais fotos pertencem à vista,
 * numa referência à biblioteca e num comparador para ordenação.
 * A ordenação por omissão é crescente no tamanho das fotos.
 */
public abstract class ALibraryView implements ILibraryView {

    private final Library library;
    private final Predicate<IPhoto> predicate;
    private Comparator<IPhoto> comparator;

    /**
     * Cria uma nova ALibraryView.
     *
     * @param library   a biblioteca a que a vista se refere
     * @param predicate determina quais fotos pertencem a esta vista
     */
    protected ALibraryView(Library library, Predicate<IPhoto> predicate) {
        this.library = library;
        this.predicate = predicate;
        this.comparator = Comparator.comparingLong(IPhoto::size);
    }
    
    /**
     * Devolve a biblioteca associada a esta vista.
     *
     * @return a biblioteca
     */
    protected Library getLibrary() {
        return library;
    }

    /**
     * Devolve o predicado usado para filtrar as fotos.
     *
     * @return o predicado
     */
    protected Predicate<IPhoto> getPredicate() {
        return predicate;
    }
    
    /**
     * Devolve o comparador corrente.
     *
     * @return o comparador
     */
    protected Comparator<IPhoto> getComparator() {
        return comparator;
    }
    
    /**
     * Define o critério de ordenação usado na apresentação das fotos.
     *
     * @param c o comparador a usar
     */
    @Override
    public void setComparator(Comparator<IPhoto> c) {
        this.comparator = c;
    }
    
    /**
     * Devolve o número de fotos que pertencem a esta vista.
     *
     * @return o número de fotos
     */
    @Override
    public int numberOfPhotos() {
        int count = 0;
        for (IPhoto p : library.getPhotos()) {
            if (predicate.test(p)) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Devolve as fotos na vista pela ordem determinada pelo
     * critério de ordenação corrente.
     *
     * @return a lista de fotos ordenada
     */
    @Override
    public List<IPhoto> getPhotos() {
        List<IPhoto> result = new ArrayList<>();
        for (IPhoto p : library.getPhotos()) {
            if (predicate.test(p)) {
                result.add(p);
            }
        }
        result.sort(comparator);
        return result;
    }
    
    /**
     * Devolve as fotos na vista que fazem match com a expressão
     * regular dada, pela ordem determinada pelo critério de
     * ordenação corrente.
     *
     * @param regexp a expressão regular
     * @return as fotos que fazem match
     */
    @Override
    public List<IPhoto> getMatches(String regexp) {
        List<IPhoto> result = new ArrayList<>();
        for (IPhoto p : library.getPhotos()) {
            if (predicate.test(p) && p.matches(regexp)) {
                result.add(p);
            }
        }
        result.sort(comparator);
        return result;
    }
}
