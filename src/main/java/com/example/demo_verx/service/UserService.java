package com.example.demo_verx.service;

import com.example.demo_verx.repository.UserRepository;
import io.vertx.core.Future;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;

public class UserService {

  private final UserRepository repo;

  public UserService(UserRepository repo) {
    this.repo = repo;
  }

  public Future<RowSet<Row>> getAllUsers() {
    return repo.getAll();
  }

  public Future<Void> createUser(String name, String email, double salary) {
    return repo.insert(name, email, salary);
  }

  public Future<Void> updateUser(int id, String name, String email, double salary) {
    return repo.update(id, name, email, salary);
  }

  public Future<Void> deleteUser(int id) {
    return repo.delete(id);
  }
}
