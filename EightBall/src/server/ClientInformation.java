package server;

public class ClientInformation {
    private String username;
    private CommunicationThread communicationThread;
    private ConnectionState state;

    public ClientInformation(String username, CommunicationThread communicationThread) {
        this.username = username;
        this.communicationThread = communicationThread;
        this.state = ConnectionState.WAITING;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CommunicationThread getCommunicationThread() {
        return communicationThread;
    }

    public void setCommunicationThread(CommunicationThread communicationThread) {
        this.communicationThread = communicationThread;
    }

    public ConnectionState getState() {
        return state;
    }

    public void setState(ConnectionState state) {
        this.state = state;
    }
}
