package minio;

import com.heima.file.service.FileStorageService;
import com.heima.minio.MinioApplication;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.InputStream;

@SpringBootTest(classes = MinioApplication.class)
@RunWith(SpringRunner.class)
public class MinioTest {

    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void test() throws Exception{
        FileInputStream fileInputStream = new FileInputStream("D://granblue.jpg");
        String path = fileStorageService.uploadImgFile("", "granblue3.jpg", fileInputStream);
        System.out.println(path);
    }

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
