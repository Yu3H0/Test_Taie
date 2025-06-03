

package pascal.taie.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.CompletableFuture;

public final class SerializationUtils {

    private SerializationUtils() {
    }

    /**
     * Deep copy an object by serialization.
     */
    @SuppressWarnings("unchecked")
    public static <T> T serializedCopy(T o) {
        try {
            PipedOutputStream pipeOut = new PipedOutputStream();
            PipedInputStream pipeIn = new PipedInputStream(pipeOut);
            CompletableFuture.runAsync(() -> {
                try {
                    new ObjectOutputStream(pipeOut).writeObject(o);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            return (T) CompletableFuture.supplyAsync(() -> {
                try {
                    return new ObjectInputStream(pipeIn).readObject();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }).get();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
