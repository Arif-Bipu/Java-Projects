/* Name: Arif Bipu
 Course: CNT 4714 – Fall 2020
 Assignment title: Project 1 – Event-driven enterprise simulation
 Date: Sunday September 13, 2020
*/

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.SwingConstants;


public class Gui extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Item> inventory;
	private LogicofOrder order = new LogicofOrder();
	
	
	//Jlabels
	JLabel jlbItemID = new JLabel("Enter item ID for Item #1:", SwingConstants.CENTER);
	JLabel jlbQuantity = new JLabel("Enter quantity for Item #1:", SwingConstants.CENTER);
	JLabel jlbItemInfo = new JLabel("Item #1 info:", SwingConstants.CENTER);
	JLabel jlbSubtotal = new JLabel("Order Subtotal for 0 item(s):", SwingConstants.CENTER);
	
	//Text fields
	private JTextField jtfNumItems = new JTextField();
	private JTextField jtfItemID = new JTextField();
	private JTextField jtfQuantity = new JTextField();
	private JTextField jtfItemInfo = new JTextField();
	private JTextField jtfTotalItems = new JTextField();
	
	//Buttons
	
	//Item buttons
	private JButton jProcessItem = new JButton("Process Item #1");
	private JButton jConfirmItem = new JButton("Confirm Item #1");
	
	// Other Button Labels
	
	private JButton jViewOrder = new JButton("View Order");
	private JButton jFinishOrder = new JButton("Finish Order");
	private JButton jNewOrder = new JButton("New Order");
	private JButton jExit = new JButton("Exit");
	
	
	public Gui() throws FileNotFoundException
	{
		this.getInventoryFromFile();
		
		
		//Text boxes
		JPanel p1 = new JPanel(new GridLayout(5,2));
		p1.add(new JLabel("Enter number of items in this order:", SwingConstants.CENTER));
		
		p1.add(jtfNumItems);
		p1.add(jlbItemID);
		p1.add(jtfItemID);
		p1.add(jlbQuantity);
		p1.add(jtfQuantity);
		p1.add(jlbItemInfo);
		p1.add(jtfItemInfo);
		p1.add(jlbSubtotal);
		p1.add(jtfTotalItems);
		
		
		//Lower six buttons
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		p2.add(jProcessItem);
		p2.add(jConfirmItem);
		p2.add(jViewOrder);
		p2.add(jFinishOrder);
		p2.add(jNewOrder);
		p2.add(jExit);
		
		//disable text fields
		this.jtfTotalItems.setEnabled(false);
		this.jtfItemInfo.setEnabled(false);
		
		//disable buttons
		this.jConfirmItem.setEnabled(false);
		this.jViewOrder.setEnabled(false);
		this.jFinishOrder.setEnabled(false);
		
		//adding panels
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.SOUTH);
		
		//ActionListeners
		
		jProcessItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int numOfItemsOrdered = Integer.parseInt(jtfNumItems.getText());
				int itemID = Integer.parseInt(jtfItemID.getText());
				int quantityOfItem = Integer.parseInt(jtfQuantity.getText());
				
				if((order.getMaxNumItems() == -1) && (numOfItemsOrdered > 0)) {
					order.setMaxNumItems(numOfItemsOrdered);
					jtfNumItems.setEnabled(false);
				}
				
				int itemIndex = searchItem(itemID);
				
				if(itemIndex != -1) {
					Item foundItem = inventory.get(itemIndex);
					order.setItemInfo(
							foundItem.getItemID() + "",
							foundItem.getName() + "",
							foundItem.getPrice() + "",
							quantityOfItem + "",
							order.getDiscountPercentage(quantityOfItem) + "",
							new DecimalFormat("#0.00").format(order.getTotalDiscount(quantityOfItem, foundItem.getPrice()))+ "");
					
					String itemInfo = foundItem.getItemID() + 
							foundItem.getName() +  " $" + 
							foundItem.getPrice() + " " + 
							quantityOfItem + " " + 
							order.getDiscountPercentage(quantityOfItem) + "% " + 
							new DecimalFormat("#0.00").format(order.getTotalDiscount(quantityOfItem, foundItem.getPrice()));
					
					jtfItemInfo.setText(itemInfo);
					jConfirmItem.setEnabled(true);
					jProcessItem.setEnabled(false);
					order.setOrderSubtotal(quantityOfItem, foundItem.getPrice());
					jtfItemInfo.setEnabled(false);
					jtfTotalItems.setEnabled(false);
				}
				
				else {
					JOptionPane.showMessageDialog(null, "Item ID " + itemID + "not in file.");
				}
			}
			
			
		});
		
		jConfirmItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int numOfItemsOrdered = Integer.parseInt(jtfNumItems.getText());
				int quantityOfItem = Integer.parseInt(jtfQuantity.getText());
				
				if(numOfItemsOrdered > order.getMaxNumItems())
					System.out.println("Over Quantity");
					
				//incrementing number of items and the subtotal updated
				order.setCurrentNumItems(quantityOfItem);
				order.setTotalItems(order.getTotalItems()+1);
				
				JOptionPane.showMessageDialog(null, "Item #" + order.getTotalItems() + " accepted");
				
				//preparing transactions
				order.prepareTransaction();
				
				order.addToViewOrder(jtfItemInfo.getText());
				
				jProcessItem.setEnabled(true);
				jViewOrder.setEnabled(true);
				jFinishOrder.setEnabled(true);
				jConfirmItem.setEnabled(true);

				
				//updating the buttons
				jProcessItem.setText("Process Item#" + (order.getTotalItems()+1));
				jConfirmItem.setText("Confirm Item#" + (order.getTotalItems()+1));
				
				//updating text fields
				jtfItemID.setText("");
				jtfQuantity.setText("");
				jtfTotalItems.setText("$" + new DecimalFormat("#0.00").format(order.getOrderSubtotal()));
				
				//updating labels
				
				jlbItemID.setText("Enter Item ID for item #" + (order.getTotalItems()+1)+":");
				jlbSubtotal.setText("Order subtotal for " + order.getCurrentNumItems() + 1 + "item(s)");
				jlbQuantity.setText("Enter quantity for Item #" + (order.getTotalItems()+1) + ":");
				if(order.getCurrentNumItems() < order.getMaxNumItems())
					jlbItemInfo.setText("Item#" + (order.getTotalItems()+1) + "info:");
				
				if(order.getCurrentNumItems() >= order.getMaxNumItems()) {
					jlbItemID.setVisible(true);
					jlbQuantity.setVisible(true);
					jProcessItem.setEnabled(false);
					jConfirmItem.setEnabled(false);
				}
									
			}});
		
		jViewOrder.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, order.getViewOrder());
				}
			});
		
		jFinishOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					order.printTransactions();
					JOptionPane.showMessageDialog(null, order.getFinishOrder());
				}catch (IOException e1) {
					e1.printStackTrace();
				}
				Gui.super.dispose();
				
			}
		});
		
		jNewOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Gui.super.dispose();
				
				try {
					Gui.main(null);
				}catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		jExit.addActionListener(new ActionListener() {
			@Override
			
			public void actionPerformed(ActionEvent e) {
				Gui.super.dispose();
			}
		});
	}	
		
		public int searchItem(int ItemID) {
			for(int i = 0;i< this.inventory.size();i++) {
				Item currentItem = inventory.get(i);
				
				if(currentItem.getItemID() == ItemID)
					return i;
			}
			
			return -1;
		}
		
		public void getInventoryFromFile() throws FileNotFoundException {
			
			this.inventory = new ArrayList<Item>();
			
			File file = new File("inventory.txt");
			
			Scanner textFile = new Scanner(file);
			
			while(textFile.hasNextLine()) {
				String item = textFile.nextLine();
				String[] itemInfo = item.split(", ");
				
				Item currentItem = new Item();
				currentItem.setItemID(Integer.parseInt(itemInfo[0]));
				currentItem.setName(itemInfo[1]);
				currentItem.setPrice(Double.parseDouble(itemInfo[2]));
				
				inventory.add(currentItem);
			}
			
			textFile.close();
			
			
			
			//outputing the inventory to console
			
			for(int i = 0; i <inventory.size();i++) {
				Item current = inventory.get(i);
				
				if ((Integer.toString(current.getItemID()).length() <=5)) {
					System.out.println(current.getItemID() + "\t\t\t" + current.getName() + "\t\t" + current.getPrice());
				}
				else
					System.out.println(current.getItemID() + "\t" + current.getName() + "\t\t" + current.getPrice());
			}
		}
		
		public ArrayList<Item> getInventory(){
			return inventory;
		}
		
		public void setInventory(ArrayList<Item> inventory) {
			this.inventory = inventory;
		}
		
		public static void main(String[] args) throws FileNotFoundException {
			Gui frame = new Gui();
			frame.pack();
			frame.setTitle("Nile Dot Com - Fall 2020");
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setVisible(true);
		}
}