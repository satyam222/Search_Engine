package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class Crawler {
    private HashSet<String> urlSet;
    private int MAX_DEPTH = 2;

    public Crawler() {
        urlSet = new HashSet<>();
    }

//
public void getPageTextsAndLinks(String url, int depth) {
    if (urlSet.contains(url)) {
        return; // Skip this URL if it has already been visited in this session
    }
    if (depth >= MAX_DEPTH) {
        return; // Stop crawling when depth exceeds the maximum
    }

    urlSet.add(url); // Mark the URL as visited

    try {
        Document document = Jsoup.connect(url).timeout(5000).get();
        // Indexer starts here
        Indexer indexer = new Indexer(document, url);
        System.out.println(document.title());

        // Find and process links on the page
        Elements availableLinksOnPage = document.select("a[href]");
        for (Element currentLink : availableLinksOnPage) {
            String nextUrl = currentLink.attr("abs:href");
            getPageTextsAndLinks(nextUrl, depth);
        }
    } catch (IOException ioException) {
        ioException.printStackTrace();
    }
}


    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.getPageTextsAndLinks("https://www.javatpoint.com", 0);
    }
}




//package org.example;
//
//        import org.jsoup.Jsoup;
//        import org.jsoup.nodes.Document;
//        import org.jsoup.nodes.Element;
//        import org.jsoup.select.Elements;
//
//        import java.io.IOException;
//        import java.util.HashSet;
//
//public class Crawler {
//    private HashSet<String> urlSet;
//    private int MAX_DEPTH = 2;
//
//    public Crawler() {
//        urlSet = new HashSet<>();
//    }
//
//    public void getPageTextsAndLinks(String url, int depth) {
//        if (urlSet.contains(url)) {
//            return; // Stop crawling if the URL has already been visited
//        }
//        if (depth >=MAX_DEPTH) {
//            return; // Stop crawling when depth exceeds the maximum
//        }
//        if(urlSet.add(url)){
//            System.out.println(url);
//        }
//        depth++;
//
//
//
//
//        try {
//            Document document = Jsoup.connect(url).timeout(5000).get();
//            // Indexer starts here
//            Indexer indexer=new Indexer(document,url);
//            System.out.println(document.title());
//
//            // Find and process links on the page
//            Elements availableLinksOnPage = document.select("a[href]");
//            for (Element currentLink : availableLinksOnPage) {
//                String nextUrl = currentLink.attr("abs:href");
//                getPageTextsAndLinks(nextUrl, depth );
//            }
//        } catch (IOException ioException) {
//            ioException.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        Crawler crawler = new Crawler();
//        crawler.getPageTextsAndLinks("https://www.javatpoint.com", 0);
//    }
//}
