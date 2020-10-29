import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ColaCajas {
    private final Semaphore semaphore;
    private final Caja[] cajas;
    private final boolean[] cajaDisponible;
    private final Lock reentrantLock = new ReentrantLock(true);
    private static final int NO_CAJA = -1;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ColaCajas(int numeroCajas) {
        semaphore = new Semaphore(numeroCajas, true);
        cajas = new Caja[numeroCajas];
        cajaDisponible = new boolean[numeroCajas];
        for (int i = 0; i < numeroCajas; i++) {
            cajas[i] = new Caja(i);
            cajaDisponible[i] = true;
        }
    }

    public void atenderCliente() throws InterruptedException {
        try {

            System.out.printf("%s -> %s ha llegado a la zona de cola única\n",
                    LocalTime.now().format(dateTimeFormatter),
                    Thread.currentThread().getName());

            semaphore.acquire();

            System.out.printf("%s -> %s ha pasado la zona de cola única.\n",
                    LocalTime.now().format(dateTimeFormatter),
                    Thread.currentThread().getName());

            int numeroCaja = escogerCaja();

            if (numeroCaja != NO_CAJA) {


                System.out.printf("%s -> %s ha comenzado a ser atendido en la caja %d.\n",
                        LocalTime.now().format(dateTimeFormatter),
                        Thread.currentThread().getName(), numeroCaja);

                cajas[numeroCaja].pagar();
                cajaDisponible[numeroCaja] = true;

                System.out.printf("%s -> %s ha salido de la caja %d.\n",
                        LocalTime.now().format(dateTimeFormatter),
                        Thread.currentThread().getName(), numeroCaja);



            }

        } finally {
            // This is called even if an exception is thrown.
            semaphore.release();
        }
    }

    private int escogerCaja() {
        reentrantLock.lock();
        try {
            for (int i = 0; i < cajas.length; i++) {
                if (cajaDisponible[i]) {
                    cajaDisponible[i] = false;
                    return i;
                }
            }
        } finally {
            reentrantLock.unlock();
        }
        return NO_CAJA;
    }
}
