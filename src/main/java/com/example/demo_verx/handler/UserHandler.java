package com.example.demo_verx.handler;

import com.example.demo_verx.service.UserService;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UserHandler {
  private final UserService service;

  public UserHandler(UserService service) {
    this.service = service;
  }

  // GET ALL
  public void getAll(RoutingContext ctx) {
    service.getAllUsers() // Gọi xuống service
      .onSuccess(rows -> {
        JsonArray users = new JsonArray();

        // Duyệt qua từng dòng kết quả từ MySQL
        rows.forEach(row -> {
          users.add(new JsonObject()
            .put("id", row.getInteger("id"))
            .put("name", row.getString("name"))
            .put("email", row.getString("email"))
          );
        });

        // Gửi về trình duyệt dưới dạng JSON
        ctx.response()
          .putHeader("content-type", "application/json")
          .end(users.encodePrettily());
      })
      .onFailure(err -> {
        ctx.response().setStatusCode(500).end(err.getMessage());
      });
  }

  // CREATE
  public void create(RoutingContext ctx) {
    JsonObject body = ctx.body().asJsonObject();

    service.createUser(
      body.getString("name"),
      body.getString("email"),
      body.getDouble("salary")
    ).onSuccess(v -> ctx.response().end("Created"));
  }

  // UPDATE
  public void update(RoutingContext ctx) {
    int id = Integer.parseInt(ctx.pathParam("id"));
    JsonObject body = ctx.body().asJsonObject();

    service.updateUser(id,
      body.getString("name"),
      body.getString("email"),
      body.getDouble("salary")
    ).onSuccess(v -> ctx.response().end("Updated"));
  }

  // DELETE
  public void delete(RoutingContext ctx) {
    int id = Integer.parseInt(ctx.pathParam("id"));

    service.deleteUser(id)
      .onSuccess(v -> ctx.response().end("Deleted"));
  }
}
