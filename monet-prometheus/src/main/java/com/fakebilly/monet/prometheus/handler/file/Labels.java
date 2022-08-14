package com.fakebilly.monet.prometheus.handler.file;

/**
 * Labels
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class Labels {

    private String job;

    private String applicationName;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public String toString() {
        return "Labels{" +
                "job='" + job + '\'' +
                ", applicationName='" + applicationName + '\'' +
                '}';
    }
}