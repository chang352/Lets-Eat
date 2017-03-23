package cs307spring17team26.lets_eat_;

import java.util.Arrays;


import org.bson.Document;

import com.mongodb.*;

public class ResponseParser {

	Account parseAccount(Document user) {
		String email = user.get("_id").toString();
		String pass = user.get("_pass").toString();
		return new Account(email, pass);
	}
	
	User parseUser(Document user) {
		int age = (Integer) user.get("age");
	    	String userId = (String) user.get("userId");
	    	String name = (String) user.get("name");
	    	String gender = (String) user.get("gender");
	    	String bio = (String) user.get("bio");
	    	int[] location = (int[]) user.get("location");
	    	int maxRange = (Integer) user.get("maxRange");
	    	return new User(age, userId, name, gender, bio, location, maxRange);
		
	}
	
}
