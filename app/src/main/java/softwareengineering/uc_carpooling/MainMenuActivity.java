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

        Button profileButton = (Button) findViewById(R.id.buttonProfile); //Button for Profile
        Button rideRequestButton = (Button) findViewById(R.id.buttonRequestRide); //Button for requesting a ride
        Button offerRideButton = (Button) findViewById(R. id.offer_a_ride); //Button for

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UserProfile.createIntent(MainMenuActivity.this);
                startActivity(intent);
            }
        });

        rideRequestButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = MapsActivity.createIntent(MainMenuActivity.this);
                startActivity(intent);
            }
        });



    }

    public static Intent createIntent(Context context) {
        return new Intent(context, MainMenuActivity.class);
    }
}
