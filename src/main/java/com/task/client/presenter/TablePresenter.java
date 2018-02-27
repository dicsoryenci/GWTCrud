package com.task.client.presenter;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.task.client.BookServiceAsync;
import com.task.client.event.TableEvents.AddBookEvent;
import com.task.client.event.TableEvents.DeleteBookEvent;
import com.task.client.event.TableEvents.EditBookEvent;
import com.task.server.domain.Book;

import java.util.List;

public class TablePresenter implements Presenter {

    public interface Display {
        HasClickHandlers getAddButton();
        HasClickHandlers getDeleteButton();
        HasClickHandlers getUpdateButton();
        void setData(List<Book> data);
        int getClickedRow(ClickEvent event);
        Widget asWidget();
    }

    private final BookServiceAsync rpcService;
    private final HandlerManager eventBus;
    private final Display display;

    public TablePresenter(BookServiceAsync rpcService, HandlerManager eventBus, Display view) {
        this.rpcService = rpcService;
        this.eventBus = eventBus;
        this.display = view;
    }

    public void bind() {
        display.getAddButton().addClickHandler(event -> eventBus.fireEvent(new AddBookEvent()));
        display.getDeleteButton().addClickHandler(event -> eventBus.fireEvent(new DeleteBookEvent(display.getClickedRow(event))));
        display.getUpdateButton().addClickHandler(event -> eventBus.fireEvent(new EditBookEvent(display.getClickedRow(event))));
    }


    @Override
    public void go(HasWidgets container) {
        bind();
        container.clear();
        container.add(display.asWidget());
        fetchTable();
    }

    private void fetchTable()
    {
        rpcService.list(new AsyncCallback<List<Book>>() {
            @Override
            public void onFailure(Throwable throwable) {
            }

            @Override
            public void onSuccess(List<Book> books) {
                display.setData(books);
            }
        });
    }




}
