package server;

/**
 * Structure to keep client information
 */
public class ClientInformation {
    private String username;
    private CommunicationThread communicationThread;
    private ConnectionState state;

    public ClientInformation(String username, CommunicationThread communicationThread) {
        this.username = username;
        this.communicationThread = communicationThread;
        this.state = ConnectionState.WAITING;
    }
}
