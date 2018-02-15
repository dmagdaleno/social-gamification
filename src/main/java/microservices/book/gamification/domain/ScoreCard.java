package microservices.book.gamification.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This class represents the Score linked to an attempt in the game,
 * with an associated user and the timestamp in which the score
 * is registered.
 */
@Entity
public final class ScoreCard {
	public static final int DEFAULT_SCORE = 10;
	
	@Id
	@GeneratedValue
	@Column(name="CARD_ID")
	private final Long cardId;
	
	@Column(name="USER_ID")
	private final Long userId;
	
	@Column(name="ATTEMPT_ID")
	private final Long attemptId;
	
	@Column(name="SCORE_TS")
	private final long scoreTimestamp;
	
	@Column(name="SCORE")
	private final int score;
	
	public ScoreCard(final Long cardId, final Long userId, final Long attemptId, 
			final long scoreTimestamp, final int score) {
		super();
		this.cardId = cardId;
		this.userId = userId;
		this.attemptId = attemptId;
		this.scoreTimestamp = scoreTimestamp;
		this.score = score;
	}
	
	public ScoreCard(final Long userId, final Long attemptId) {
		this(null, userId, attemptId, System.currentTimeMillis(), DEFAULT_SCORE);
	}

	public ScoreCard() {
		this(null, null, null, 0L, 0);
	}

	public Long getCardId() {
		return cardId;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getAttemptId() {
		return attemptId;
	}

	public long getScoreTimestamp() {
		return scoreTimestamp;
	}

	public int getScore() {
		return score;
	}
	
	@Override
	public String toString() {
		return String.format("ID: %d, User ID: %d, Attempt ID: %d, Timestamp: %d, Score: %d", 
				cardId, userId, attemptId, scoreTimestamp, score);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attemptId == null) ? 0 : attemptId.hashCode());
		result = prime * result + ((cardId == null) ? 0 : cardId.hashCode());
		result = prime * result + score;
		result = prime * result + (int) (scoreTimestamp ^ (scoreTimestamp >>> 32));
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
		ScoreCard other = (ScoreCard) obj;
		if (attemptId == null) {
			if (other.attemptId != null)
				return false;
		} else if (!attemptId.equals(other.attemptId))
			return false;
		if (cardId == null) {
			if (other.cardId != null)
				return false;
		} else if (!cardId.equals(other.cardId))
			return false;
		if (score != other.score)
			return false;
		if (scoreTimestamp != other.scoreTimestamp)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}