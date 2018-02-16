package microservices.book.gamification.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.BadgeCard;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.repository.BadgeCardRepository;
import microservices.book.gamification.repository.ScoreCardRepository;

public class GameServiceTest {
	
	@Mock
	private BadgeCardRepository badgeCardRepo;
	
	@Mock
	private ScoreCardRepository scoreCardRepo;
	
	private GameService gameService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		gameService = new GameServiceImpl(badgeCardRepo, scoreCardRepo);
	}
	
	@Test
    public void processFirstCorrectAttemptTest() {
        // given
        Long userId = 1L;
        Long attemptId = 8L;
        int totalScore = 10;
        ScoreCard scoreCard = new ScoreCard(userId, attemptId);
        given(scoreCardRepo.getTotalScoreForUser(userId)).willReturn(totalScore);
        // this repository will return the just-won score card
        given(scoreCardRepo.findByUserIdOrderByScoreTimestampDesc(userId))
                .willReturn(Collections.singletonList(scoreCard));
        given(badgeCardRepo.findByUserIdOrderByBadgeTimestampDesc(userId))
                .willReturn(Collections.emptyList());


        // when
        GameStats iteration = gameService.newAttemptForUser(userId, attemptId, true);

        // assert - should score one card and win the badge FIRST_WON
        assertThat(iteration.getScore()).isEqualTo(scoreCard.getScore());
        assertThat(iteration.getBadges()).containsOnly(Badge.FIRST_WON);
    }
	
	
	
	
	// my tests
	@Test
	public void testRetrieveStatsForUser() {
		// given
		Long userId = 1L;
		List<BadgeCard> expectedBadgeCards = getBadgeCardList(userId);
		given(badgeCardRepo.findByUserIdOrderByBadgeTimestampDesc(userId)).willReturn(expectedBadgeCards);
		given(scoreCardRepo.getTotalScoreForUser(userId)).willReturn(210);
		
		// when
		GameStats stats = gameService.retrieveStatsForUser(userId);
		
		// then
		assertThat(stats.getUserId()).isEqualTo(userId);
		assertThat(stats.getScore()).isEqualTo(210);
		assertThat(stats.getBadges()).contains(expectedBadgeCards.get(0).getBadge(), 
				expectedBadgeCards.get(1).getBadge(), expectedBadgeCards.get(2).getBadge());
	}
	
	private List<BadgeCard> getBadgeCardList(Long userId){
		BadgeCard bc1 = new BadgeCard(userId, Badge.FIRST_ATTEMPT);
		BadgeCard bc2 = new BadgeCard(userId, Badge.FIRST_WON);
		BadgeCard bc3 = new BadgeCard(userId, Badge.BRONZE_MULTIPLICATOR);
		return Arrays.asList(new BadgeCard[]{bc1, bc2, bc3});
	}
}
