import java.util.*;
public class Speedometer implements Runnable {
    public ArrayList<Client> clientList;
    private Thread thread;

    Speedometer() {
        this.clientList = new ArrayList<Client>();
        thread = new Thread(this);
        thread.start();
    }

    public void printSpeed() {
        Iterator<Client> iterator = clientList.iterator();
        System.out.println("//______________________________________________________");
        while(iterator.hasNext()) {
            Client client = iterator.next();
            System.out.println("Total speed : " + ((double)client.bytesRead / (System.currentTimeMillis() - client.startTime)) / 1024 + "Mb/sec");//Мегайбайт/сек
            System.out.println("Download % :" + ((double)client.bytesRead)/client.fileLength + "\n");
        }
        System.out.println("//______________________________________________________");
    }

    public void run() {
        System.out.println("Speedometer");
        long time = System.currentTimeMillis();
        for(;;){
            if(System.currentTimeMillis() - time > 5000) {
                if(!clientList.isEmpty()) {
                    printSpeed();
                }
                time = System.currentTimeMillis();
            }
        }
    }
}
