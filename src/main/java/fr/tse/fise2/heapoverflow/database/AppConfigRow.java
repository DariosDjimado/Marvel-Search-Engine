package fr.tse.fise2.heapoverflow.database;


import java.sql.Date;

public class AppConfigRow {
    private String appVersion;
    private String appSection;
    private int requestCount;
    private Date requestLastCheck;


    public AppConfigRow(String appVersion, String appSection, int requestCount, Date requestLastCheck) {
        this.appVersion = appVersion;
        this.appSection = appSection;
        this.requestCount = requestCount;
        this.requestLastCheck = requestLastCheck;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppSection() {
        return appSection;
    }

    public void setAppSection(String appSection) {
        this.appSection = appSection;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public java.sql.Date getRequestLastCheck() {
        return requestLastCheck;
    }

    public void setRequestLastCheck(Date requestLastCheck) {
        this.requestLastCheck = requestLastCheck;
    }

    @Override
    public String toString() {
        return "AppConfigRow{" +
                "appVersion='" + appVersion + '\'' +
                ", appSection='" + appSection + '\'' +
                ", requestCount=" + requestCount +
                ", requestLastCheck=" + requestLastCheck +
                '}';
    }
}
