package softwareengineering.uc_carpooling;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

/**
 * Created by jjsch on 7/15/2017.
 */

// This is meant to be a central location for validating emails, usernames, and passwords
public class Validation {

    public EditText validateUsername(EditText username)
    {
        username.setError(null);

        String userName = username.getText().toString();

        if (!TextUtils.isEmpty(userName) && !isPasswordValid(userName)) {
            username.setError(CommonErrorMessages.UsernameInvalid);
        }

        return username;
    }

    public EditText validatePassword(EditText password)
    {
        password.setError(null);

        String pswd = password.getText().toString();

        if (!TextUtils.isEmpty(pswd) && !isPasswordValid(pswd)) {
            password.setError(CommonErrorMessages.PasswordInvalid);
        }

        return password;
    }

    public EditText validateEmail(EditText email)
    {

        email.setError(null);

        String checkString = email.getText().toString();

        if (!TextUtils.isEmpty(checkString) && !isEmailValid(checkString)) {
            email.setError(CommonErrorMessages.InvalidEmail);
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(checkString)) {
            email.setError(CommonErrorMessages.EmptyEmail);
        } else if (!isEmailValid(checkString)) {
            email.setError(CommonErrorMessages.InvalidEmail);
        }

        return email;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@mail.uc.edu");
    }

    private boolean isUsernameValid(String username) {
        //TODO: Force this to be unique through firebase somehow
        return username.length() > 6;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 6;
    }
}
