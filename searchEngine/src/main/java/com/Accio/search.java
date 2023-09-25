package com.Accio;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import com.Accio.SearchResult;

@WebServlet("/search")//this is route same as (https://www.google.com/search) which produce result and com.Accio.search class behaving as web Servelet,This annotation is used to specify the URL pattern ("/com.Accio.search") that maps to this servlet. When a user accesses a URL that matches this pattern, the servlet will be invoked.
public class search extends  HttpServlet //: This declares a Java class named "com.Accio.search" that extends the HttpServlet class, making it a servlet.
{
    protected  void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String keyword=request.getParameter("keyword");// This line retrieves the value of the "keyword" parameter from the HTTP request's query parameters. This parameter is typically sent by a client when making a GET request with a query string like "?keyword=example."
        Connection connection= databaseConnection.getConnection();
        try {
            //store the keyword or query into history table
            PreparedStatement preparedStatement = connection.prepareStatement("Insert into history values(?, ?)");
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, "http://localhost:8080/SearchEngineAccio/Search?keyword="+keyword);
            preparedStatement.executeUpdate();
            //getting result after running the rank query
            ResultSet resultSet = connection.createStatement().executeQuery("Select pageTitle,pageLink,(length(lower(pageText))-length(replace(lower(pageText),'" + keyword.toLowerCase() + "','')))/length('" + keyword.toLowerCase() + "')as countoccurence from pages order by countoccurence desc limit 30;");
            ArrayList<SearchResult> results = new ArrayList<SearchResult>();
            //transferring values from resultset to results Arraylist


            while (resultSet.next()) {
                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(resultSet.getString("pageTitle"));
                searchResult.setLink(resultSet.getString("pageLink"));
                results.add(searchResult);
            }
            //display arryalist in console
            for(SearchResult result:results){
                System.out.println(result.getTitle()+"\n"+result.getLink()+"\n");
            }
            //this request is forwarded to frontend
            request.setAttribute("results", results);
            //sending to frontend com.Accio.search.jsp file
            request.getRequestDispatcher("/search.jsp").forward(request,response);


            response.setContentType("Text/html");//This sets the content type of the HTTP response to "text/html," indicating that the servlet will be generating HTML content in the response.
            PrintWriter out = response.getWriter();//This obtains a PrintWriter object, which is used to write the HTML content to the response.
        }catch (SQLException | ServletException sqlException){
            sqlException.printStackTrace();

        }

    }
}