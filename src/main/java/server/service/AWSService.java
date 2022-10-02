package server.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import server.entity.process.document.Document;

import java.util.Collection;

//@Service
@NoArgsConstructor
public class AWSService {
    
    private AmazonS3 client;
    
    @Value("${amazon.success-key}")
    private String accessKey;
    
    @Value("${amazon.secret-key}")
    private String secretKey;
    
    @Value("${amazon.bucket-name}")
    private String bucketName;
    
    public AWSService(String accessKey, String secretKey, String bucketName) {
        
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
    
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_WEST_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
    
    public void uploadFiles(Collection<Document> documents) {
    
    
    }

    public void updateFiles(Collection<Document> documents) {
    
    }
    
    private void deleteFiles(Collection<Document> documents) {
    
    }
    
    private void deleteFile(String file) {
    
    }
}
