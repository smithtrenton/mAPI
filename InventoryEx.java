package mAPI;

import java.util.ArrayList;

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
		if (!item.getWidgetChild().validate())
			return false;
		switch(amount) {
		case -1:
			return item.getWidgetChild().interact(prefix + "-All but one");
		case 0:
			return item.getWidgetChild().interact(prefix + "-All");
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
		return bankAction(item, "Deposit", amount);
	}
	
	public static boolean isFull() {
		return Inventory.getCount() >= 28;
	}
	
	public static boolean isEmpty() {
		return Inventory.getCount() == 0;
	}
	
	public static Item[] getInvItems(final Filter<Item> filter) {
		ArrayList<Item> list = new ArrayList<Item>();
		for(Item b:Inventory.getItems())
			if (filter.accept(b))
				list.add(b);
		return (Item[])list.toArray();
	}
	
	public static Item[] getInvItems(final String[] names) {
		return getInvItems(new Filter<Item>() {
			@Override
			public boolean accept(Item i) {
				return Misc.inStrArr(i.getName(), names);
			}			
		});
	}
	
	public static Item[] getInvItems(final String name) {
		return getInvItems(new String[] {name});
	}
	
	public static Item[] getInvItems(final int...ids) {
		return getInvItems(new Filter<Item>() {
			@Override
			public boolean accept(Item i) {
				return Misc.inIntArr(i.getId(), ids);
			}			
		});
	}
	
	public static Item getInvItem(final Filter<Item> filter) {
		for(Item b:Inventory.getItems())
			if (filter.accept(b))
				return b;
		return null;
	}
	
	public static Item getInvItem(final int id) {
		return getInvItem(new Filter<Item>() {
			@Override
			public boolean accept(Item b) {
				return (b.getId() == id);
			}});
	}
	
	public static Item getInvItem(final String name) {
		return getInvItem(new Filter<Item>() {
			@Override
			public boolean accept(Item b) {
				return (b.getName() == name);
			}});
	}
	
	public static boolean itemsInInv(final Filter<Item> filter) {
		return getInvItems(filter).length > 0;
	}
	
	public static boolean itemsInInv(final int...ids) {
		return getInvItems(ids).length > 0;
	}
	
	public static boolean itemsInInv(final String[] names) {
		return getInvItems(names).length > 0;
	}
	
	public static boolean itemInInv(final Filter<Item> filter) {
		return getInvItem(filter) != null;
	}
	
	public static boolean itemInInv(final int id) {
		return getInvItem(id) != null;
	}
	
	public static boolean itemInInv(final String name) {
		return getInvItem(name) != null;
	}
	
}
