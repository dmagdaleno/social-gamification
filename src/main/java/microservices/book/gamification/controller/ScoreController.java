package microservices.book.gamification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.service.GameService;

@RestController
@RequestMapping("/scores")
public class ScoreController {
	private final GameService gameService;

	@Autowired
	public ScoreController(GameService gameService) {
		this.gameService = gameService;
	}
	
	@GetMapping("/{attemptId}")
	public ScoreCard getScoreForAttempt(@PathVariable("attemptId") final Long attemptId) {
		return gameService.getScoreForAttempt(attemptId);
	}
}
