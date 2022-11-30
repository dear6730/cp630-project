package ec.stats.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import ec.stats.sb.StatsSingleton;

import org.jboss.logging.Logger;

@MessageDriven(name = "testQueue", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/test"),
})
public class StatsMDBSaveModel implements MessageListener {
	private static final Logger LOGGER = Logger.getLogger(StatsMDBSaveModel.class);
	
    @EJB
    StatsSingleton statsSingleton;

    public void onMessage(Message message) {
        try {
			String sMessage = ((TextMessage) message).getText();
            LOGGER.info("Action message consumer received : " + sMessage);
			if(sMessage.equals("save")){
				LOGGER.info("Calling saveModel");
				statsSingleton.saveModel();
			}
        } catch (JMSException e) {
            e.printStackTrace();
			LOGGER.error(e);
        }
    }
}