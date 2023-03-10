package shoppingmall.shoppingmallspring.repository;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import shoppingmall.shoppingmallspring.domain.Cloth;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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
        String sql = "select * from clothes";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Cloth> list = new ArrayList<>();

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                Cloth cloth = new Cloth();
                cloth.setId(rs.getLong("id"));
                cloth.setType(rs.getString("type"));
                cloth.setColor(rs.getString("color"));
                cloth.setGender(rs.getString("gender"));
                cloth.setSize(rs.getString("size"));
                cloth.setImage(rs.getString("image"));
                list.add(cloth);
            }
        } catch (Exception e){
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }

    @Override
    public Long updateCloth(Long id, Cloth cloth) {
        String sql = "update Clothes set type=?, color=?, image=? where id=?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cloth.getType());
            pstmt.setString(2, cloth.getColor());
            pstmt.setString(3, cloth.getImage());
            pstmt.setLong(4, id);
            pstmt.executeUpdate();

        } catch (Exception e){
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

        return id;
    }

    @Override
    public Optional<Cloth> deleteOneByTypeColor(String type, String color) {
        //못 찾으면 null 반환
        String find_sql = "select * from clothes where type = ? and color = ?";
        String delete_sql = "delete from clothes where type = ? and color = ? and id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Cloth result = new Cloth();
        Long id_save;

        try {
            conn = getConnection();

            pstmt = conn.prepareStatement(find_sql);
            pstmt.setString(1, type);
            pstmt.setString(2, color);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                id_save = rs.getLong("id");
                result.setId(id_save);
                result.setType(rs.getString("type"));
                result.setColor(rs.getString("color"));
                result.setGender(rs.getString("gender"));
                result.setSize(rs.getString("size"));
                result.setImage(rs.getString("image"));
            } else {
                return Optional.empty();
            }

            pstmt = conn.prepareStatement(delete_sql);
            pstmt.setString(1, type);
            pstmt.setString(2, color);
            pstmt.setLong(3, id_save);
            pstmt.executeUpdate();
        } catch (Exception e){
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

        return Optional.ofNullable(result);
    }

    @Override
    public Long getRowLength(){
        String sql = "select count(*) from clothes";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Long result = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                result = rs.getLong(1);
            } else {
                throw new SQLException("id 조회 실패");
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

        return result;
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

    @Override
    public List<Integer> getCounts() {
        String sql =
                "select " +
                        "COUNT(*), " +
                        "SUM(CASE WHEN type = 'tshirt' then 1 else 0 END)," +
                        "SUM(CASE WHEN type = 'pants' then 1 else 0 END)," +
                        "SUM(CASE WHEN type = 'skirt' then 1 else 0 END)," +
                        "SUM(CASE WHEN color = 'blue' then 1 else 0 END)," +
                        "SUM(CASE WHEN color = 'pink' then 1 else 0 END)," +
                        "SUM(CASE WHEN color = 'yellow' then 1 else 0 END)," +
                "from clothes";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> list = new ArrayList<>();

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                list.add(rs.getInt(1));
                list.add(rs.getInt(2));
                list.add(rs.getInt(3));
                list.add(rs.getInt(4));
                list.add(rs.getInt(5));
                list.add(rs.getInt(6));
                list.add(rs.getInt(7));
            }
        } catch (Exception e){
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
        return list;
    }
}
