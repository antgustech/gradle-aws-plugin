# Gradle AWS Plugin

Purpose of this project is to offer a gradle plugin that you can use to upload either single files or directories to an S3 bucket.

### Installation
Add the dependency like this in your build.gradle file and apply the plugin:

### Usage

Configure the parameters like this

  ```Configure bucket params
       aws {
           region = "xx-xxxx-x"   
           profile = "xxxxx"
           proxyHost = "xxxxx"
           proxyPort = xx
           bucket = "xxxxxxx"
           s3Key = "xxxxxxx"
           file = "xxxxxxx"
           s3Overwrite = xxxx;
           directory = xxxx;
       }
  ```

And then execute the uploads3 tasl as with the Gradle command:

  ```
gradle uploads3
  ```

| Parameter     | Required      | default    |  description                                             |
| ------------- |:--------------|:-----------|:---------------------------------------------------------|
| region        | Yes           |            |  AWS region                                              |
| profile       | Yes           |   default  |  AWS profile on your machine                             |
| bucket        | Yes           |            |  Bucket name                                             |
| s3Key         | Yes           |            |  Key on the bucket                                       |
| proxyHost     | No            |            |  If on a proxy specify host                              |
| proxyPort     | No            |            |  If on proxy specify port                                |
| s3Overwrite   | no            |   false    |  Specify if you want to overwrite files if they exist    |
| directory     | no            |   false    |  Specify if you want to upload an entire directory       | 
