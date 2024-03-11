package com.aws.demo.services;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;



@Slf4j
public class SecretManagerService {


    private String userName;


    private String password;

    private static SecretManagerService secretManagerService;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    private SecretManagerService()
    {
        getUserNameAndPasswordFromAWS(this);
    }

    public static SecretManagerService getInstance()
    {
        if(secretManagerService == null)
        {
            synchronized (SecretManagerService.class)
            {
                if(secretManagerService == null)
                    secretManagerService = new SecretManagerService();
            }
        }
        return  secretManagerService;
    }
    private static void getUserNameAndPasswordFromAWS(SecretManagerService secretManagerService)
    {
        try
        {
            AwsClientBuilder.EndpointConfiguration  config  =  new  AwsClientBuilder.EndpointConfiguration("secretsmanager.us-east-1.amazonaws.com","us-east-1");
            AWSSecretsManagerClientBuilder  clientBuilder  =  AWSSecretsManagerClientBuilder.standard().withCredentials(new ProfileCredentialsProvider("spring_user"));
            clientBuilder.setEndpointConfiguration(config);
            AWSSecretsManager client  =  clientBuilder.build();
            GetSecretValueRequest getSecretValueRequest  =  new  GetSecretValueRequest().withSecretId("demo/db-username-pwd");
            GetSecretValueResult getSecretValueResponse  =  client.getSecretValue(getSecretValueRequest);
            String secret = getSecretValueResponse.getSecretString();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode secretsJson  =  objectMapper.readTree(secret);
            secretManagerService.userName = secretsJson.get("username").asText();
            secretManagerService.password = secretsJson.get("password").asText();
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        }
    }
}
