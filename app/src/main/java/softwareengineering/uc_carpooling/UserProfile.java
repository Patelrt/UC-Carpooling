package softwareengineering.uc_carpooling;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {

    // stores Firebase database reference
    private DatabaseReference mDatabase;

    // stores the FirebaseAuth context
    private FirebaseAuth mAuth;

    // stores the user who is currently logged in
    private FirebaseUser fbUser;

    // stores username
    private String username;

    // stores the false password text
    //private String password;

    // stores the full name of the user
    private String fullName;

    // stores email
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //Initialize variables
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        fbUser = mAuth.getCurrentUser();

        username = "hi";//(fbUser.getDisplayName().isEmpty() ? "(none)" : fbUser.getDisplayName());
        fullName = "name";
        //password = "******";
        email = "example@gmail.com";

        if (fbUser != null)
        {
            username = fbUser.getDisplayName();
            email = fbUser.getEmail();
            fullName = fbUser.getDisplayName();
        }

        // Initialize box text to user's information
        ((EditText)findViewById(R.id.showUsernameProfile)).setText(username);
        ((EditText)findViewById(R.id.showEmailProfile)).setText(email);
        //((EditText)findViewById(R.id.showNameProfile)).setText(fullName);
        //(TextView)findViewById(R.id.input_password).setText(password);


        // Override on Click Listener for the button
        Button mUpdateButton = (Button) findViewById(R.id.editProfileButton);
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // open new EditUserProfile activity
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent = EditUserProfile.createIntent(UserProfile.this);
                    startActivity(intent);
            }
        });
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, UserProfile.class);
    }


}
