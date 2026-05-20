package leiphotos.domain.controllers;

import leiphotos.domain.core.MainLibrary;
import leiphotos.domain.core.Photo;
import leiphotos.domain.core.PhotoFactory;
import leiphotos.domain.core.TrashLibrary;
import leiphotos.domain.facade.ILibrariesController;
import leiphotos.domain.facade.IPhoto;

import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Set;

/**
 * Controlador responsável pela gestão das bibliotecas de fotos
 * (principal e lixo).
 * Implementa ILibrariesController.
 */
public class LibrariesController implements ILibrariesController {

    private final MainLibrary mainLibrary;
    private final TrashLibrary trashLibrary;

    /**
     * Cria um novo LibrariesController.
     *
     * @param mainLibrary  a biblioteca principal de fotos
     * @param trashLibrary a biblioteca de lixo
     */
    public LibrariesController(MainLibrary mainLibrary, TrashLibrary trashLibrary) {
        this.mainLibrary = mainLibrary;
        this.trashLibrary = trashLibrary;
    }
    
    /**
     * Importa uma foto com o título e caminho dados.
     * Se o ficheiro não existir, imprime uma mensagem e devolve Optional vazio.
     *
     * @param title           o título da foto
     * @param pathToPhotoFile o caminho para o ficheiro da foto
     * @return um Optional com a foto importada, ou vazio se falhar
     */
    @Override
    public Optional<IPhoto> importPhoto(String title, String pathToPhotoFile) {
        try {
            Photo photo = PhotoFactory.INSTANCE.createPhoto(title, pathToPhotoFile);
            if (mainLibrary.addPhoto(photo)) {
                return Optional.of(photo);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + pathToPhotoFile +
                    " not found or could not be open");
        }
        return Optional.empty();
    }
    
    /**
     * Move as fotos selecionadas da biblioteca principal para o lixo.
     * Fotos que não existam na biblioteca principal são ignoradas.
     *
     * @param selectedPhotos as fotos a apagar
     */
    @Override
    public void deletePhotos(Set<IPhoto> selectedPhotos) {
        if (selectedPhotos != null) {
            for (IPhoto photo : selectedPhotos) {
                if (mainLibrary.deletePhoto(photo)) {
                    trashLibrary.addPhoto(photo);
                }
            }
        }
    }

    /**
     * Esvazia o lixo, apagando definitivamente todas as fotos.
     */
    @Override
    public void emptyTrash() {
        trashLibrary.deleteAll();
    }

    /**
     * Alterna o estado de favorita das fotos selecionadas
     * que existam na biblioteca principal.
     *
     * @param selectedPhotos as fotos a alternar
     */
    @Override
    public void toggleFavourite(Set<IPhoto> selectedPhotos) {
        if (selectedPhotos != null) {
            mainLibrary.toggleFavourite(selectedPhotos);
        }
    }

    /**
     * Devolve as fotos que fazem match com a expressão regular dada.
     *
     * @param regExp a expressão regular
     * @return um Iterable com as fotos que fazem match
     */
    @Override
    public Iterable<IPhoto> getMatches(String regExp) {
        return mainLibrary.getMatches(regExp);
    }

    /**
     * Devolve uma representação textual das bibliotecas.
     *
     * @return representação textual
     */
    @Override
    public String toString() {
        return mainLibrary.toString() + trashLibrary.toString();
    }
}
