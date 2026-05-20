package leiphotos.domain.controllers;

import leiphotos.domain.facade.IPhoto;
import leiphotos.domain.facade.IViewsController;
import leiphotos.domain.facade.ViewsType;
import leiphotos.domain.views.ILibraryView;
import leiphotos.domain.views.IViewsCatalog;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Controlador responsável pela gestão das interações com as vistas.
 * Implementa IViewsController.
 */
public class ViewsController implements IViewsController {

    private final IViewsCatalog viewsCatalog;

    /**
     * Cria um novo ViewsController.
     *
     * @param viewsCatalog o catálogo de vistas
     */
    public ViewsController(IViewsCatalog viewsCatalog) {
        this.viewsCatalog = viewsCatalog;
    }

    /**
     * Devolve as fotos da vista do tipo dado, ordenadas pelo
     * critério de ordenação corrente da vista.
     *
     * @param viewType o tipo de vista
     * @return a lista de fotos
     */
    @Override
    public List<IPhoto> getPhotos(ViewsType viewType) {
        ILibraryView view = viewsCatalog.getView(viewType);
        return view != null ? view.getPhotos() : Collections.emptyList();
    }

    /**
     * Devolve as fotos da vista que fazem match com a expressão regular,
     * ordenadas pelo critério de ordenação corrente.
     *
     * @param viewType o tipo de vista
     * @param regexp   a expressão regular
     * @return a lista de fotos que fazem match
     */
    @Override
    public List<IPhoto> getMatches(ViewsType viewType, String regexp) {
        ILibraryView view = viewsCatalog.getView(viewType);
        return view != null ? view.getMatches(regexp) : Collections.emptyList();
    }

    /**
     * Define o critério de ordenação para a vista do tipo dado.
     *
     * @param v        o tipo de vista
     * @param criteria o comparador a usar na ordenação
     */
    @Override
    public void setSortingCriteria(ViewsType v, Comparator<IPhoto> criteria) {
        ILibraryView view = viewsCatalog.getView(v);
        if (view != null) {
            view.setComparator(criteria);
        }
    }

    /**
     * Devolve uma representação textual das vistas.
     *
     * @return representação textual
     */
    @Override
    public String toString() {
        return viewsCatalog.toString();
    }
}
