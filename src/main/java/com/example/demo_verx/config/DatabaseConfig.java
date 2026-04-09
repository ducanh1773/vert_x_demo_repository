package com.example.demo_verx.config;

import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLBuilder;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.mysqlclient.MySQLPool;
public class DatabaseConfig {
  public static MySQLPool getPool(Vertx vertx) {

    MySQLConnectOptions connectOptions = new MySQLConnectOptions()
      .setPort(3306)
      .setHost("localhost")
      .setDatabase("demo_sp")
      .setUser("root")
      .setPassword("123456");

    PoolOptions poolOptions = new PoolOptions()
      .setMaxSize(10);

    return MySQLPool.pool(vertx, connectOptions, poolOptions);
  }
}
