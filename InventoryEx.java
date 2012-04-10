package mAPI;

import java.util.ArrayList;

import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Filter;

public class InventoryEx {
	
	public static ItemEx[] getInvItems() {
		int parent, array;
		if (Bank.bankScreen()) {
			parent = Constants.INDEX_BANKINV;
			array = Constants.BANKINV_ITEM_ARRAY;
		} else if (Bank.depositBoxScreen()) {
			parent = Constants.INDEX_DEPOSIT_BOX;
			array = Constants.DEPOSIT_BOX_ITEM_ARRAY;
		} else return (ItemEx[])Inventory.getItems();
		
		ArrayList<ItemEx> list = new ArrayList<ItemEx>();
		
		for(int i = 0; i < 28; i++) {
			ItemEx item = new ItemEx(parent, array, i);
			if (item.exists())
				list.add(item);
		}
		
		Object[] result = list.toArray();
		return (ItemEx[])result;
	}
	
	public static int getInvCount() {
		return getInvItems().length;
	}
	
	public static boolean invFull() {
		return getInvCount() >= 28;
	}
	
	public static boolean invEmpty() {
		return getInvCount() == 0;
	}
	
	public static ItemEx[] getInvItems(final Filter<ItemEx> filter) {
		//for(ItemEx b:getInvItems())
		//	if (filter.accept(b))
		//		return b;
		return null;
	}
	
	public static ItemEx getInvItem(final Filter<ItemEx> filter) {
		for(ItemEx b:getInvItems())
			if (filter.accept(b))
				return b;
		return null;
	}
	
	public static ItemEx getInvItem(final int id) {
		return getInvItem(new Filter<ItemEx>() {
			@Override
			public boolean accept(ItemEx b) {
				return (b.getId() == id);
			}});
	}
	
	public static ItemEx getInvItem(final String name) {
		return getInvItem(new Filter<ItemEx>() {
			@Override
			public boolean accept(ItemEx b) {
				return (b.getName() == name);
			}});
	}
	
	public static boolean itemInInv(final Filter<ItemEx> filter) {
		return getInvItem(filter).exists();
	}
	
	public static boolean itemInInv(final int id) {
		return getInvItem(id).exists();
	}
	
	public static boolean itemInInv(final String name) {
		return getInvItem(name).exists();
	}
	
}
