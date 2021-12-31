import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatServer implements Runnable {
    private int port;
    private ServerSocket server;
    private InetAddress ipAddress;
    private Updater updater = new Updater();

    public ChatServer(int port) {
        System.out.println("[Server] - starting...");
        this.port = port;
        try {
            server = new ServerSocket(port, 50);
        } catch (UnknownHostException e) {
            System.out.println("Couldnt resolve address.");
        } catch (IOException e) {
            System.out.println("Couldnt create serverSocket.");
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("[Server] - awaiting new connection...");
                Socket client = server.accept();
                System.out.println("[Server] - accepted new client.");
                ClientHandler newClient = new ClientHandler(client, updater);
                updater.addClient(newClient);
                new Thread(newClient).start();
            }
        } catch (IOException e) {
            System.out.println("[Server] - something went wrong.");
        }
    }
}
