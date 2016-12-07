package com.cloudthat.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.cloudthat.models.UserModel;

public class UserDynamodbController {

	private static AmazonDynamoDB dbObject;
	private ScanResult dbResult;
	private ScanRequest scanRequest;
	private UserModel modelObject;
	private BasicAWSCredentials credentials;
	private DynamoDB dynamoDB = null;
	private static DescribeTableRequest describeTableRequest = null;
	private static TableDescription tableDescription = null;
	private static final String tableName = "userdetails";
	
	private int count = 0;

	
	public UserDynamodbController() {

		//credentials = new BasicAWSCredentials(accessKey, secretKey);
		dbObject = new AmazonDynamoDBClient(new InstanceProfileCredentialsProvider()); // set
		// Region
		dbObject.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_1));
				
		dynamoDB = new DynamoDB(dbObject);

		count = 0;
	}

	// check whether table exists or not

	public boolean doesTableExists() {
		// check
		describeTableRequest = new DescribeTableRequest().withTableName(tableName);
		if (describeTableRequest != null) {
			tableDescription = dbObject.describeTable(describeTableRequest).getTable();
			System.out.println("Table Description: " + tableDescription);
			System.out.println("Table " + tableName + " is ACTIVE");
			return true;
		} else {
			System.out.println("Table " + tableName + " doesn't exist");
			return false;
		}
	}

	// Insert the content to the table

	public String insertData(UserModel model) {

		this.modelObject = model;
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss a");

		System.out
				.println(modelObject.getEmailId() + " " + modelObject.getFirstName() + " " + modelObject.getLastName());

		if (doesTableExists()) {
			Map<String, AttributeValue> items = new HashMap<String, AttributeValue>();
			items.put("username", new AttributeValue().withS(model.getUserName()));
			items.put("email", new AttributeValue().withS(model.getEmailId()));
			items.put("firstname", new AttributeValue().withS(model.getFirstName()));
			items.put("lastname", new AttributeValue().withS(model.getLastName()));
			items.put("post-time", new AttributeValue().withS(ft.format(date)));
			PutItemRequest putItemRequest1 = new PutItemRequest().withTableName(tableName).withItem(items);
			dbObject.putItem(putItemRequest1);
			return "success";
		} else {
			return "failed";
		}
	}

	// delete user record from the table

	public String deleteData(String hashKeyValue, String rangeKeyValue) {
		Table table = dynamoDB.getTable(tableName);
		String result = "";

		try {

			/*
			 * DeleteItemSpec deleteItemSpec = new
			 * DeleteItemSpec().withPrimaryKey("username", hashKeyValue,
			 * "post-time", rangeKeyValue);
			 */
			// System.out.println(hashKeyValue + " " + rangeKeyValue);
			// HashMap<String, AttributeValue> key = new HashMap<String,
			// AttributeValue>();
			// key.put("username", new AttributeValue().withS(hashKeyValue));
			// key.put("post-time", new AttributeValue().withS(rangeKeyValue));
			//
			// DeleteItemRequest deleteItemRequest = new
			// DeleteItemRequest().withTableName(tableName).withKey(key);
			// dbObject.deleteItem(deleteItemRequest);
			// System.out.println("Delete Item succeeded");

			result = "success";

		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("Unable to delete item:");

			result = "failed";
		}

		return result;
	}
	// get UserDetails

	public List<UserModel> getUserData() throws IOException {

		List<UserModel> userList = new ArrayList<UserModel>();
		// create the object of scan request
		scanRequest = new ScanRequest();
		scanRequest.setTableName(tableName);

		if (dbResult != null) {
			scanRequest.setExclusiveStartKey(dbResult.getLastEvaluatedKey());

		}

		dbResult = dbObject.scan(scanRequest);
		List<Map<String, AttributeValue>> rows = dbResult.getItems();

		UserModel modelObject;
		AttributeValue username, email, firstname, lastname, dateTime;
		userList.clear();
		for (Map<String, AttributeValue> map : rows) {
			try {
				modelObject = new UserModel();

				username = map.get("firstname");
				modelObject.setUserName(username.getS());

				email = map.get("email");
				modelObject.setEmailId(email.getS());

				firstname = map.get("firstname");
				modelObject.setFirstName(firstname.getS());

				lastname = map.get("lastname");
				modelObject.setLastName(lastname.getS());

				dateTime = map.get("post-time");
				modelObject.setDateTime(dateTime.getS());
				userList.add(modelObject);
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
			}
		}

		for (int i = 0; i < userList.size(); i++) {
			System.out.println(userList.get(i).getEmailId());
		}
		return userList;
	}

}
