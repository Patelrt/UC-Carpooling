package softwareengineering.uc_carpooling;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RequestRide extends AppCompatActivity {

    private EditText inputDate;
    private EditText inputDestination;

    public static String date;
    public static String destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_ride);

        inputDate = (EditText) findViewById(R.id.enterDate);
        inputDestination = (EditText) findViewById(R.id.enterAddress);

        Button requestButton = (Button) findViewById(R.id.buttonEnterRequest);


        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = inputDate.getText().toString();
                destination = inputDestination.getText().toString();
                Intent intent = MapsActivity.createIntent(RequestRide.this);
                startActivity(intent);
            }
        });
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, RequestRide.class);
    }




}
