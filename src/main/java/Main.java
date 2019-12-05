import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Flux
                .interval(Duration.ofMillis(10))
                .map( __ -> ThreadLocalRandom.current().nextInt(0, 100))
                .scan(new ArrayList<Integer>(), (collection, nextItem) -> {
                    final int size = collection.size();

                    if(size == 0) {
                        collection.add(nextItem);
                    } else {
                        for (int i = 0; i < size; i++) {
                            Integer currentItem = collection.get(i);
                            if (nextItem <= currentItem) {
                                collection.add(i, nextItem);
                                break;
                            }
                        }
                    }

                    return collection;
                })
                .flatMapIterable(Function.identity())
                .log()
                .blockLast();
    }
}
