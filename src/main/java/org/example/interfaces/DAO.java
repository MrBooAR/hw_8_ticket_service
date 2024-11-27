package org.example.interfaces;

import java.util.List;

public interface DAO<T> {
    void save(T t); // Сохранить запись
    T getById(int id); // Получить запись по ID
    List<T> getAll(); // Получить все записи
    void update(T t); // Обновить запись
    void delete(int id); // Удалить запись
}