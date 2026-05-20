package leiphotos.domain.core.events;

import leiphotos.domain.core.Library;
import leiphotos.domain.facade.IPhoto;

/**
 * Evento que representa uma alteração numa fotografia de uma biblioteca.
 */
public class PhotoChangedLibraryEvent extends PhotoLibraryEvent {

    /**
     * Cria um evento de alteração de fotografia.
     * 
     * @param photo fotografia alterada
     * @param lib biblioteca onde ocorreu o evento
     * @requires photo != null
     * @requires lib != null
     */
    public PhotoChangedLibraryEvent(IPhoto photo, Library lib) {
        super(photo, lib);
    }
}