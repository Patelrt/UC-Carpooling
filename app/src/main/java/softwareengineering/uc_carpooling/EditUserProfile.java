package softwareengineering.uc_carpooling;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditUserProfile extends AppCompatActivity {

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

    // class that validates email and password
    private Validation validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        //Initialize variables
        validator = new Validation();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        fbUser = mAuth.getCurrentUser();

        username = "defaultusername";//(fbUser.getDisplayName().isEmpty() ? "(none)" : fbUser.getDisplayName());
        fullName = "defaultname";
        //password = "******";
        email = "example@gmail.com";

        // get user information
        if (fbUser != null)
        {
            username = fbUser.getDisplayName();
            email = fbUser.getEmail();
            fullName = fbUser.getUid();
        }

        // Initialize box text to user's information
        ((EditText)findViewById(R.id.input_username)).setText(username);
        ((EditText)findViewById(R.id.input_email)).setText(email);
        //(TextView)findViewById(R.id.input_password).setText(password);


        // Override on Click Listener for the button
        Button mUpdateButton = (Button) findViewById(R.id.editProfileSave);
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textToValidate = ((EditText)(findViewById(R.id.input_username)));
                validator.validateUsername(textToValidate);

            }
        });
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, EditUserProfile.class);
    }
}
