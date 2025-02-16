package org.enricogiurin.ocp17.book.ch15;

import static org.enricogiurin.ocp17.book.ch15.SetupDataBase.JDBC_URL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class StoredProcedure {

  public static void main(String[] args) throws SQLException {
    new StoredProcedure().magic_number();
  }

  //out parameters
  void magic_number() throws SQLException {
    var sql = "{ ?= call magic_number(?) }";
    try (Connection conn = DriverManager.getConnection(JDBC_URL);
        var cs = conn.prepareCall(sql)) {
      cs.registerOutParameter(1, Types.INTEGER);
      cs.execute();
      //num is defined in the body of the procedure. Check SetupDataBase
      System.out.println(cs.getInt("num"));
    }
  }

  //neither IN nor OUT parameters
  void read_e_names() throws SQLException {
    var sql = "{call read_e_names()}";
    try (Connection connection = DriverManager.getConnection(JDBC_URL);
        CallableStatement callableStatement = connection.prepareCall(sql);
        ResultSet resultSet = callableStatement.executeQuery()
    ) {
      while (resultSet.next()) {
        System.out.println(resultSet.getString("name"));
        //Elsa
        //Ester
        //Eddie
      }
    }
  }

  //passing IN parameter
  void read_names_by_letter() throws SQLException {
    var sql = "{call read_names_by_letter(?) }";
    try (Connection connection = DriverManager.getConnection(JDBC_URL);
        CallableStatement callableStatement = connection.prepareCall(sql)) {
      callableStatement.setString("prefix", "z");
      try (ResultSet resultSet = callableStatement.executeQuery()) {
        while (resultSet.next()) {
          System.out.println(resultSet.getString("name"));
          //Zelda
          //Zoe
        }
      }
    }
  }

  //INOUT parameter
  void double_number() throws SQLException {
    var sql = "{call double_number(?) }";
    try (Connection connection = DriverManager.getConnection(JDBC_URL);
        CallableStatement cs = connection.prepareCall(sql)) {
      cs.setInt("num", 23);
      cs.registerOutParameter("num", Types.INTEGER);
      cs.execute();
      System.out.println(cs.getInt("num"));
    }
  }

  //two in, on out


}
