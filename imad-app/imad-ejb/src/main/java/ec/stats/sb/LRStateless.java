package ec.stats.sb;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import ec.stats.jpa.ModelDao;
import ec.stats.jpa.model.Model;
import weka.classifiers.Classifier;
import weka.core.DenseInstance;

/**
 * Session Bean implementation class LRStateless
 */
@Stateless
@LocalBean
public class LRStateless implements LRStatelessLocal {
    private static final Logger LOGGER = Logger.getLogger(LRStateless.class);
    
    @EJB
    private ModelDao modelDao;

    public LRStateless() {
    }
	

    @Override
    public double prediction(String modelName, double [] arrayUnknownParams) {
        LOGGER.info("prediction method is called");
        double result = 0;
        try {
            Model model = modelDao.getModel(modelName);
            if(model != null){
                byte[] buf = model.getObject();
                ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
                Classifier cls = (Classifier) objectIn.readObject();

                if(cls != null){
                    // predict
                    LOGGER.info("Processing \"Prediction\" using the unknown parameters : " + Arrays.toString(arrayUnknownParams));
                    DenseInstance unknownDataInstance = new DenseInstance(1.0, arrayUnknownParams);
                    result = cls.classifyInstance(unknownDataInstance);
                    LOGGER.info("\"Prediction\" : " + result);
                }
            }
        } catch (IOException e) {
            LOGGER.error("IOException", e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("ClassNotFoundException", e);
        } catch (Exception e) {
            LOGGER.error("Exception", e);
        }
        return result;
    }					
}