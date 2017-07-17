package softwareengineering.uc_carpooling;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by nick on 7/17/2017.
 */

@IgnoreExtraProperties
public class User {

    public String email;
    public String name;

    public User() {

    }

    public User(String email, String name) {
        this.email = email;
        this.name = name;

    }

    public void addMarker() {

    }
}
