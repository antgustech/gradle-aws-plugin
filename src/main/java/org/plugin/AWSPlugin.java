package org.plugin;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import org.gradle.api.GradleException;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;

public class AWSPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        AWSExtension ext = project.getExtensions()
                .create("aws", AWSExtension.class);

        project.task("uploads3")
                .doFirst(task -> {
                    String invalidParameter = "";
                    if (!isValid(ext.getProfile())) {
                        invalidParameter = "Invalid profile";
                    } else if (!isValid(ext.getBucket())) {
                        invalidParameter = "Invalid bucket";
                    } else if (!isValid(ext.getS3Key())) {
                        invalidParameter = "Invalid key";
                    } else if (!isValid(ext.getRegion())) {
                        invalidParameter = "Invalid regions";
                    } else if (!isValid(ext.getRegion())) {
                        invalidParameter = "Invalid file";
                    }
                    if (!invalidParameter.isEmpty()) {
                        throw new InvalidUserDataException(invalidParameter);
                    }
                })

                .doLast(task -> {
                    AmazonS3 s3;
                    ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider(ext.getProfile());
                    if (isValid(ext.getProxyHost())) {
                        System.out.println("Using client with proxy");
                        s3 = AmazonS3ClientBuilder.standard().withClientConfiguration(new ClientConfiguration().withProxyHost(ext.getProxyHost()).withProxyPort(ext.getProxyPort()))
                                .withCredentials(credentialsProvider)
                                .withRegion(Regions.fromName(ext.getRegion())).build();
                    } else {
                        System.out.println("Using client without proxy");
                        s3 = AmazonS3ClientBuilder.standard().withClientConfiguration(new ClientConfiguration())
                                .withCredentials(credentialsProvider)
                                .withRegion(Regions.fromName(ext.getRegion())).build();
                    }

                    if (s3.doesObjectExist(ext.getBucket(), ext.getS3Key())) {
                        if (!ext.isS3Overwrite()) {
                            throw new GradleException("File already exists and overwrite flag was set to false!");
                        }
                    }

                    System.out.println("About to upload " + ext.getFile() + " to s3://" + ext.getBucket() + "/" + ext.getS3Key() + "\n...");

                    TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(s3).build();

                    try {
                        if (ext.isDirectory()) {
                            MultipleFileUpload multipleFileUpload = transferManager.uploadDirectory(ext.getBucket(), ext.getS3Key(), new File(ext.getFile()), true);
                            multipleFileUpload.waitForCompletion();
                        } else {
                            Upload upload = transferManager.upload(ext.getBucket(), ext.getS3Key(), new File(ext.getFile()));
                            upload.waitForUploadResult();
                        }
                    } catch (AmazonServiceException e) {
                        System.err.println(e.getErrorMessage());
                        System.exit(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        transferManager.shutdownNow();
                    }
                });
    }

    private boolean isValid(String val) {
        return val != null && !val.isEmpty();
    }
}
