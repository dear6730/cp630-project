package ec.stats.mdb;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.jboss.logging.Logger;

@Stateless
@LocalBean
public class StatsJMSStateless implements StatsJMSStatelessLocal {
	private static final Logger LOGGER = Logger.getLogger(StatsJMSStateless.class);
    
    @Inject
    JMSContext context;

    @Resource(lookup = "java:/queue/test")
    private Queue queue;
  
    @Resource(lookup = "java:/topic/test")
    private Topic topic;
    
    @Override
    public void produce(String message) {
		try {
			LOGGER.info("Sending message from LocalBean to Queue : " + message);
			context.createProducer().send(queue, message);
		} catch (Exception e) {
            LOGGER.error(e);
        }
    }
    @Override
    public void publish(String data) {
		try {
			LOGGER.info("Converting data from String to double and encapsuling under TextMessage");
			double value = Double.parseDouble(data);
			TextMessage message = context.createTextMessage();
			message.setDoubleProperty("number", value);
			LOGGER.info("Sending message from LocalBean to Topic : " + value);
			context.createProducer().send(topic, message);
		} catch (Exception e) {
            LOGGER.error(e);
        }
    }
}