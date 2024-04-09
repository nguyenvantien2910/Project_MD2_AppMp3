package business.design;

import business.entity.Singer;

public interface ISingerDesign extends IGenericDesign<Singer> {
    void searchSingerByName();
}
