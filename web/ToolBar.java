//AUTHOR: 			Liam Stannard
//DATA:   			24/04/17
//DESCRIPTION:		This is a toolbar which has a textfield used as a URL bar, a selection of buttons to access various things.
//					Methods are all documented above signatures if not self explanatory

package web;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ToolBar extends JPanel {

	private static final long serialVersionUID = -4359725314560687518L;
	final private JPanel toolBarPane;
	final private JTextField urlField;
	final private JButton loadButton;
	final private JButton homeButton;
	final private JButton backButton;
	final private JButton forwardButton;
	final private JButton historyButton;
	final private String HistoryButtonName = "History";
	final private String FowardButtonName = "Forward";
	final private String BackButtonName = "Back";
	final private String HomeButtonName = "Home";
	final private String LoadButtonName = "Reload";
	final private JPanel buttonPanel;
	final private int textFieldMinLength;
	private int textFieldLength;
	private String CurrentURL;
	private Dimension screenSize; 

//=======================================================Constructor====================================================
	public ToolBar() 
	{
		textFieldMinLength = 50;
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if(screenSize.equals(new Dimension(1920, 1080)))
		{
		textFieldLength = 130;
		}
		else
		{
			textFieldLength = 80;
		}
		toolBarPane = new JPanel();
		urlField = new JTextField("", textFieldLength);
		historyButton = new JButton(HistoryButtonName);
		forwardButton = new JButton(FowardButtonName);
		backButton = new JButton(BackButtonName);
		loadButton = new JButton(LoadButtonName);
		homeButton = new JButton(HomeButtonName);
		buttonPanel = new JPanel(new GridLayout(1, 1));
		new BoxLayout(toolBarPane, BoxLayout.Y_AXIS);
		draw();
	}
//=======================================================Methods====================================================
	public void draw() {
		//setting action commands
		forwardButton.setActionCommand(FowardButtonName);
		backButton.setActionCommand(BackButtonName);
		historyButton.setActionCommand(HistoryButtonName);
		loadButton.setActionCommand(LoadButtonName);
		homeButton.setActionCommand(HomeButtonName);

		//adds components
		urlField.setBackground(Color.lightGray);
		toolBarPane.add(homeButton);
		buttonPanel.add(backButton);
		buttonPanel.add(forwardButton);
		toolBarPane.add(buttonPanel);
		toolBarPane.add(urlField);
		toolBarPane.add(loadButton);
		toolBarPane.add(historyButton);
		this.add(toolBarPane);

	}

	void setVisable(boolean visable) {
		toolBarPane.setVisible(visable);
		urlField.setVisible(visable);
		loadButton.setVisible(visable);
		homeButton.setVisible(visable);
		historyButton.setVisible(visable);
		forwardButton.setVisible(visable);
		backButton.setVisible(visable);
		buttonPanel.setVisible(visable);
	}
	
	public void updateURLField(URL url) 
	{
		CurrentURL = url.toString();
		urlField.setText(CurrentURL);
	}
//=======================================================Getter & Setters====================================================
	public String getURLFieldText()
	{
		return urlField.getText();
	}

	public JTextField getUrlField() {
		return urlField;
	}

	public JButton getLoadButton() {
		return loadButton;
	}

	public JButton getHomeButton() {
		return homeButton;
	}

	public JButton getHistoryButton() {
		return historyButton;
	}

	public JButton getBackButton() {
		return backButton;
	}

	public JButton getForwardButton() {
		return forwardButton;
	}
	void setTextFieldLength(int length)
	{
		if(length>textFieldMinLength)
		{
			urlField.setColumns(length);
		}
	}
	
	
}
