package vn.rideshare.shared;

import org.springframework.context.ApplicationEvent;

public class AfterFindAndModifyEvent<T> extends ApplicationEvent {
    private static final long serialVersionUID = 1L;

    public AfterFindAndModifyEvent(T source) {
        super(source);
    }

    @Override
    public T getSource(){
        return (T) super.getSource();
    }
}
