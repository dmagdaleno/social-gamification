package microservices.book.gamification.event;

import java.io.Serializable;

class MultiplicationSolvedEvent implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final Long multiplicationResultAttemptId;
    private final Long userId;
    private final boolean correct;

    public MultiplicationSolvedEvent(Long multiplicationResultAttemptId, Long userId, boolean correct) {
		this.multiplicationResultAttemptId = multiplicationResultAttemptId;
		this.userId = userId;
		this.correct = correct;
	}

	public Long getMultiplicationResultAttemptId() {
		return multiplicationResultAttemptId;
	}

	public Long getUserId() {
		return userId;
	}

	public boolean isCorrect() {
		return correct;
	}

}
