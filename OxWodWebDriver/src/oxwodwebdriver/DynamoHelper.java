/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oxwodwebdriver;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author herman
 */
public class DynamoHelper {
    
    // Upload the Wod (Speech) and Card Text for Alaxa app to DynamoDB
    public static void UploadProcessedWodDataToDynamoDB(String wod, String cardText) {
        System.out.println("About to upload data to DynamoDB");
        
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("", "");
        AmazonDynamoDBClient client = new AmazonDynamoDBClient(awsCreds).withRegion(Regions.US_EAST_1);
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable("OxWod");

        Map<String, String> expressionAttributeNames = new HashMap<String, String>();
        expressionAttributeNames.put("#W", "Wod");
        expressionAttributeNames.put("#C", "CardWod");

        Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
        expressionAttributeValues.put(":val1", wod);
        expressionAttributeValues.put(":val2", cardText);

        UpdateItemOutcome outcome = table.updateItem(
                "Id", // key attribute name
                1, // key attribute value
                "set #W = :val1, #C = :val2", // UpdateExpression
                expressionAttributeNames,
                expressionAttributeValues);
        System.out.println("Fnished uploading data to DynamoDB");
    }
}
