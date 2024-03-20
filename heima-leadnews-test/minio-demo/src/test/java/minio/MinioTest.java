package minio;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import java.io.FileInputStream;
import java.io.InputStream;

public class MinioTest {

    public static void main(String[] args) throws Exception{
        //上传文件到Minio
        MinioClient minioClient = MinioClient.builder()
                .endpoint("http://192.168.200.130:9000")
                .credentials("minio","minio123")
                .build();

        InputStream inputStream = new FileInputStream("D://granblue.jpg");

        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .object("granblue1.jpg")
                .contentType("image/jpg")
                .stream(inputStream, inputStream.available(), -1)
                .bucket("cd63")
                .build();

        minioClient.putObject(putObjectArgs);

        //访问规则
        //192.168.200.130:9000/cd63/granblue.jpg
    }
}
