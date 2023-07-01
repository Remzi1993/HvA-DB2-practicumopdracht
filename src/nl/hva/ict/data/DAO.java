package nl.hva.ict.data;

import java.util.List;

/**
 * Interface for DAO abstract classes. Ik pas de create, read, update, and delete (CRUD) principe toe.
 * Design patterns:
 * Create, read, update, and delete (CRUD) principe is hetzelfde als:
 * INSERT, SELECT, UPDATE, and DELETE
 * @param <T> Het model wat je gebruikt
 * @author HvA FDMCI HBO-ICT - Remzi Cavdar - remzi.cavdar@hva.nl
 */
public interface DAO<T> {
    // Create / INSERT
    boolean create(T object);
    // Read / SELECT
    List<T> read();
    // Update / UPDATE
    boolean update(T object);
    // Delete / DELETE
    boolean delete(T object);
}