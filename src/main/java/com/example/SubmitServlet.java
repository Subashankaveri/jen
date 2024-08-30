package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubmitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String phone = request.getParameter("phone");

        // Database connection parameters
        String url = "jdbc:postgresql://localhost:5432/subash";
        String user = "postgres";
        String password = "admin";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Load PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");
            // Connect to PostgreSQL database
            connection = DriverManager.getConnection(url, user, password);

            String sql = "INSERT INTO users (name, age, phone) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, Integer.parseInt(age));
            preparedStatement.setString(3, phone);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                response.getWriter().println("Data inserted successfully!");
            } else {
                response.getWriter().println("Failed to insert data.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error occurred: " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}