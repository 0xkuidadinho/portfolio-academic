package leiphotos.domain.core.events;

import leiphotos.domain.core.Library;
import leiphotos.domain.facade.IPhoto;

/**
 * Evento que representa a adição de uma fotografia a uma biblioteca.
 */
public class PhotoAddedLibraryEvent extends PhotoLibraryEvent {

    /**
     * Cria um evento de adição de fotografia.
     * 
     * @param photo fotografia adicionada
     * @param lib biblioteca onde ocorreu o evento
     * @requires photo != null
     * @requires lib != null
     */
    public PhotoAddedLibraryEvent(IPhoto photo, Library lib) {
        super(photo, lib);
    }
}