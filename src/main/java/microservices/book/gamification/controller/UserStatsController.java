package microservices.book.gamification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.service.GameService;

/**
 * REST API for the Gamification Statistics service
 * 
 * @author dmagdaleno
 */

@RestController
@RequestMapping("/stats")
public class UserStatsController {
	private final GameService service;

	public UserStatsController(GameService service) {
		this.service = service;
	}
	
	@GetMapping
	public GameStats getStatsForUser(@RequestParam("userId") final Long userId) {
		return service.retrieveStatsForUser(userId);
	}

}
