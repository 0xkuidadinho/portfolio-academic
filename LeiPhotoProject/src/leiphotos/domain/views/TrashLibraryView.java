package leiphotos.domain.views;

import leiphotos.domain.core.TrashLibrary;

/**
 * Vista sobre uma TrashLibrary. Mostra todas as fotos no lixo.
 */
public class TrashLibraryView extends ALibraryView {

    /**
     * Cria uma nova TrashLibraryView.
     *
     * @param library a biblioteca de lixo
     */
    public TrashLibraryView(TrashLibrary library) {
        super(library, p -> true);
    }
}
