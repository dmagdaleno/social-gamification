package microservices.book.gamification.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This object contains the result of one or many iterations of the game.
 * It may contain any combination of {@link ScoreCard} objects and {@link BadgeCard} objects.
 *
 * It can be used as a delta (as a single game iteration) or to represent the total amount of score / badges.
 */
public final class GameStats {
	private final Long userId;
	private final int score;
	private final List<Badge> badges;
	
	public GameStats(Long userId, int score, List<Badge> badges) {
		this.userId = userId;
		this.score = score;
		this.badges = badges;
	}
	
	public GameStats() {
		this(0L, 0, new ArrayList<>());
	}
	
	/**
	 * Factory method to build an empty instance (zero points and no badges)
	 * @param userId the user's id
	 * @return a {@link GameStats} object with zero score and no badges
	 */
	public static GameStats emptyStats(final Long userId) {
		return new GameStats(userId, 0, Collections.emptyList());
	}

	public Long getUserId() {
		return userId;
	}

	public int getScore() {
		return score;
	}
	
	/**
	 * 
	 * @return an unmodifiable view of the badge card list
	 */
	public List<Badge> getBadges() {
		return Collections.unmodifiableList(badges);
	}
	
	@Override
	public String toString() {
		return String.format("User ID: %d, Score: %d, Badges: %d", userId, score, badges.size());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((badges == null) ? 0 : badges.hashCode());
		result = prime * result + score;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameStats other = (GameStats) obj;
		if (badges == null) {
			if (other.badges != null)
				return false;
		} else if (!badges.equals(other.badges))
			return false;
		if (score != other.score)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}
