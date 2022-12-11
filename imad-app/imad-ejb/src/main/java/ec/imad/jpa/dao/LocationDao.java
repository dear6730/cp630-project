package ec.imad.jpa.dao;

import javax.ejb.Local;

import ec.imad.jpa.model.Location;

@Local
public interface LocationDao {
    public void saveModel(Location location);
}
