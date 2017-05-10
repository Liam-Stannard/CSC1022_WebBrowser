//AUTHOR: 			Liam Stannard
//DATA:   			24/04/17
//DESCRIPTION:		This is a class which acts as a generic tab for the tabbedPane so a tab can be added to a tabbed pane
//					The class itself is generic and is used to display things such as bookmarks or history using the Jlist
//					Methods are all documented above signatures if not self explanatory

package web;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class TabGeneric<E> extends JPanel {

	private static final long serialVersionUID = 421920844933924691L;
	private JList<E> jList;
	final private JScrollPane scrollPane;
	final private JPanel contentPane;
	private DefaultListModel<E> model;
	final private Button button1;
	final private Button button2;
	final private Button button3;
	final private JPanel buttonBar;
//=======================================================Constructor=====================================================
	TabGeneric(String buttonName1, String buttonName2, String buttonName3) {
		model = new DefaultListModel<E>();
		jList = new JList<E>(model);
		contentPane = new JPanel(new BorderLayout());
		scrollPane = new JScrollPane(jList);
		button1 = new Button(buttonName1);
		button2 = new Button(buttonName2);
		button3 = new Button(buttonName3);
		this.setLayout(new BorderLayout());
		buttonBar = new JPanel(new GridLayout(1, 4));

		this.add(buttonBar, BorderLayout.NORTH);
		this.add(contentPane, BorderLayout.CENTER);
		contentPane.add(scrollPane);
		buttonBar.add(button1);
		buttonBar.add(button2);
		buttonBar.add(button3);
		jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);	//only a single item can be selected
		button1.setActionCommand(buttonName1);
		button2.setActionCommand(buttonName2);
		button1.setEnabled(false);
		button2.setEnabled(false);
		button3.setEnabled(false);
		ButtonEnablerListener();
		setVisible(false);

	}
	
//=======================================================Methods=======================================================
	
	void setVisable(boolean visable) {
		jList.setVisible(visable);
		contentPane.setVisible(visable);
		scrollPane.setVisible(visable);
		this.setVisible(visable);
		buttonBar.setVisible(visable);
		button1.setVisible(visable);
		button2.setVisible(visable);
		button3.setVisible(visable);

	}

	boolean isVisablee() {
		if (jList.isVisible() && contentPane.isVisible() && scrollPane.isVisible()) {
			return true;
		} else {
			return false;
		}
	}

	void addItemsToList(E Item) {
		model.addElement(Item);
	}

	void removeIndexFromList(int index) {
		model.remove(index);
	}
	
	void enableButton1(boolean enable) {
		button1.setEnabled(enable);
	}

	void enableButton2(boolean enable) {
		button2.setEnabled(enable);
	}

	void enableButton3(boolean enable) {
		button3.setEnabled(enable);
	}	
//=======================================================Getter & Setters============================================
	JList<E> getjList() {
		return jList;
	}

	DefaultListModel<E> getModel() {
		return model;
	}

	Button getButton1() {
		return button1;
	}

	Button getButton2() {
		return button2;
	}

	Button getButton3() {
		return button3;
	}
//======================================================Listeners=========================================
	void ButtonEnablerListener() {
		jList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) 			//if the value is still being selected
				{
					if (jList.getSelectedIndex() == -1) 		//-1 is selected index when no value is selected
					{
						enableButton1(false);
						enableButton2(false);
						enableButton3(false);
					} else 
					{
						enableButton1(true);
						enableButton2(true);
						enableButton3(true);
					}
				}
			}
		});
	}
}
