package com.mh.core.task.command.abstracts;

public abstract class MHCommonCmd<T> extends MHCommand {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public abstract T getResult();
}
