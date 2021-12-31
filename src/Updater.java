import java.util.ArrayList;

public class Updater {
    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public void addClient(ClientHandler clientHandler){
        clients.add(clientHandler);
        System.out.println("[Updater] - added new client with port: " + clientHandler.getPort());
    }
    public void removeClient(int port){
        clients.removeIf(o -> o.getPort() == port);
        System.out.println("[Updater] - removed client with port: " + port);
    }
    public void triggerUpdate(String msg, String ip, int port){
        for(ClientHandler client: clients){
            if(client.getPort()!=port) {
                client.send(ip + ":" + port + ": " + msg);
                System.out.println("[Updater] - Sending: " + msg + " to client on port: " + client.getPort());
            }
        }
    }
}
