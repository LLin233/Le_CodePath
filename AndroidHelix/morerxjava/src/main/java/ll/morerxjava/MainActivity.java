package ll.morerxjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoSimpleModule(View view) {
        startActivity(new Intent(this, SimpleActivity.class));
    }

    public void gotoMoreModule(View view) {
        startActivity(new Intent(this, MoreActivity.class));
    }
    public void gotoLambdaModule(View view) {
        startActivity(new Intent(this, LambdaActivity .class));
    }
}
