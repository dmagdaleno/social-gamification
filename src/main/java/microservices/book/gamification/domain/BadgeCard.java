package microservices.book.gamification.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This class links a {@link Badge} to a User. 
 * Contains also a timestamp with the moment in which the user got it.
 */
@Entity
public class BadgeCard {
	
	@Id
	@GeneratedValue
	@Column(name="BADGE_ID")
	private final Long badgeId;
	
	private final Long userId;
	private final long badgeTimestamp;
	private final Badge badge;
	
	public BadgeCard(final Long badgeId, final Long userId, final long badgeTimestamp, final Badge badge) {
		super();
		this.badgeId = badgeId;
		this.userId = userId;
		this.badgeTimestamp = badgeTimestamp;
		this.badge = badge;
	}
	
	public BadgeCard(final Long userId, final Badge badge) {
		this(null, userId, System.currentTimeMillis(), badge);
	}

	public BadgeCard() {
		this(null, null, 0L, null);
	}

	public Long getBadgeId() {
		return badgeId;
	}

	public Long getUserId() {
		return userId;
	}

	public long getBadgeTimestamp() {
		return badgeTimestamp;
	}

	public Badge getBadge() {
		return badge;
	}
	
	@Override
	public String toString() {
		return String.format("ID: %d, User ID: %d, Timestamp: %d, Badge: %s", 
				badgeId, userId, badgeTimestamp, badge);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((badge == null) ? 0 : badge.hashCode());
		result = prime * result + ((badgeId == null) ? 0 : badgeId.hashCode());
		result = prime * result + (int) (badgeTimestamp ^ (badgeTimestamp >>> 32));
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
		BadgeCard other = (BadgeCard) obj;
		if (badge != other.badge)
			return false;
		if (badgeId == null) {
			if (other.badgeId != null)
				return false;
		} else if (!badgeId.equals(other.badgeId))
			return false;
		if (badgeTimestamp != other.badgeTimestamp)
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}
