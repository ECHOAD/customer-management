package mediator;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApplicationScoped
public class MediatorImpl implements Mediator{
    private final Map<Class<?>, Function<?, ?>> commandHandlers;
    private final Map<Class<?>, Function<?, ?>> queryHandlers;

    @Inject
    public MediatorImpl(
            Instance<CommandHandler<?, ?>> commandHandlers,
            Instance<QueryHandler<?, ?>> queryHandlers
    ) {
        this.commandHandlers = commandHandlers.stream()
                .collect(Collectors.toMap(CommandHandler::getType, h -> h));

        this.queryHandlers = queryHandlers.stream()
                .collect(Collectors.toMap(QueryHandler::getType, h -> h));
    }

    @Override
    public <R> R send(Command<R> command) {
        @SuppressWarnings("unchecked")
        Function<Command<R>, R> handler = (Function<Command<R>, R>) commandHandlers.get(command.getClass());
        if (handler == null) {
            throw new IllegalArgumentException("No handler found for " + command.getClass());
        }
        return handler.apply(command);
    }

    @Override
    public <R> R send(Query<R> query) {
        @SuppressWarnings("unchecked")
        Function<Query<R>, R> handler = (Function<Query<R>, R>) queryHandlers.get(query.getClass());
        if (handler == null) {
            throw new IllegalArgumentException("No handler found for " + query.getClass());
        }
        return handler.apply(query);
    }
}
