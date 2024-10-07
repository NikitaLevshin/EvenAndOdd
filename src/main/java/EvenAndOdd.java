public class EvenAndOdd {

    private static final Object monitor = new Object();
    private static int count = 1;
    private static final int limit = 1000;


    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while(count < limit) {
                synchronized (monitor) {
                    if (count % 2 == 0) {
                        System.out.println(count);
                        count++;
                        monitor.notify();
                    } else {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });

        Thread t2 = new Thread(() -> {
            while(count < limit) {
                synchronized (monitor) {
                    if (count % 2 != 0) {
                        System.out.println(count);
                        count++;
                        monitor.notify();
                    } else {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });

        t1.start();
        t2.start();

    }
}
