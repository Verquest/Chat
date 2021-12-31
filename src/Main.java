class Main {
    public static void main(String[] args) {
        new Thread(new ChatServer(4867)).start();
    }
}
