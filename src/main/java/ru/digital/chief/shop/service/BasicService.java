package ru.digital.chief.shop.service;

import ru.digital.chief.shop.exception.ServiceException;
import ru.digital.chief.shop.model.dto.ProductDto;

import java.util.List;

public interface BasicService<T> {
    void deleteById(long id) throws ServiceException;

    T findById(long id) throws ServiceException;

    List<T> findPage(int page, int size);

    T create(T dto) throws ServiceException;

    T update(T dto) throws ServiceException;
}
