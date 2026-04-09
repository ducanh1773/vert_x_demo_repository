package com.example.demo_verx.router;

import com.example.demo_verx.handler.UserHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class UserRouter {

  public static Router setup(Vertx vertx, UserHandler handler) {

    Router router = Router.router(vertx);

    router.get("/users").handler(handler::getAll);
    router.post("/users").handler(handler::create);
    router.put("/users/:id").handler(handler::update);
    router.delete("/users/:id").handler(handler::delete);

    return router;
  }
}
