package iCrawler;
import java.util.Scanner;
import java.io.IOException;
import java.sql.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

 
public class Main {
	public static Database database = new Database();
	static Scanner userIn= new Scanner(System.in);
	public static void main(String[] args) throws SQLException, IOException {
		System.out.println("Introduce the search term");
		String searchIt=userIn.nextLine();
		
		System.out.println("Introduce the full link (ex. www.mcgill.ca)");
		String a=userIn.nextLine();
		String webUrl = "http://"+a;
		database.sqlStart1("TRUNCATE Resource;");
		processPage(webUrl,searchIt);
	}
 
	public static void processPage(String URL, String searchTerm) throws SQLException, IOException{
		//check if the given URL is already in database
		
		String addRess=URL.substring(11);
		System.out.println(addRess);
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
			
			Document doc = Jsoup.connect(URL).get();
			
			if(doc.text().contains(searchTerm)){
				System.out.println(URL);
			}
 
			//get links
			Elements questions = doc.select("a[href]");
			for(Element link: questions){
				if(link.attr("href").contains(addRess))
					processPage(link.attr("abs:href"), searchTerm);
			}
		}
	}
}