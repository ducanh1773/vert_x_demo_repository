package com.example.demo_verx;

//import com.example.demo_verx.config.DatabaseConfig;

import com.example.demo_verx.config.DatabaseConfig;
import com.example.demo_verx.handler.UserHandler;
import com.example.demo_verx.repository.UserRepository;
import com.example.demo_verx.service.UserService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.mysqlclient.MySQLPool;

public class MainVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new MainVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) {

    MySQLPool pool = DatabaseConfig.getPool(vertx);

    // 1. Kiểm tra kết nối thử tới MySQL
    pool.query("SELECT 1").execute(ar -> {
      if (ar.succeeded()) {
        System.out.println("✅ MySQL connected successfully!");

        // 2. Nếu kết nối DB thành công, mới khởi tạo Repository/Service và Server
        UserRepository repo = new UserRepository(pool);
        UserService service = new UserService(repo);
        UserHandler handler = new UserHandler(service);

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.get("/users").handler(handler::getAll);
        router.post("/users").handler(handler::create);
        router.put("/users/:id").handler(handler::update);
        router.delete("/users/:id").handler(handler::delete);

        vertx.createHttpServer()
          .requestHandler(router)
          .listen(8888, http -> {
            if (http.succeeded()) {
              System.out.println("✅ Server started at http://localhost:8888");
              startPromise.complete();
            } else {
              System.out.println("❌ Failed to start HTTP server");
              startPromise.fail(http.cause());
            }
          });
      } else {
        // 3. Nếu kết nối DB thất bại, in ra lỗi và không chạy App
        System.err.println("❌ Could not connect to MySQL: " + ar.cause().getMessage());
        startPromise.fail(ar.cause());
      }
    });
  }
}

