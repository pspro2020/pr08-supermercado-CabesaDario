import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore barrera = new Semaphore(50);
        try{
            ColaCajas colaCajas = new ColaCajas(4);
            barrera.acquire(50);
            Thread[] hilosClientes = new Thread[50];
            for (int i = 0; i < 50; i++) {
                hilosClientes[i] = new Thread(new Cliente(barrera,colaCajas), "Cliente #" + i);
            }
            for (int i = 0; i < 50; i++) {
                hilosClientes[i].start();
            }

        }catch (InterruptedException e){

        } finally{
            barrera.release(50); //no está funcionando la barrera
        }



    }



}
