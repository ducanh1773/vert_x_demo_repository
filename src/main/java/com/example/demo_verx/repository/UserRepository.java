package com.example.demo_verx.repository;

import io.vertx.core.Future;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;

public class UserRepository {

  private final MySQLPool client;

  public UserRepository(MySQLPool client) {
    this.client = client;
  }

  // GET ALL
  public Future<RowSet<Row>> getAll() {
    return client.query("SELECT * FROM users").execute();
  }

  // GET BY ID
  public Future<RowSet<Row>> getById(int id) {
    return client
      .preparedQuery("SELECT * FROM users WHERE id = ?")
      .execute(Tuple.of(id));
  }

  // INSERT
  public Future<Void> insert(String name, String email, double salary) {
    return client
      .preparedQuery("INSERT INTO users(name,email,salary) VALUES (?,?,?)")
      .execute(Tuple.of(name, email, salary))
      .mapEmpty();
  }

  // UPDATE
  public Future<Void> update(int id, String name, String email, double salary) {
    return client
      .preparedQuery("UPDATE users SET name=?, email=?, salary=? WHERE id=?")
      .execute(Tuple.of(name, email, salary, id))
      .mapEmpty();
  }

  // DELETE
  public Future<Void> delete(int id) {
    return client
      .preparedQuery("DELETE FROM users WHERE id=?")
      .execute(Tuple.of(id))
      .mapEmpty();
  }

}
