package vn.rideshare.shared;

import org.springframework.context.ApplicationListener;

public abstract class FindAndModifyEventListener<E> implements ApplicationListener<AfterFindAndModifyEvent<?>> {

    public void onAfterFindAndModify(AfterFindAndModifyEvent<E> event){}

    @Override
    public void onApplicationEvent(AfterFindAndModifyEvent<?> event) {
        if (event instanceof AfterFindAndModifyEvent){
            this.onAfterFindAndModify((AfterFindAndModifyEvent<E>) event);
        }

    }
}
