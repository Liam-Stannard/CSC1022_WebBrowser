//AUTHOR: 			Liam Stannard
//DATA:   			24/04/17
//DESCRIPTION:		This is a class which acts as a main pane for the web broswer, this is made up of a WebPane, an extension of JEditor and a scroll pane
//					The class itself is an extension of JPanel so it can be treated as a component panel and added to the interface.
//					Methods are all documented above signatures if not self explanatory

package web;
import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URL;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkListener;


public class MainPane extends JPanel {

	private static final long serialVersionUID = -3149580182366470567L;
	final private WebPane pane;
	final private JScrollPane scrollPane;
	
//=======================================================Constructor====================================================
	public MainPane()  {
		pane = new WebPane();
		scrollPane = new JScrollPane(pane);
		this.setLayout(new BorderLayout());
		pane.setContentType("text/html");
		pane.setEditable(false);
		this.add(scrollPane, BorderLayout.CENTER);	
	}
//=======================================================Methods==========================================================
	//saves having to use get methods so it ensures greater encapsulation
		//write file simply writes the home URL to a file named config
	void writeFile()
	{
		try {
			pane.writeFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//saves having to use get methods so it ensures greater encapsulation
		//adds a hyper link listener to pane.
	void addHyperLinkListener(HyperlinkListener hListener)
	{
		pane.addHyperlinkListener(hListener);
	}
	
	//saves having to use get methods so it ensures greater encapsulation
		//sets the pane text - used for error messages mainly
	void setPaneText(String text)
	{
		pane.setPaneText(text);
	}

	//sets all components visable or not
	void setVisable(boolean visable) {

		pane.setVisible(visable);
		scrollPane.setVisible(visable);
		this.setVisible(visable);

	}
	
	//saves having to use get methods so it ensures greater encapsulation
		//sets the page to the provided URL
	public void setPage(URL url) throws IOException {
		pane.setPage(url);
		
	}
	
//=======================================================Getter & Setters=====================================================
	public String getHomeURL() {
		return pane.getHomeURL();
	}

	
	public void setHomeURL(String homeURL) {
		pane.setHomeURL(homeURL);
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	

}
