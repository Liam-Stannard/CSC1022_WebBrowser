//AUTHOR: 			Liam Stannard
//DATA:   			24/04/17
//DESCRIPTION:		This is a class which acts as a store for a single URL and the time and date it was accessed
//					Methods are all documented above signatures if not self explanatory

package web;
import java.net.URL;

public class URLItem 
{

	final private URL url;
	final private String dateAccessed;
	final private String timeAccessed;
	
//=======================================================Constructor=====================================================
	public URLItem(URL url, String dateAccessed, String timeAccessed) 
	{
		this.url = url;
		this.dateAccessed = dateAccessed;
		this.timeAccessed = timeAccessed;
	}
//=======================================================Methods=====================================================
	 
	 public String toString() 
	 {
			
			return dateAccessed + " " + timeAccessed + " " +url.toString() ;
	
	 }
//=======================================================Getters & Setters=====================================================
	 URL getUrl() 
	 {
			return url;
	 }
	

}
