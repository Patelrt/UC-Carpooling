package softwareengineering.uc_carpooling;

import android.content.Intent;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class OfferRide extends AppCompatActivity {

    private EditText inputDate;
    private EditText inputDestination;

    public static String date;
    public static String destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_ride);

        inputDate = (EditText) findViewById(R.id.enterDate);
        inputDestination = (EditText) findViewById(R.id.enterAddress);

        Button requestButton = (Button) findViewById(R.id.buttonEnterRequest);

        requestButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                date = inputDate.getText().toString();
                destination = inputDestination.getText().toString();
                Intent intent = MapsActivity.createIntent(OfferRide.this);
                startActivity(intent);
            }
        });
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, OfferRide.class);
    }
}
