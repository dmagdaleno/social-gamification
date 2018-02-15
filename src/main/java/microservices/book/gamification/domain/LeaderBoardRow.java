package microservices.book.gamification.domain;

/**
 * Represents a line in our Leaderboard: it links a user to a total score.
 */
public class LeaderBoardRow {
	private final Long userId;
	private final Long totalScore;
	
	public LeaderBoardRow(Long userId, Long totalScore) {
		this.userId = userId;
		this.totalScore = totalScore;
	}

	public LeaderBoardRow() {
		this(0L, 0L);
	}
	
	public Long getUserId() {
		return userId;
	}

	public Long getTotalScore() {
		return totalScore;
	}

	@Override
	public String toString() {
		return String.format("User ID: %d, Total Score: %d", userId, totalScore);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((totalScore == null) ? 0 : totalScore.hashCode());
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
		LeaderBoardRow other = (LeaderBoardRow) obj;
		if (totalScore == null) {
			if (other.totalScore != null)
				return false;
		} else if (!totalScore.equals(other.totalScore))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}
