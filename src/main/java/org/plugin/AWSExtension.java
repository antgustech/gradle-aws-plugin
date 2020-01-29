package org.plugin;

public class AWSExtension {
    private String profile = "default";
    private String region;

    private String proxyHost;
    private int proxyPort;

    private String bucket;
    private String s3Key;
    private String file;
    private boolean directory = false;
    private boolean s3Overwrite = false;

    public boolean isS3Overwrite() {
        return s3Overwrite;
    }

    public void setS3Overwrite(boolean s3Overwrite) {
        this.s3Overwrite = s3Overwrite;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        bucket = bucket.replace("s3://", ""); //Removes s3 protocol if present
        this.bucket = bucket;
    }

    public String getS3Key() {
        return s3Key;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

}
