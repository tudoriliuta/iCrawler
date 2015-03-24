package iCrawler;

import java.io.IOException;
import java.sql.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

 
public class Main {
	public static Database database = new Database();
 
	public static void main(String[] args) throws SQLException, IOException {
		database.sqlStart1("TRUNCATE Resource;");
		processPage("http://www.mit.edu");
	}
 
	public static void processPage(String URL) throws SQLException, IOException{
		//check if the given URL is already in database
		String sql = "select * from Resource where URL = '"+URL+"'";
		ResultSet rs = database.sqlStart2(sql);
		if(rs.next()){
 
		}else{
			//store the URL to database to avoid parsing again
			sql = "INSERT INTO  `iCrawler`.`Resource` " + "(`URL`) VALUES " + "(?);";
			PreparedStatement stmt = database.initiate.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, URL);
			stmt.execute();
 
			//get useful information
			Document doc = Jsoup.connect("http://www.mit.edu/").get();
 
			if(doc.text().contains("technology")){
				System.out.println(URL);
			}
 
			//get links
			Elements questions = doc.select("a[href]");
			for(Element link: questions){
				if(link.attr("href").contains("mit.edu"))
					processPage(link.attr("abs:href"));
			}
		}
	}
}