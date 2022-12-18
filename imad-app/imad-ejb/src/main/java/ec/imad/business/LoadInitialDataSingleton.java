package ec.imad.business;

import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import ec.imad.jpa.db.ImadDBInsert;

@Startup
@Singleton
@Local
public class LoadInitialDataSingleton {
    
    public LoadInitialDataSingleton() {
        new ImadDBInsert().insertInitialLoad();
    }
}
