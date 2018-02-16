package microservices.book.gamification.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.BadgeCard;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.repository.BadgeCardRepository;
import microservices.book.gamification.repository.ScoreCardRepository;

@Service
public class GameServiceImpl implements GameService {
	
	
	private static final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);
	
	private final BadgeCardRepository badgeCardRepo;
	
	private final ScoreCardRepository scoreCardRepo;

	public GameServiceImpl(final BadgeCardRepository badgeCardRepo, final ScoreCardRepository scoreCardRepo) {
		this.badgeCardRepo = badgeCardRepo;
		this.scoreCardRepo = scoreCardRepo;
	}

	@Override
	public GameStats newAttemptForUser(Long userId, Long attemptId, boolean correct) {
		if(correct) {
			ScoreCard scoreCard = new ScoreCard(userId, attemptId);
			scoreCardRepo.save(scoreCard);
			log.info("User with id {} scored {} points for attempt id {}", 
					userId, scoreCard.getScore(), attemptId);
			List<BadgeCard> badgeCards = processForBadges(userId, attemptId);
			return new GameStats(userId, scoreCard.getScore(), 
					badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
		}
		return GameStats.emptyStats(userId);
	}

	private List<BadgeCard> processForBadges(Long userId, Long attemptId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameStats retrieveStatsForUser(Long userId) {
		List<BadgeCard> badgeCards = badgeCardRepo.findByUserIdOrderByBadgeTimestampDesc(userId);
		int scoreForUser = scoreCardRepo.getTotalScoreForUser(userId);
		return new GameStats(userId, scoreForUser, 
				badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
	}

}
