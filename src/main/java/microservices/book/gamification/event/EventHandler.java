package microservices.book.gamification.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import microservices.book.gamification.service.GameService;

@Component
class EventHandler {
	
	private static final Logger log = LoggerFactory.getLogger(EventHandler.class);

    private GameService gameService;

    EventHandler(final GameService gameService) {
        this.gameService = gameService;
    }

    @RabbitListener(queues = "${multiplication.queue}")
    void handleMultiplicationSolved(final MultiplicationSolvedEvent event) {
        log.info("Multiplication Solved Event received: {}", event.getMultiplicationResultAttemptId());
        try {
            gameService.newAttemptForUser(event.getUserId(),
                    event.getMultiplicationResultAttemptId(),
                    event.isCorrect());
        } catch (final Exception e) {
            log.error("Error when trying to process MultiplicationSolvedEvent", e);
            // Avoids the event to be re-queued and reprocessed.
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}