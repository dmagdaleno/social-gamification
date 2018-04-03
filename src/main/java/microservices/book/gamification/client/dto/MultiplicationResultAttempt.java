package microservices.book.gamification.client.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import microservices.book.gamification.client.MultiplicationResultAttemptDeserializer;

@JsonDeserialize(using = MultiplicationResultAttemptDeserializer.class)
public final class MultiplicationResultAttempt {
	private final String userAlias;
	private final int multiplicationFactorA;
	private final int multiplicationFactorB;
	private final int resultAttempt;
	private final boolean correct;
	
	public MultiplicationResultAttempt() {
		this(null, -1, -1, -1, false);
	}

	public MultiplicationResultAttempt(final String userAlias, final int multiplicationFactorA, 
			final int multiplicationFactorB, final int resultAttempt, final boolean correct) {
		this.userAlias = userAlias;
		this.multiplicationFactorA = multiplicationFactorA;
		this.multiplicationFactorB = multiplicationFactorB;
		this.resultAttempt = resultAttempt;
		this.correct = correct;
	}

	public String getUserAlias() {
		return userAlias;
	}

	public int getMultiplicationFactorA() {
		return multiplicationFactorA;
	}

	public int getMultiplicationFactorB() {
		return multiplicationFactorB;
	}

	public int getResultAttempt() {
		return resultAttempt;
	}

	public boolean isCorrect() {
		return correct;
	}
	
	@Override
	public String toString() {
		return "User: " + userAlias + ", Factor A: " + multiplicationFactorA + ", Factor B: " + 
				multiplicationFactorB + ", Result attempt: " + resultAttempt + ", Correct: " + correct;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (correct ? 1231 : 1237);
		result = prime * result + multiplicationFactorA;
		result = prime * result + multiplicationFactorB;
		result = prime * result + resultAttempt;
		result = prime * result + ((userAlias == null) ? 0 : userAlias.hashCode());
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
		MultiplicationResultAttempt other = (MultiplicationResultAttempt) obj;
		if (correct != other.correct)
			return false;
		if (multiplicationFactorA != other.multiplicationFactorA)
			return false;
		if (multiplicationFactorB != other.multiplicationFactorB)
			return false;
		if (resultAttempt != other.resultAttempt)
			return false;
		if (userAlias == null) {
			if (other.userAlias != null)
				return false;
		} else if (!userAlias.equals(other.userAlias))
			return false;
		return true;
	}
	
}
