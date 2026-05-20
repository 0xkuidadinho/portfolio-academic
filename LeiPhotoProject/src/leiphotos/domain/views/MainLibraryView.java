package leiphotos.domain.views;

import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.events.PhotoLibraryEvent;
import leiphotos.domain.facade.IPhoto;
import leiphotos.utils.Listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Vista sobre uma MainLibrary que usa uma cache para guardar as fotos
 * ordenadas pelo critério de ordenação corrente.
 * A cache é invalidada quando ocorrem eventos na biblioteca.
 * Implementa Listener para reagir aos eventos da biblioteca.
 * A ordenação por omissão é crescente no tamanho das fotos.
 */
public class MainLibraryView extends ALibraryView
        implements Listener<PhotoLibraryEvent> {

    private List<IPhoto> cache;
    private boolean cacheValid;

    /**
     * Cria uma nova MainLibraryView e regista-se como listener da biblioteca.
     *
     * @param library   a biblioteca principal
     * @param predicate determina quais fotos pertencem a esta vista
     */
    public MainLibraryView(MainLibrary library, Predicate<IPhoto> predicate) {
        super(library, predicate);
        this.cache = new ArrayList<>();
        this.cacheValid = false;
        library.registerListener(this);
    }

    /**
     * Reconstrói a cache a partir da biblioteca usando o predicado
     * e o comparador correntes.
     */
    private void rebuildCache() {
        cache = new ArrayList<>();
        for(IPhoto p : getLibrary().getPhotos()) {
        	if(getPredicate().test(p)) {
        		cache.add(p);
        	}
        }
        cache.sort(getComparator());
        cacheValid = true;
    }
    
    /**
     * Invalida a cache para que seja reconstruída no próximo acesso.
     */
    private void invalidateCache() {
        cacheValid = false;
    }
    
    /**
     * Define o critério de ordenação e invalida a cache.
     *
     * @param c o comparador a usar
     */
    @Override
    public void setComparator(Comparator<IPhoto> c) {
        super.setComparator(c);
        invalidateCache();
    }
    
    /**
     * Devolve o número de fotos na vista.
     * Reconstrói a cache se necessário.
     *
     * @return o número de fotos
     */
    @Override
    public int numberOfPhotos() {
        if (!cacheValid) {
            rebuildCache();
        }
        return cache.size();
    }
    
    /**
     * Devolve as fotos na vista ordenadas pelo critério corrente.
     * Reconstrói a cache se necessário.
     *
     * @return a lista de fotos não modificável
     */
    @Override
    public List<IPhoto> getPhotos() {
        if (!cacheValid) {
            rebuildCache();
        }
        return Collections.unmodifiableList(new ArrayList<>(cache));
    }
    
    /**
     * Devolve as fotos na vista que fazem match com a expressão
     * regular dada. Reconstrói a cache se necessário.
     *
     * @param regexp a expressão regular
     * @return as fotos que fazem match
     */
    @Override
    public List<IPhoto> getMatches(String regexp) {
        if (!cacheValid) {
            rebuildCache();
        }
        List <IPhoto> resultado = new ArrayList<>();
        for(IPhoto p : cache) {
        	if(p.matches(regexp)) {
        		resultado.add(p);
        	}
        }
        return resultado;
    }

    /**
     * Processa um evento da biblioteca invalidando a cache.
     *
     * @param event o evento da biblioteca
     */
    @Override
    public void processEvent(PhotoLibraryEvent event) {
        invalidateCache();
    }
}
