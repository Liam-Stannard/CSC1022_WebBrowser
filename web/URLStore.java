//AUTHOR: 			Liam Stannard
//DATA:   			24/04/17
//DESCRIPTION:		This is a class which acts as a store for URLItems, it also has methods for reading/writing them to file
//					Methods are all documented above signatures if not self explanatory

package web;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class URLStore {
	private List<URLItem> urlItems;
	private Stack<URL> backStack = new Stack<>();
	private Stack<URL> forwardStack = new Stack<>();
	private FileReader inData;
	private Scanner inFile;
	private URL previousURL;
	private URLItem lastItemAdded;
	private File file;
//=======================================================Constructor=====================================================
	URLStore(String fileName) {
		file = new File(fileName);
		urlItems = new ArrayList<>();
		readFile();
	}
//=======================================================Methods=====================================================
	URL popForwardStack() {
		return forwardStack.pop();
	}

	URL popBackStack() {
		return backStack.pop();
	}

	void pushForwardStack(URL url) {
		forwardStack.push(url);
	}

	void pushBackStack(URL url) {
		backStack.push(url);
	}

	//reads the file and adds it to a list
	private void readFile() {
		try {

			if (!file.exists()) {
				file.createNewFile();
			}

			inData = new FileReader(file);

			inFile = new Scanner(inData);
			while (inFile.hasNextLine())											//whilst there's a next line in the file
			{
				String input = inFile.nextLine();
				Scanner lineScanner = new Scanner(input);
				String[] ar = lineScanner.nextLine().split(" ");					//splits the line via spaces
				urlItems.add(new URLItem(new URL(ar[02]), ar[0], ar[01]));			//creats a new urlItem with the split string
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

	//rewrites the entire file without the removed element
	void removeItem() throws IOException {

		FileWriter fileWriter = new FileWriter(file, false);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		PrintWriter printWriter = new PrintWriter(bufferedWriter);
		
		for (URLItem urlItem : urlItems)											
			printWriter.println(urlItem);

		printWriter.close();
		bufferedWriter.close();
		fileWriter.close();

	}

	//writes the URLItems to a file
	void writeFile() throws IOException {

		FileWriter fileWriter = new FileWriter(file, true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		PrintWriter printWriter = new PrintWriter(bufferedWriter);

		printWriter.println(lastItemAdded);

		printWriter.close();
		bufferedWriter.close();
		fileWriter.close();
	}

	//adds a new item to the list
	void addItem(URL url) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		URLItem newItem = new URLItem(url, dateFormat.format(date).toString(), timeFormat.format(date));
		urlItems.add(newItem);
		previousURL = url;
		lastItemAdded = newItem;

	}
	//searchs for an item in the list, if its found it returns true
	boolean searchListForURL(URL url) {
		for (URLItem urlItem : urlItems) 
		{
			
			if(urlItem.getUrl().equals(url))
			{
				return true;
				
			}



		}
		return false;
	}

//=======================================================Getters and setters=====================================================
	URLItem getIndexedItem(int index) {

		return urlItems.get(index);

	}

	List<URLItem> getUrlItems() {
		return urlItems;
	}

	URLItem getLastItemAdded() {
		return lastItemAdded;
	}

	URL getPreviousURL() {
		return previousURL;
	}

	void setPreviousURL(URL previousURL) {
		this.previousURL = previousURL;
	}

	Stack<URL> getBackStack() {
		return backStack;
	}

	Stack<URL> getForwardStack() {
		return forwardStack;
	}

}
