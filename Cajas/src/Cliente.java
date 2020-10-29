import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Cliente implements Runnable{
    private Semaphore barrera;
    private ColaCajas colaCajas;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Cliente(Semaphore barrera, ColaCajas colaCajas) {
        this.barrera = barrera;
        this.colaCajas = colaCajas;
    }



    @Override
    public void run(){

        try {
            barrera.acquire(1);

            System.out.printf("%s -> %s ha llegado al supermercado.\n",
                    LocalTime.now().format(dateTimeFormatter),
                    Thread.currentThread().getName());



            cogerArticulos();

            colaCajas.atenderCliente();


        } catch (InterruptedException e) {

        }
    }



    public void cogerArticulos() throws InterruptedException{


        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(1,4));

        System.out.printf("%s -> %s ha llegado a la zona de cola única.\n",
                LocalTime.now().format(dateTimeFormatter),
                Thread.currentThread().getName());
    }

}
