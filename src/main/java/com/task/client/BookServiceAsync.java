package com.task.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.task.server.domain.Book;

import java.util.List;

public interface BookServiceAsync {

    void list(AsyncCallback<List<Book>> async);

    void save(Book book, AsyncCallback<Book> async);

    void delete(Book book, AsyncCallback<Boolean> async);

}
