package com.jn.kickoff.holder;

import java.util.List;

public class NewsBase {
	private String timestamp;
	private String resultsOffset;
	private String status;
	private String resultsLimit;
	private String resultsCount;
	private List<NewsHeadlines> headlines;
	
/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the resultsOffset
	 */
	public String getResultsOffset() {
		return resultsOffset;
	}
	/**
	 * @param resultsOffset the resultsOffset to set
	 */
	public void setResultsOffset(String resultsOffset) {
		this.resultsOffset = resultsOffset;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the resultsLimit
	 */
	public String getResultsLimit() {
		return resultsLimit;
	}
	/**
	 * @param resultsLimit the resultsLimit to set
	 */
	public void setResultsLimit(String resultsLimit) {
		this.resultsLimit = resultsLimit;
	}
	/**
	 * @return the resultsCount
	 */
	public String getResultsCount() {
		return resultsCount;
	}
	/**
	 * @param resultsCount the resultsCount to set
	 */
	public void setResultsCount(String resultsCount) {
		this.resultsCount = resultsCount;
	}
	/**
	 * @return the headlines
	 */
	public List<NewsHeadlines> getHeadlines() {
		return headlines;
	}
	/**
	 * @param headlines the headlines to set
	 */
	public void setHeadlines(List<NewsHeadlines> headlines) {
		this.headlines = headlines;
	}


}
