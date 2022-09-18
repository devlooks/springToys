package com.example.demo.repository;

import com.example.demo.domain.TodoContext;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcTodoRepository implements TodoRepository{

    private final DataSource dataSource;

    public JdbcTodoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public TodoContext save(TodoContext todoContext) {
        String sql = "insert into todotbl(context, priority) values(?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, todoContext.getTodoContext());
            pstmt.setInt(2, todoContext.getPriority());

            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                todoContext.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }

            return todoContext;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

    }

    @Override
    public Optional<TodoContext> findById(Long id) {
        String sql = "select * from todotbl where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                TodoContext todoContext = new TodoContext();
                todoContext.setId(rs.getLong("id"));
                todoContext.setTodoContext(rs.getString("context"));
                todoContext.setPriority(Integer.parseInt(rs.getString("priority")));
                return Optional.of(todoContext);
            } else {
                return Optional.empty();
            }

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<TodoContext> findAll() {
        String sql = "select * from todotbl";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<TodoContext> todoContexts = new ArrayList<>();

            while(rs.next()) {
                TodoContext todoContext = new TodoContext();
                todoContext.setId(rs.getLong("id"));
                todoContext.setTodoContext(rs.getString("context"));
                todoContext.setPriority(rs.getInt("priority"));
                todoContexts.add(todoContext);
            }

            return todoContexts;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<TodoContext> findByContext(String context) {
        String sql = "select * from todotbl where context like '%" + context + "%'";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<TodoContext> todoContexts = new ArrayList<>();

            while(rs.next()) {
                TodoContext todoContext = new TodoContext();
                todoContext.setId(rs.getLong("id"));
                todoContext.setTodoContext(rs.getString("context"));
                todoContext.setPriority(rs.getInt("priority"));
                todoContexts.add(todoContext);
            }

            return todoContexts;

        }catch (Exception e) {
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<TodoContext> sortedByPriority(String sort) {
        String sql = "select * from todotbl order by id " + sort;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<TodoContext> todoContexts = new ArrayList<>();

            while(rs.next()) {
                TodoContext todoContext = new TodoContext();
                todoContext.setId(rs.getLong("id"));
                todoContext.setTodoContext(rs.getString("context"));
                todoContext.setPriority(rs.getInt("priority"));
                todoContexts.add(todoContext);
            }

            return todoContexts;

        }catch (Exception e) {
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
