package softwareengineering.uc_carpooling;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        /*
        Button profileButton = (Button) findViewById(R.id.buttonRequestRide);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = .createIntent(MainMenuActivity.this);
                startActivity(intent);
            }
        });
        */
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, MainMenuActivity.class);
    }
}
