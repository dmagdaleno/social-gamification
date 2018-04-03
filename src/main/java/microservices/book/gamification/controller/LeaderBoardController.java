package microservices.book.gamification.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import microservices.book.gamification.domain.LeaderBoardRow;
import microservices.book.gamification.service.LeaderBoardService;

/**
 * REST API for the Gamification LeaderBoard service
 * 
 * @author dmagdaleno
 */

@RestController
@RequestMapping("/leaders")
public class LeaderBoardController {
	
	private final LeaderBoardService service;

	public LeaderBoardController(LeaderBoardService service) {
		this.service = service;
	}
	
	@GetMapping
	public List<LeaderBoardRow> getLeaderBoard(){
		return service.getCurrentLeaderBoard();
	}

}
