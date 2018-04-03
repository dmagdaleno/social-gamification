package microservices.book.gamification.service;

import static microservices.book.gamification.domain.Badge.BRONZE_MULTIPLICATOR;
import static microservices.book.gamification.domain.Badge.GOLD_MULTIPLICATOR;
import static microservices.book.gamification.domain.Badge.SILVER_MULTIPLICATOR;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
		List<BadgeCard> badgeCards = new ArrayList<>();
		int totalScore = scoreCardRepo.getTotalScoreForUser(userId);
		log.info("New score for user {} is {}", userId, totalScore);
		
		List<ScoreCard> scoreCardList = scoreCardRepo.findByUserIdOrderByScoreTimestampDesc(userId);
		List<BadgeCard> badgeCardList = badgeCardRepo.findByUserIdOrderByBadgeTimestampDesc(userId);
		
		checkAndGiveBadgeBasedOnScore(badgeCardList, BRONZE_MULTIPLICATOR, totalScore, 100, userId)
			.ifPresent(badgeCards::add);
		checkAndGiveBadgeBasedOnScore(badgeCardList, SILVER_MULTIPLICATOR, totalScore, 500, userId)
			.ifPresent(badgeCards::add);
		checkAndGiveBadgeBasedOnScore(badgeCardList, GOLD_MULTIPLICATOR,   totalScore, 999, userId)
			.ifPresent(badgeCards::add);
		
		if(scoreCardList.size() == 1 && !containsBadge(badgeCardList, Badge.FIRST_WON)) {
			BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_WON, userId);
			badgeCards.add(firstWonBadge);
		}
		
		return badgeCards;
	}

	private BadgeCard giveBadgeToUser(Badge badge, Long userId) {
		BadgeCard badgeCard = new BadgeCard(userId, badge);
		badgeCardRepo.save(badgeCard);
		log.info("User with id {} won a new badge: {}", userId, badge);
		return badgeCard;
	}

	private boolean containsBadge(List<BadgeCard> badgeCards, Badge badge) {
		return badgeCards.stream().anyMatch(b -> b.getBadge().equals(badge));
	}

	private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(List<BadgeCard> badgeCards, 
			Badge badge, int score, int scoreThreshold, Long userId) {
		
		if(score >= scoreThreshold && !containsBadge(badgeCards, badge)) {
			return Optional.of(giveBadgeToUser(badge, userId));
		}
		
		return Optional.empty();
	}

	@Override
	public GameStats retrieveStatsForUser(Long userId) {
		int scoreForUser = scoreCardRepo.getTotalScoreForUser(userId);
		List<BadgeCard> badgeCards = badgeCardRepo.findByUserIdOrderByBadgeTimestampDesc(userId);
		return new GameStats(userId, scoreForUser, 
				badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList()));
	}

}
