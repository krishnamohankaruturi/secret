package edu.ku.cete.batch.support;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class SkipExceptionPolicy implements SkipPolicy {

	private Class<? extends Exception> exceptionClassToSkip;

	public SkipExceptionPolicy(Class<? extends Exception> exceptionClassToSkip) {
		super();
		this.exceptionClassToSkip = exceptionClassToSkip;
	}

	@Override
	public boolean shouldSkip(Throwable t, int skipCount)
			throws SkipLimitExceededException {
		return exceptionClassToSkip.isAssignableFrom(t.getClass());
	}
}