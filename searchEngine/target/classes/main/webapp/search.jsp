
<%@page import = "java.util.ArrayList"%>
<%@page import = "com.Accio.SearchResult"%>
<html>
<head>
<link rel = "stylesheet" type = "text/css" href = "style.css">
</head>
    <body>
       <h1>Simple Search Table</h1>
        <form action = "search">
            <input type = "text" name = "keyword">
            <button type = "submit">Search</button>
        </form>
         <form action = "History">
                <button type = "submit">History</button>
            </form>
        <div class = "resultTable">
        <table border = 2>
            <tr>
                <td>Title</td>
                <td>Link</td>
            </tr>
            <%
                //Get results from search servlet
                ArrayList<SearchResult> results = (ArrayList<SearchResult>)request.getAttribute("results");
                //Iterate for every data present in results array
                for(SearchResult result:results){
            %>
                <tr>
                    <td><%out.println(result.getTitle());%></td>
                    <td><a href="<%out.println(result.getLink());%>"><%out.println(result.getLink());%></a></td>
                </tr>
            <%
                }
            %>
        </table>
        </div>
    </body>
</html>