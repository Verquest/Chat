import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private PrintWriter output;
    private BufferedReader input;
    private ArrayList<String> receivedMessages = new ArrayList<>();
    private Updater updater;
    public ClientHandler(Socket clientSocket, Updater updater) {
        this.clientSocket = clientSocket;
        this.updater = updater;
        try {
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void send(String message){
        output.println(message);
    }
    public String get(){
        try{
            return input.readLine();
        } catch(IOException e){
            System.out.println(e);
            return "ERROR";
        }
    }
    public int getPort(){
        return clientSocket.getPort();
    }
    @Override
    public void run() {
        try{
            String line;
            while((line = input.readLine())!=null){
                receivedMessages.add(line);
                updater.triggerUpdate(line, clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort());
                System.out.println("[Client handler] - received message from client on port: " + clientSocket.getPort());
            }
        } catch (SocketException e){
            updater.removeClient(clientSocket.getPort());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
