import java.util.Random;
import java.util.concurrent.*;

public class ProducerConsumerExample {

    // Shared BlockingQueue for producer-consumer communication
    private static final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);

    // Producer that generates random integers and puts them into the queue
    static class Producer implements Runnable {
        private final Random random = new Random();

        @Override
        public void run() {
            try {
                while (true) {
                    int value = random.nextInt(100); // Generate random integer
                    queue.put(value); // Put value into the queue
                    System.out.println("Produced: " + value);
                    Thread.sleep(100); // Simulate work
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Consumer that takes integers from the queue and processes them
    static class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Integer value = queue.take(); // Take value from the queue
                    System.out.println("Consumed: " + value);
                    Thread.sleep(150); // Simulate work
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // ExecutorService with a fixed thread pool to manage consumers
        int numConsumers = 3;
        ExecutorService executor = Executors.newFixedThreadPool(numConsumers);

        // Start the Producer thread
        Thread producerThread = new Thread(new Producer());
        producerThread.start();

        // Start Consumer threads
        for (int i = 0; i < numConsumers; i++) {
            executor.submit(new Consumer());
        }

        // Wait for a while before shutting down for demonstration purposes
        Thread.sleep(5000);
        
        // Gracefully shut down the executor and producer thread
        executor.shutdown();
        producerThread.interrupt();
    }
}
Multi-threaded Producer-Consumer System with Thread Pool and Blocking Queues
