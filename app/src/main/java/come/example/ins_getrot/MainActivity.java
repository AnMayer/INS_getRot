package come.example.ins_getrot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import java.io.File;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
Button button;
    public static long AppTime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppTime = SystemClock.elapsedRealtimeNanos();
        button=findViewById(R.id.button);
        button.setOnClickListener((View.OnClickListener) this);

    }

    @Override
    public void onClick(View v) {
        Intent intent1 = new Intent(this, Data.class);
        startActivity(intent1);
    }
}
