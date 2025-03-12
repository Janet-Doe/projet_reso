package server;

public enum ConnectionState {
    WAITING(0),
    CONNECTED(0);

    public int id;

    ConnectionState(int id){
        this.id = id;
    }

    public void setId(int a){
        id = a;
    }

    public int getId(){
        return id;
    }
}
