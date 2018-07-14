package microservices.book.gamification.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import microservices.book.gamification.client.dto.MultiplicationResultAttempt;

@Component
public class MultiplicationResultAttemptClientImpl implements MultiplicationResultAttemptClient {
	
	private static final Logger log = LoggerFactory.getLogger(MultiplicationResultAttemptClientImpl.class);
	
	private final RestTemplate restTemplate;
	private final String multiplicationHost;
	
	@Autowired
	public MultiplicationResultAttemptClientImpl(final RestTemplate restTemplate, 
			@Value("${multiplicationHost}") final String multiplicationHost) {
		this.restTemplate = restTemplate;
		this.multiplicationHost = multiplicationHost;
	}

	@HystrixCommand(fallbackMethod = "defaultResult")
	@Override
	public MultiplicationResultAttempt retrieveMultiplicationResultAttemptById(long multiplicationId) {
		
		log.info("Retrieving multiplication result attempt with id {}", multiplicationId);
		
		MultiplicationResultAttempt multiplicationResultAttempt = restTemplate.getForObject(
				multiplicationHost + "/results/" + multiplicationId, 
				MultiplicationResultAttempt.class);
		 
		if(multiplicationResultAttempt == null)
			 multiplicationResultAttempt = new MultiplicationResultAttempt();
		 
		return multiplicationResultAttempt;
	}

	//fallbackMethod
	@SuppressWarnings("unused")
	private MultiplicationResultAttempt defaultResult(long multiplicationResultAttemptId) {
        return new MultiplicationResultAttempt("fakeAlias", 10, 10, 100, true);
    }
}
