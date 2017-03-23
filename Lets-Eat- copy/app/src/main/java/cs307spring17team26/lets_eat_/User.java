package LetsEatServer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
    String age;
    String userId;
    String name;
    String gender;
    String bio;
    int[] location;
    int maxRange;
    
    public User(String age, String userId, String name, String gender, String bio, int[] location, int maxRange) {
    	this.age = age;
    	this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.bio = bio;
        this.location = location;
        this.maxRange = maxRange;
        
    }
    
}
