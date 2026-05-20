package leiphotos.domain.views;

import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.TrashLibrary;
import leiphotos.domain.facade.ViewsType;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

/**
 * Catálogo de vistas de bibliotecas indexado por ViewsType.
 * Cria todas as vistas padrão no construtor.
 */
public class ViewsCatalog implements IViewsCatalog {

    private final Map<ViewsType, ILibraryView> views;

    /**
     * Cria um ViewsCatalog com todas as vistas padrão.
     *
     * @param mainLib  a biblioteca principal de fotos
     * @param trashLib a biblioteca de lixo
     */
    public ViewsCatalog(MainLibrary mainLib, TrashLibrary trashLib) {
        this.views = new EnumMap<>(ViewsType.class);
        views.put(ViewsType.ALL_MAIN,
                new MainLibraryView(mainLib, p -> true));
        views.put(ViewsType.ALL_TRASH,
                new TrashLibraryView(trashLib));
        views.put(ViewsType.FAVOURITES,
                new MainLibraryView(mainLib, p -> p.isFavourite()));
        views.put(ViewsType.MOST_RECENT,
                new MainLibraryView(mainLib, p -> {
                    LocalDateTime capturedDate = p.capturedDate();
                    if (capturedDate != null) {
                        LocalDateTime twelveMonthsAgo =
                                LocalDateTime.now().minusMonths(12);
                        return capturedDate.isAfter(twelveMonthsAgo);
                    }
                    return false;
                }));
    }
    
    /**
     * Devolve a vista do tipo dado.
     *
     * @param t o tipo de vista pretendido
     * @return a vista, ou null se não existir
     */
    @Override
    public ILibraryView getView(ViewsType t) {
        return views.get(t);
    }
    
    /**
     * Devolve uma representação textual do catálogo de vistas
     * com o número de fotos e os ficheiros de cada vista.
     *
     * @return representação textual
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("***** VIEWS *****\n");
        for (ViewsType type : ViewsType.values()) {
            ILibraryView view = views.get(type);
            if (view != null) {
                sb.append("***** VIEW ").append(type).append(": ")
                  .append(view.numberOfPhotos()).append(" photos *****\n");
                for (var p : view.getPhotos()) {
                    sb.append(p.file()).append("\n");
                }
            }
        }
        return sb.toString();
    }
}
