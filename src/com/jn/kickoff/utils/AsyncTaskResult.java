/**
 * 
 */
package com.jn.kickoff.utils;

import com.jn.kickoff.webservice.RESTWebInvokeException;


/**
 * @author ArunMohan
 * 
 */
public class AsyncTaskResult<T> {

	private T result;
	private Exception error;
	private RESTWebInvokeException webError;

	public T getResult() {
		return result;
	}

	public Exception getError() {
		return error;
	}

	public AsyncTaskResult(T result) {
		this.result = result;
	}

	public AsyncTaskResult(Exception error) {
		this.error = error;
	}

	public AsyncTaskResult(RESTWebInvokeException webError) {
		this.webError = webError;
	}

	public RESTWebInvokeException getWebError() {
		return webError;
	}

}
