import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Caja {
    private int numero;
    private final Random random = new Random();


    public Caja(int numero) {
        this.numero = numero;
    }

    public void pagar() throws InterruptedException{
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(1,5));
    }
}
