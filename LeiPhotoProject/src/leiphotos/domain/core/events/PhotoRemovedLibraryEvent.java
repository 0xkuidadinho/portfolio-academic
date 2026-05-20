package leiphotos.domain.core.events;

import leiphotos.domain.core.Library;
import leiphotos.domain.facade.IPhoto;

/**
 * Evento que representa a remoção de uma fotografia de uma biblioteca.
 */
public class PhotoRemovedLibraryEvent extends PhotoLibraryEvent {

     /**
     * Cria um evento de remoção de fotografia.
     * 
     * @param photo fotografia removida
     * @param lib biblioteca onde ocorreu o evento
     * @requires photo != null
     * @requires lib != null
     */
    public PhotoRemovedLibraryEvent(IPhoto photo, Library lib) {
        super(photo, lib);
    }
}