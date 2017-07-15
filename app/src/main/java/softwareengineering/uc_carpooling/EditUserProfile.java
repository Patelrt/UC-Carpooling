package softwareengineering.uc_carpooling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditUserProfile extends AppCompatActivity {

    // stores the FirebaseAuth context
    private FirebaseAuth mAuth;

    // stores the user who is currently logged in
    private FirebaseUser fbUser;

    // stores username
    private String username;

    // stores the false password text
    private String password;

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
        mAuth = FirebaseAuth.getInstance();
        fbUser = mAuth.getCurrentUser();
        username = fbUser.getDisplayName();
        password = "******";
        email = fbUser.getEmail();

        // Initialize box text to user's information
        ((EditText)findViewById(R.id.input_username)).setText(username);
        ((EditText)findViewById(R.id.input_email)).setText(email);



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
}
