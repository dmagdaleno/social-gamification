package microservices.book.gamification.service;

import java.util.List;

import org.springframework.stereotype.Service;

import microservices.book.gamification.domain.LeaderBoardRow;
import microservices.book.gamification.repository.ScoreCardRepository;

@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {
	
	private final ScoreCardRepository scoreCardRepository;
	
	public LeaderBoardServiceImpl(final ScoreCardRepository scoreCardRepository) {
		this.scoreCardRepository = scoreCardRepository;
	}

	@Override
	public List<LeaderBoardRow> getCurrentLeaderBoard() {
		return scoreCardRepository.findFirst10();
	}

}
