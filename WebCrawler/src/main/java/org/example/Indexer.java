package org.example;

import org.jsoup.nodes.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.example.databaseConnection.getConnection;

public class Indexer {
    private static Connection connection = null;

    public Indexer(Document document, String url) {
        // Select important elements from the document
        String title = document.title();
        String link = url;
        String text = document.text();

        // Save these elements to the database
        try {
            // Initialize the database connection
            if (connection == null) {
                connection = getConnection(); // Assuming this method returns a valid database connection
            }

            // Define the SQL query to insert data into the database


            // Create a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT into pages VALUES (?, ?, ?)");

            // Set the values for the prepared statement
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, link);
            preparedStatement.setString(3, text);

            // Execute the SQL query to insert the data
            preparedStatement.executeUpdate();

//            // Close the prepared statement
//            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors here
        }
    }
}


//

    //Here are the changes made:

       // You should call getConnection() to get a database connection. I assumed that you have a method named getConnection in your databaseConnection class that returns a valid connection.

        //In the PreparedStatement creation, you should use connection.prepareStatement instead of Connection.PreparedStatement. The connection variable is the reference to the database connection.

       // The SQL query in insertQuery should specify the table name where you want to insert the data. I assumed the table name is "pages," but you should adjust it to match your database schema.

      //  Ensure that you have properly defined your getConnection() method in the databaseConnection class to return a valid database connection based on your database configuration.
     // Select pageTitle,pageLink,(length(lower(pageText))-length(replace(lower(pageText),'java','')))/length('java')as countoccurence from pages order by countoccurence desc limit 30;//ranking algo top 30reult