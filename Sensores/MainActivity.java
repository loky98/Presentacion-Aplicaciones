import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private RelativeLayout mainLayout;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener el SensorManager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // Obtener el sensor de proximidad TMD4910
        proximitySensor = sensorManager.getDefaultSensor(65560);

        // Obtener referencias a los elementos de la interfaz de usuario
        mainLayout = findViewById(R.id.main_layout);
        textView = findViewById(R.id.text_view);

        // Mantener la pantalla encendida mientras se ejecuta la aplicación
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registrar el listener para el sensor de proximidad
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Detener la lectura del sensor de proximidad cuando la aplicación está en pausa
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Verificar si el evento proviene del sensor de proximidad
        //if (event.sensor.getType() == 65560) {
            // Obtener el valor de proximidad
            float proximityValue = event.values[0];

            // Cambiar el color de fondo de acuerdo al valor de proximidad
            if (proximityValue < proximitySensor.getMaximumRange()) {
                mainLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                textView.setText("Objeto cercano");
            } else {
                mainLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                textView.setText("Objeto alejado");
            }
        //}
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No se utiliza en este ejemplo
    }
}
