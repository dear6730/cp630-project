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

@MessageDriven(name = "addData", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "topic/test"),
})
public class StatsMDBAddData implements MessageListener {
	private static final Logger LOGGER = Logger.getLogger(StatsMDBAddData.class);
	
    @EJB
    StatsSingleton statsSingleton;

    public void onMessage(Message message) {
        try {
			double dNumber = ((TextMessage) message).getDoubleProperty("number");
			LOGGER.info("Action Add number consumer received at testTopic: " + dNumber);
			statsSingleton.addData(dNumber);
        } catch (JMSException e) {
            e.printStackTrace();
			LOGGER.error(e);
        }
    }
}