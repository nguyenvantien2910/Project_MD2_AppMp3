package business.design;

import business.entity.Song;

public interface ISongDesign extends IGenericDesign<Song>{
    void searchByName();
}
