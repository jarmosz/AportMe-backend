package com.aportme.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.project.id}")
    private String projectId;

    @Value("${firebase.bucket.name}")
    private String bucketName;

    @Value("${firebase.key.path}")
    private String keyPath;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(keyPath);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId(projectId)
                .setStorageBucket(bucketName)
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public StorageOptions storageOptions() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(keyPath);

        return StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

    }
}
