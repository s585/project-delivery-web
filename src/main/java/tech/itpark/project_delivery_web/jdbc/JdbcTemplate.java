package tech.itpark.project_delivery_web.jdbc;

import tech.itpark.project_delivery_web.exception.DataAccessException;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcTemplate {
  public int update(Connection conn, String sql, Object... params) {
    try (
        final var stmt = conn.prepareStatement(sql)
    ) {
      var index = 0;
      for (Object param : params) {
        stmt.setObject(++index, param);
      }
      return stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException(e);
    }
  }
}
