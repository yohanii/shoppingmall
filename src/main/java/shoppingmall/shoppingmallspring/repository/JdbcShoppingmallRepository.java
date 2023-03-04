package shoppingmall.shoppingmallspring.repository;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import shoppingmall.shoppingmallspring.domain.Cloth;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcShoppingmallRepository implements ShoppingmallRepository{

    private final DataSource dataSource;

    public JdbcShoppingmallRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Cloth save(Cloth cloth) {
        String sql = "insert into clothes(type, gender, size, color, image) values(?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, cloth.getType());
            pstmt.setString(2, cloth.getGender());
            pstmt.setString(3, cloth.getSize());
            pstmt.setString(4, cloth.getColor());
            pstmt.setString(5, cloth.getImage());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                cloth.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return cloth;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Cloth> findById(Long id) {
        String sql = "select * from clothes where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            Cloth result = new Cloth();
            if (rs.next()) {
                result.setId(rs.getLong("id"));
                result.setType(rs.getString("type"));
                result.setColor(rs.getString("color"));
                result.setGender(rs.getString("gender"));
                result.setSize(rs.getString("size"));
                result.setImage(rs.getString("image"));
            } else {
                throw new SQLException("id로 옷 조회 실패");
            }
            return Optional.ofNullable(result);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Cloth> findAll() {
        return null;
    }

    @Override
    public Optional<Cloth> deleteByTypeColor(String type, String color) {
        return Optional.empty();
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
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
