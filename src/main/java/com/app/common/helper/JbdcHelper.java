package com.app.common.helper;

import com.app.common.configs.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author InuHa
 */
public class JbdcHelper {

    public static ResultSet query(String query, Object... args) throws SQLException {
        return query(query, false, args);
    }

    public static ResultSet query(String query, boolean autoCloseConnect, Object... args) throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement stmt = null;
        try {
            stmt = getStatement(query, args);
            resultSet = stmt.executeQuery();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        finally {
            if (autoCloseConnect) {
                assert stmt != null;
                close(resultSet);
            }
        }
        return resultSet;
    }

    public static int update(String query, Object... args) throws SQLException {
        return update(query, false, args);
    }

    public static int update(String query, boolean autoCloseConnect, Object... args) throws SQLException {
        int result = 0;
        PreparedStatement stmt = null;

        try {
            stmt = getStatement(query, args);
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        finally {
            if (autoCloseConnect) {
                assert stmt != null;
                close(stmt);
            }
        }
        return result;
    }

    public static Object value(String query, Object... args) throws SQLException {
        ResultSet resultSet = null;
        Statement stmt = null;
        try {
            resultSet = query(query, false, args);
            stmt = resultSet.getStatement();
            if (resultSet.next()) {
                return resultSet.getObject(0);
            }
        } catch(Exception e) {
            e.printStackTrace(System.out);
        }
        finally {
            assert stmt != null;
            close(resultSet);
        }
        return null;
    }

    public static PreparedStatement getStatement(String query, Object... args) throws SQLException {
        Connection connection = DBConnect.getInstance().getConnect();
        PreparedStatement stmt = null;

        if (query.trim().startsWith("{")) {
            String sql = "{CALL " + getProcedureName(query) + "(";
            if (args.length > 0) {
                String[] fill = new String[args.length];
                Arrays.fill(fill, "?");
                sql += String.join(",", fill);
            }
            sql += ")}";
            stmt = connection.prepareCall(sql);
        } else {
            stmt = connection.prepareStatement(query);
        }

        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }

        return stmt;
    }

    public static String getProcedureName(String query) {
        query = query.trim();
        String regex = "\\{\\s*(?:CALL )?\\s*([^(\\}\\s+]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(query);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }

    public static void close(AutoCloseable close) {
        try {
            DBConnect.getInstance().close(close);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

}
