package mediator;

import java.util.function.Function;

public interface QueryHandler<C extends Query<R>, R> extends Function<C, R> {
    Class<C> getType();
}