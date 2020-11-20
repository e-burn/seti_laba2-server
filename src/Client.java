public class Client {
    public long bytesRead;
    public long startTime;
    public long fileLength;
    public Speedometer speedometer;


    Client(long startTime, long fileLength, Speedometer speedometer) {
        this.bytesRead = 0;
        this.startTime = startTime;
        this.fileLength = fileLength;
        this.speedometer = speedometer;
    }
    public void addClientToSpeedometer() {
        speedometer.clientList.add(this);
    }

    public void removeClientFromSpeedometer() {
        speedometer.clientList.remove(this);
    }

}
