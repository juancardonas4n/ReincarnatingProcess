package com.s4n.campus.reincarnating;

/**
 */
public class InfoProcess {
    private String path;
    private String fileName;
    private int maxRetries;
    private long maxTimeout;
    
    public InfoProcess(String path, String fileName, int maxRetries, long maxTimeout) {
	this.path = path;
	this.fileName = fileName;
	this.maxRetries = maxRetries;
	this.maxTimeout = maxTimeout;
    }

    public String getPath() {
	return path;
    }

    public String fileName() {
	return fileName;
    }

    public String getCompletePath() {
	return path + "/" + fileName;
    }

    public int getMaxRetries() {
	return maxRetries;
    }

    public long getMaxTimeout() {
	return maxTimeout;
    }
}
