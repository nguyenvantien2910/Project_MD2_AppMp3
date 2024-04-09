package business.design;

import business.entity.Album;

public interface IAlbumDesign extends IGenericDesign<Album>{
    void searchAlbumByName();
}
