//AUTHOR: 			Liam Stannard
//DATA:   			24/04/17
//DESCRIPTION:		This is a class which acts as the class which puts everything togerther for the web broswer, this is made up of a mainPane a toolbar and two Generic tabs inside a JTabbed Pane
//					Two URL stores handle the history and bookmarks
//					Methods are all documented above signatures if not self explanatory

package web;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class UserInterface {
	final private JFrame frame;
	final private MainPane mainPane;
	final private ToolBar toolBar;
	final private URLStore history;
	final private URLStore bookmarks;
	final private TabGeneric<URLItem> historyInterface;
	final private JTabbedPane tabbedPane;
	final private TabGeneric<URL> bookmarkInterface;
	final private String urlNotFound;
	private boolean startUp;
	
//=======================================================Constructor====================================================
	public UserInterface() {

		frame = new JFrame("Browser");
		startUp = true;
		mainPane = new MainPane();
		toolBar = new ToolBar();
		history = new URLStore("history.txt");
		bookmarks = new URLStore("bookmarks.txt");
		historyInterface = new TabGeneric<URLItem>("Bookmark", "Delete", "Set As Home");
		tabbedPane = new JTabbedPane();
		bookmarkInterface = new TabGeneric<URL>("Bookmark Current Page", "Delete", "Set As Home");
		urlNotFound = "URL not found.\n Please try a scheme before your web address such as: \n \t https:// \n\t  file://";
		setup();
		eventHandlers();

	}
//=======================================================Methods====================================================

	//first start up of the browser
	private void setup() {

		try {

			frame.add(toolBar, BorderLayout.NORTH);
			frame.add(mainPane, BorderLayout.CENTER);
			updatePage(new URL(mainPane.getHomeURL()), true);
			tabbedPane.add("History", historyInterface);
			tabbedPane.add("Bookmarks", bookmarkInterface);
			frame.add(tabbedPane, BorderLayout.EAST);
			
			
			for (URLItem urlItem : history.getUrlItems()) {					//adds all history items to the Jlist
				historyInterface.addItemsToList(urlItem);
			}

			for (URLItem urlItem : bookmarks.getUrlItems()) {				//adds all bookmark items to the Jlist
				bookmarkInterface.addItemsToList(urlItem.getUrl());
			}

		} catch (IOException e) {
			mainPane.setPaneText(urlNotFound);
			e.printStackTrace();
		}
		startUp = false;
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setVisible(true);
		toolBar.setVisable(true);
		mainPane.setVisable(true);
		tabbedPane.setVisible(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bookmarkInterface.getButton1().setEnabled(true);

	}

	//updates the page using methods from each component to ensure everything stays concurrent
	private void updatePage(URL url, boolean forwardBack) throws IOException 
	{

		if (!url.equals(history.getPreviousURL())) {
			
				if (!forwardBack) {
					history.getBackStack().push(history.getPreviousURL());
				}
					
				mainPane.setPage(url);
				toolBar.updateURLField(url);
				history.addItem(url);
				history.writeFile();
				history.setPreviousURL(url);
				
			
				//prevents a glitch which causes the home page to be added twice to the history List.
				if (startUp == false)
				{
					historyInterface.addItemsToList(history.getLastItemAdded());	
				}
				
				//enables the buttons or not.
				if (history.getBackStack().isEmpty()) 
				{
					toolBar.getBackButton().setEnabled(false);
				} else
				{
					toolBar.getBackButton().setEnabled(true);
				}
				
				//enables the buttons or not.
				if (history.getForwardStack().isEmpty()) 
				{
					toolBar.getForwardButton().setEnabled(false);
				} else 
				{
					toolBar.getForwardButton().setEnabled(true);
				}

			
		}
	}

	private void eventHandlers() 
	{
		loadHomePageListener();
		urlLoadWebPageListener();
		keyLoadWebPageListener();
		hyperLinkListener();
		showHistoryListener();
		hdeleteButtonListener();
		hBookmarkButtonListener();
		hdbClickHistoryLinkListener();
		bdbClickHistoryLinkListener();
		bBookmarkButtonListener();
		bdeleteButtonListener();
		ForwardListener();
		BackListener();
		hsetHomeButtonListener();
		bsetHomeButtonListener();
		frameResizeListener();
	}
//=======================================================Listeners====================================================
	//if the back button is pressed push the current URL onto the forward stack so the user can press forward to go back to the previous URL
	private void BackListener() {
		toolBar.getBackButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ("Back".equals(e.getActionCommand())) {									
					try {																	//
						history.pushForwardStack(new URL(toolBar.getURLFieldText()));
						URL url = history.popBackStack();
						updatePage(url, true);
					}  catch (MalformedURLException e1 )
					{
						e1.printStackTrace();
						mainPane.setPaneText(urlNotFound);
					} catch (IOException e1) {
						e1.printStackTrace();
						mainPane.setPaneText(urlNotFound);
					}
																		

				} else {
					throw new IllegalArgumentException("ERROR");
				}
			}
		});

	}
	//if the forward button is pressed push the current URL onto the forward stack so the user can press forward to go back to the previous URL
	private void ForwardListener() {
		toolBar.getForwardButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {									
				if ("Forward".equals(e.getActionCommand())) {							
					try {																	
						history.pushBackStack((new URL(toolBar.getURLFieldText())));
						URL url = history.popForwardStack();
						updatePage(url, true);
					}  catch (MalformedURLException e1 )
					{
						e1.printStackTrace();
						mainPane.setPaneText(urlNotFound);
					} catch (IOException e1) {
						e1.printStackTrace();
						mainPane.setPaneText(urlNotFound);
					}
					

				} else {
					throw new IllegalArgumentException("ERROR");
				}
			}
		});

	}
	//if Reload is pressed updates the page
	private void urlLoadWebPageListener() {
		toolBar.getLoadButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if ("Reload".equals(e.getActionCommand())) {								
					try {
						URL url = new URL(toolBar.getURLFieldText());
						updatePage(url, false);
					}  catch (MalformedURLException e1 )
					{
						e1.printStackTrace();
						mainPane.setPaneText(urlNotFound);
					} catch (IOException e1) {
						e1.printStackTrace();
						mainPane.setPaneText(urlNotFound);
					}
				} else {
					throw new IllegalArgumentException("ERROR");
				}
			}
		});

	}
	//if enter is pressed updates the page
	private void keyLoadWebPageListener() {
		toolBar.getUrlField().addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { 								

					try {
						URL url = new URL(toolBar.getURLFieldText());
						updatePage(url, false);
					} catch (MalformedURLException e1 )
					{
						e1.printStackTrace();
						mainPane.setPaneText(urlNotFound);
					} catch (IOException e1) {
						e1.printStackTrace();
						mainPane.setPaneText(urlNotFound);
					}
				}

			}
		});
	}
	//if the home button is pressed it returns home
	private void loadHomePageListener() {
		toolBar.getHomeButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if ("Home".equals(e.getActionCommand())) {							
					try {
						URL url = new URL(mainPane.getHomeURL());
						updatePage(url, false);
					}  catch (MalformedURLException e1 )
					{
						e1.printStackTrace();
						mainPane.setPaneText(urlNotFound);
					} catch (IOException e1) {
						e1.printStackTrace();
						mainPane.setPaneText(urlNotFound);
					}
				} else {
					throw new IllegalArgumentException("ERROR");
				}
			}
		});
	}
	//if an hyperlink is pressed it updates the page
	private void hyperLinkListener() {
		mainPane.addHyperLinkListener(new HyperlinkListener() {

			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) 	
				{
					URL url = e.getURL();
					try {
						updatePage(url, false);
					}  catch (MalformedURLException e1 )
					{
						e1.printStackTrace();
						mainPane.setPaneText(urlNotFound);
					} catch (IOException e1) {
						e1.printStackTrace();
						mainPane.setPaneText(urlNotFound);
					}
				}

			}
		});
	}

	//sets the history/bookmark interface visable
	private void showHistoryListener() {
		toolBar.getHistoryButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if ("History".equals(e.getActionCommand()) && !historyInterface.isVisablee()) {
					tabbedPane.setVisible(true);
					historyInterface.setVisable(true);
					bookmarkInterface.setVisable(true);

				} else if ("History".equals(e.getActionCommand()) && historyInterface.isVisablee()) {
					historyInterface.setVisable(false);
					tabbedPane.setVisible(false);
					bookmarkInterface.setVisable(false);

				}
			}
		});
	}

	//sets the home page to the selected item
	private void hsetHomeButtonListener() {
		historyInterface.getButton3().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ("Set As Home".equals(e.getActionCommand())) {
					int index = historyInterface.getjList().getSelectedIndex();
					mainPane.setHomeURL(history.getIndexedItem(index).getUrl().toString());
					mainPane.writeFile();
				} else {
					throw new IllegalArgumentException("ERROR");
				}
			}
		});
	}
	//sets the home page to the selected item
	private void bsetHomeButtonListener() {
		bookmarkInterface.getButton3().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ("Set As Home".equals(e.getActionCommand())) {
					int index = bookmarkInterface.getjList().getSelectedIndex();
					mainPane.setHomeURL(bookmarks.getIndexedItem(index).getUrl().toString());
					mainPane.writeFile();
				} else {
					throw new IllegalArgumentException("ERROR");
				}
			}
		});
	}
	//double click to load item
	private void hdbClickHistoryLinkListener() {
		historyInterface.getjList().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {

					int index = historyInterface.getjList().locationToIndex(evt.getPoint());
					try {
						updatePage(historyInterface.getjList().getModel().getElementAt(index).getUrl(), false);
					}  catch (MalformedURLException e1 )
					{
						e1.printStackTrace();
						mainPane.setPaneText(urlNotFound);
					} catch (IOException e1) {
						e1.printStackTrace();
						mainPane.setPaneText(urlNotFound);
					}
				}
			}
		});
	}
	//double click to load item
	private void bdbClickHistoryLinkListener() {
		bookmarkInterface.getjList().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {

					int index = bookmarkInterface.getjList().locationToIndex(evt.getPoint());
					try {
						updatePage(bookmarkInterface.getjList().getModel().getElementAt(index), false);
					} catch (MalformedURLException e1 )
					{
						e1.printStackTrace();
						mainPane.setPaneText(urlNotFound);
					} catch (IOException e1) {
						e1.printStackTrace();
						mainPane.setPaneText(urlNotFound);
					}
				}
			}
		});
	}
	//press to bookmark selected item
	private void hBookmarkButtonListener() {
		historyInterface.getButton1().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if ("Bookmark".equals(e.getActionCommand())) {
					int index = historyInterface.getjList().getSelectedIndex();
					URL url = history.getIndexedItem(index).getUrl();
				
					try {
						
						if (!bookmarks.searchListForURL(url))
						{
						bookmarks.addItem(url);
						bookmarkInterface.addItemsToList(bookmarks.getLastItemAdded().getUrl());
						bookmarks.writeFile();
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					throw new IllegalArgumentException("ERROR");
				}
			}
		});

	}	
	//press to bookmark current page
	private void bBookmarkButtonListener() {
		bookmarkInterface.getButton1().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if ("Bookmark Current Page".equals(e.getActionCommand())) {

					try {
						URL url = new URL(toolBar.getUrlField().getText());
						if (!bookmarks.searchListForURL(url))
						{
						bookmarks.addItem(url);
						bookmarkInterface.addItemsToList(bookmarks.getLastItemAdded().getUrl());
						bookmarks.writeFile();
						}
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

}
		
	
	//press to delete selected item
	private void hdeleteButtonListener() {
		historyInterface.getButton2().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if ("Delete".equals(e.getActionCommand())) {

					int index = historyInterface.getjList().getSelectedIndex();

					try {
						history.removeItem();
						historyInterface.removeIndexFromList(index);
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				} else {
					throw new IllegalArgumentException("ERROR");
				}
			}
		});

	}
	//press to delete selected item
	private void bdeleteButtonListener() {
		bookmarkInterface.getButton2().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if ("Delete".equals(e.getActionCommand())) {
					int index = bookmarkInterface.getjList().getSelectedIndex();
					try {
						bookmarks.removeItem();
						bookmarkInterface.removeIndexFromList(index);
					} catch (IOException e1) {

						e1.printStackTrace();
					}

				} else {
					throw new IllegalArgumentException("ERROR");
				}
			}
		});

	}
	
	//sets size of items to fit resolution
	private void frameResizeListener()
	{
		frame.addComponentListener(new ComponentAdapter() {
		    @Override
		    public void componentResized(ComponentEvent e)
		    {
		        frame.repaint();
		       Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				if(screenSize.getHeight()>= 1080 && screenSize.getWidth()>= 1920 )
				{
				toolBar.setTextFieldLength(130); 
				}
				else
				{
					toolBar.setTextFieldLength(80); 
				}
		    }
		});
	}

}
