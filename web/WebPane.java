//AUTHOR: 			Liam Stannard
//DATA:   			24/04/17
//DESCRIPTION:		This is a class which acts as a webpane for the web broswer,this is an extension of JEditor 
//					The class itself has methods for reading/writing to a config file to retrieve the home URL
//					Methods are all documented above signatures if not self explanatory


package web;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkListener;

public class WebPane extends JEditorPane{

	private static final long serialVersionUID = 1L;
	private String homeURL;
	private FileReader inData;
	private Scanner inFile;
	private	File file;
	private String fileName = "config.txt";
	
//=======================================================Constructor====================================================	
	public WebPane() 
	{
		file = new File(fileName);
		readFile();
		if(homeURL == null)
		{
			homeURL = "https://www.google.co.uk/";
		}
		
			
		
	}
//=======================================================Methods======================================================
	//reads a config file to set the homeURL
	private void readFile() {
		try {

			if (!file.exists()) {
				file.createNewFile();
			}

			inData = new FileReader(file);
			inFile = new Scanner(inData);
			if(inFile.hasNextLine())
			{
			String input = inFile.nextLine();
			Scanner lineScanner = new Scanner(input);
			homeURL = lineScanner.nextLine();
			lineScanner.close();
			}
			inData.close();
			inFile.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (e.equals(new NoSuchElementException())) {

				file.delete();
				readFile();
			}
		}
	}
	//writes to a config file to set the homeURL
	void writeFile() throws IOException
	{

		FileWriter fileWriter = new FileWriter(file, false);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		PrintWriter printWriter = new PrintWriter(bufferedWriter);

		printWriter.println(homeURL);

		printWriter.close();
		bufferedWriter.close();
		fileWriter.close();
	}
	
	void addHyperLinkListener(HyperlinkListener hListener)
	{
		this.addHyperlinkListener(hListener);
	}
	
//=======================================================Getters & Setters======================================================	
	public String getHomeURL() {
		return homeURL;
	}
	public void setHomeURL(String homeURL) {
		this.homeURL = homeURL;
	}	
	
	void setPaneText(String text)
	{
		setText(text);
	}
	
	
}
