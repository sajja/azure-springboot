package com.example.springboot;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.SharedAccessAccountPolicy;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.SharedAccessBlobPermissions;
import com.microsoft.azure.storage.blob.SharedAccessBlobPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.EnumSet;

@RestController
public class HelloController {

    //    @Value("${connectionString}")
    private String keyVaultMessage;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot MASTER!";
    }

    @RequestMapping("/blob/list")
    public String blobList(@RequestParam String containerName) throws Exception {
        final String storageConnectionString = "DefaultEndpointsProtocol=https;" +
                "AccountName=sajithstorage;" +
                "AccountKey=DfpGhW6KnYm4SuGHpDjR4Vn4u/m3F1boL6FgrhJ4WQBcgInxUuNicNnbGx/xc3WcsvmhH28DuRLnaG5I1H+4AQ==;" +
                "EndpointSuffix=core.windows.net";
        CloudStorageAccount account = CloudStorageAccount.parse(storageConnectionString);
        CloudBlobClient client = account.createCloudBlobClient();
        CloudBlobContainer container = client.getContainerReference(containerName);
        StringBuffer sb = new StringBuffer("Files in the container: Customer\n");


        Date expirationTime = Date.from(LocalDateTime.now().plusDays(7).atZone(ZoneOffset.UTC).toInstant());


        SharedAccessAccountPolicy sharedAccessAccountPolicy = new SharedAccessAccountPolicy();
        sharedAccessAccountPolicy.setPermissionsFromString("racwdlup");
        long date = new Date().getTime();

        sharedAccessAccountPolicy.setSharedAccessStartTime(new Date(date));
        sharedAccessAccountPolicy.setSharedAccessExpiryTime(expirationTime);
        sharedAccessAccountPolicy.setResourceTypeFromString("sco");
        sharedAccessAccountPolicy.setServiceFromString("bfqt");
        String sasToken = "?" + account.generateSharedAccessSignature(sharedAccessAccountPolicy);



        sb.append("Token: \n");
        sb.append(sasToken);
        sb.append("\n");
        sb.append("\n");
        sb.append("\n");

        sb.append("Blobs (DONT USE CURL, Use  browser): \n");

        container.listBlobs().forEach(listBlobItem -> {
            sb.append(listBlobItem.getUri());
            sb.append(sasToken);
            sb.append("\n");
        });


        return sb.toString();
    }

    @RequestMapping("/vault")
    public String vault() {
        return "Greetings from Spring Boot VAULT: " + keyVaultMessage;
    }

    @RequestMapping("/mysql")
    public String mysql() throws Exception {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://172.17.0.1:3306/sajithDB", "sajith1", "sajith")) {

            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (Exception e) {
            throw e;
        }

        return "Greetings from Spring Boot MYSQL!";
    }



}
