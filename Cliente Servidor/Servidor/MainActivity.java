import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button startButton;

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);
        startButton = findViewById(R.id.start_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    startServer();
                } else {
                    stopServer();
                }
            }
        });
    }

    private void startServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(1234);

                    isRunning = true;

                    while (isRunning) {
                        clientSocket = serverSocket.accept();

                        out = new PrintWriter(clientSocket.getOutputStream(), true);
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                        String receivedMessage;
                        while ((receivedMessage = in.readLine()) != null) {
                            if (receivedMessage.equals("Cerrar")) {
                                isRunning = false;
                                break;
                            }

                            final String finalReceivedMessage = receivedMessage;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText("Dato recibido: " + finalReceivedMessage);
                                }
                            });

                            out.println("Dato recibido");
                        }

                        in.close();
                        out.close();
                        clientSocket.close();
                    }

                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        startButton.setText("Detener servidor");
    }

    private void stopServer() {
        isRunning = false;
        startButton.setText("Iniciar servidor");
    }

}
