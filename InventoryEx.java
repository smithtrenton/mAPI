package mAPI;

import java.util.ArrayList;

import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.Item;

public class InventoryEx {
	
	public static boolean bankAction(Item item, String prefix, int amount) {
		if(!Bank.bankScreen()) 
			return false;
		switch(amount) {
		case -1:
			return item.getWidgetChild().interact(prefix + "-All but one");
		case 0:
			if (countItems(item.getId()) > 1)
				return item.getWidgetChild().interact(prefix + "-All");
			else 
				return item.getWidgetChild().interact(prefix);
		case 1:
			return item.getWidgetChild().click(true);
		default:
			String[] actions = item.getWidgetChild().getActions();
			for(String str:actions)
				if (str.equals(prefix + "-" + amount))
					return item.getWidgetChild().interact(prefix + "-" + amount);
			Timer timeout = new Timer(3500);
			//TO-DO: Solid method for detection of box!!!
			while (!Widgets.get(Constants.BANK_AMOUNT_INDEX, Constants.BANK_AMOUNT_VISIBLE).isVisible()) {
				if (timeout.getRemaining() == 0) return false;
				item.getWidgetChild().interact(prefix + "-X");
				Time.sleep(Random.nextInt(1200, 1600));
			}					
			Keyboard.sendText("" + amount, true);
			return true;
		}
	}
	
	public static boolean deposit(Item item, int amount) {
		int c = Inventory.getCount();
		Timer timeout = new Timer(2000);
		while (timeout.getRemaining() > 0) {
			if (c > Inventory.getCount() ||
					countItems(item.getId()) == 0) break;
			if (bankAction(item, "Deposit", amount))
				Time.sleep(Random.nextInt(750, 1000));
			else 
				Time.sleep(Random.nextInt(250, 350));
		}
		return (c > Inventory.getCount());
	}
	
	public static boolean invTab() {
		return Bank.bankScreen() || Tabs.INVENTORY.open(); 
	}
	
	public static boolean isFull() {
		invTab();
		return Inventory.getCount() >= 28;
	}
	
	public static boolean isEmpty() {
		invTab();
		return Inventory.getCount() == 0;
	}
	
	public static Item[] getInvItems(final Filter<Item> filter) {
		invTab();
		ArrayList<Item> list = new ArrayList<Item>();
		for(Item b:Inventory.getItems())
			if (filter.accept(b))
				list.add(b);
		Item[] result = new Item[list.size()];
		list.toArray(result);
		return result;
	}
	
	public static Item[] getInvItems(final String[] names) {
		invTab();
		return getInvItems(new Filter<Item>() {
			@Override
			public boolean accept(Item i) {
				return Misc.inStrArr(i.getName(), names);
			}			
		});
	}
	
	public static Item[] getInvItems(final String name) {
		invTab();
		return getInvItems(new String[] {name});
	}
	
	public static Item[] getInvItems(final int...ids) {
		invTab();
		return getInvItems(new Filter<Item>() {
			@Override
			public boolean accept(Item i) {
				return Misc.inIntArr(i.getId(), ids);
			}			
		});
	}
	
	public static Item getInvItem(final Filter<Item> filter) {
		invTab();
		for(Item b:Inventory.getItems())
			if (filter.accept(b))
				return b;
		return null;
	}
	
	public static Item getInvItem(final int id) {
		invTab();
		return getInvItem(new Filter<Item>() {
			@Override
			public boolean accept(Item b) {
				return (b.getId() == id);
			}});
	}
	
	public static Item getInvItem(final String name) {
		invTab();
		return getInvItem(new Filter<Item>() {
			@Override
			public boolean accept(Item b) {
				return (b.getName().toLowerCase().contains(name.toLowerCase()));
			}});
	}
	
	public static boolean itemsInInv(final Filter<Item> filter) {
		invTab();
		return getInvItems(filter).length > 0;
	}
	
	public static boolean itemsInInv(final int...ids) {
		invTab();
		return getInvItems(ids).length > 0;
	}
	
	public static boolean itemsInInv(final String[] names) {
		invTab();
		return getInvItems(names).length > 0;
	}
	
	public static boolean itemsInInv(final String name) {
		invTab();
		return getInvItems(new String[] {name}).length > 0;
	}
	
	public static boolean itemInInv(final Filter<Item> filter) {
		invTab();
		return getInvItem(filter) != null;
	}
	
	public static boolean itemInInv(final int id) {
		invTab();
		return getInvItem(id) != null;
	}
	
	public static boolean itemInInv(final String name) {
		invTab();
		return getInvItem(name) != null;
	}
	
	public static int countItems(final String[] names) {
		invTab();
		return Inventory.getCount(new Filter<Item>() {
			@Override
			public boolean accept(Item b) {
				return Misc.inStrArr(b.getName(), names);
			}});
	}
	
	public static int countItems(final int...ids) {
		invTab();
		return Inventory.getCount(new Filter<Item>() {
			@Override
			public boolean accept(Item b) {
				return Misc.inIntArr(b.getId(), ids);
			}});
	}
	
	public static int countItemsExcept(final Filter<Item> filter) {
		invTab();
		return Inventory.getCount() - getInvItems(filter).length;
	}
	
	public static int countItemsExcept(final String[] names) {
		invTab();
		return countItemsExcept(new Filter<Item>() {
			@Override
			public boolean accept(Item b) {
				return Misc.inStrArr(b.getName(), names);
			}});
	}
	
	public static int countItemsExcept(final int...ids) {
		invTab();
		return countItemsExcept(new Filter<Item>() {
			@Override
			public boolean accept(Item b) {
				return Misc.inIntArr(b.getId(), ids);
			}});
	}
	
	public static int countItemsExcept(final String name) {
		invTab();
		return countItemsExcept(new String[] {name});
	}
	
	
}
