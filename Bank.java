package mAPI;

import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Npcs;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.Npc;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import mAPI.Constants;
import mAPI.Misc;

public class Bank {
	
	public static boolean bankScreen() {
		return Widgets.get(Constants.INDEX_BANK).validate();
	}
	
	public static boolean depositBoxScreen() {
		return Widgets.get(Constants.INDEX_DEPOSIT_BOX).validate();
	}
	
	public static void closeBank() {
		WidgetChild btn = Widgets.get(Constants.INDEX_BANK, Constants.BANK_CLOSE);
		if (btn.validate())
			btn.click(true);
	}
	
	public static boolean closeBankWait() {
		Timer timeout = new Timer(3000);
		while (bankScreen()) {
			if (timeout.getRemaining() <= 0)
				return false;
			closeBank();
			Time.sleep(Random.nextInt(800, 1000));
		}
		return !bankScreen();
	}
	
	public static WidgetChild[] getBankArray() {
		return Widgets.get(Constants.INDEX_BANK, Constants.BANK_ITEM_ARRAY).getChildren();
	}
	
	public static BankItem[] getBankItems() {
		if (!bankScreen()) return null;
		WidgetChild[] bank_items = getBankArray();
		int last_index = 0;
		for(int i = 0; i<bank_items.length; i++) {
			if (bank_items[i].getChildId() != -1) 
				last_index = i;
			else break;
		}		
		BankItem[] barr = new BankItem[last_index+1];
		for(int i = 0; i <= last_index; i++)
			barr[i] = new BankItem(i);
		return barr;
	}
	
	public static int getBankCount() {
		return getBankItems().length;
	}
	
	public static BankItem getBankItem(final Filter<BankItem> filter) {
		for(BankItem b:getBankItems())
			if (filter.accept(b))
				return b;
		return null;
	}
	
	public static BankItem getBankItem(final String name) {
		return getBankItem(new Filter<BankItem>() {
			@Override
			public boolean accept(BankItem b) {
				return (b.getName().equals(name));
			}			
		});
	}
	
	public static BankItem getBankItem(final int id) {
		return getBankItem(new Filter<BankItem>() {
			@Override
			public boolean accept(BankItem b) {
				return (b.getId() == id);
			}			
		});
	}
	
	public static boolean itemInBank(final Filter<BankItem> filter) {
		return (getBankItem(filter) != null);
	}
	
	public static boolean itemInBank(final String name) {
		return itemInBank(new Filter<BankItem>() {
			@Override
			public boolean accept(BankItem b) {
				return (b.getName().equals(name));
			}			
		});
	}
	
	public static boolean itemInBank(final int id) {
		return itemInBank(new Filter<BankItem>() {
			@Override
			public boolean accept(BankItem b) {
				return (b.getId() == id);
			}			
		});
	}
	
	public static boolean withdrawItem(final Filter<BankItem> filter, int amount) {
		if(!bankScreen() || !itemInBank(filter) || amount < -1)
			return false;
		
		return getBankItem(filter).withdraw(amount);
	}
	
	public static boolean withdrawItem(final String name, final int amount) {
		return withdrawItem(new Filter<BankItem>() {
			@Override
			public boolean accept(BankItem b) {
				return (b.getName().equals(name));
			}			
		}, amount);
	}
	
	public static boolean withdrawItem(final int id, final int amount) {
		return withdrawItem(new Filter<BankItem>() {
			@Override
			public boolean accept(BankItem b) {
				return (b.getId() == id);
			}			
		}, amount);
	}	
	
	public static boolean depositItem(final Filter<ItemEx> filter, int amount) {
		return InventoryEx.getInvItem(filter).deposit(amount);
	}
	
	public static boolean depositItem(final int id, int amount) {
		return InventoryEx.getInvItem(id).deposit(amount);
	}
	
	public static boolean depositItem(final String name, int amount) {
		return InventoryEx.getInvItem(name).deposit(amount);
	}
	
	public static boolean quickDeposit(final int button) {
		if(depositBoxScreen())
			return Widgets.get(Constants.INDEX_DEPOSIT_BOX, Constants.DEPOSIT_BOX_DEPOSIT_OFFSET + button).click(true);
		return Widgets.get(Constants.INDEX_BANK, Constants.BANK_DEPOSIT_OFFSET  + button).click(true);
	}
	
	public static boolean depositSummon() {
		return quickDeposit(Constants.BANK_DEPOSIT_SUMMONING);
	}
	
	public static boolean depositEquipped() {
		return quickDeposit(Constants.BANK_DEPOSIT_EQUIPPED);
	}
	
	public static boolean depositCoins() {
		return quickDeposit(Constants.BANK_DEPOSIT_PURSE);
	}
	
	public static boolean depositAll() {
		return quickDeposit(Constants.BANK_DEPOSIT_ALL);
	}
	
	public static boolean depositAll(int...ids) {
		boolean quick = true;		
		for(ItemEx item: InventoryEx.getInvItems())
			if (!Misc.inIntArr(item.getId(), ids))
				quick = false;					
		if (quick) return depositAll();
		
		for(ItemEx item: InventoryEx.getInvItems())
			if (Misc.inIntArr(item.getId(), ids))
				item.deposit(0);
		return true;
	}
	
	public static boolean depositAll(String[] names) {
		boolean quick = true;		
		for(ItemEx item: InventoryEx.getInvItems())
			if (!Misc.inStrArr(item.getName(), names))
				quick = false;					
		if (quick) return depositAll();
		
		for(ItemEx item: InventoryEx.getInvItems())
			if (Misc.inStrArr(item.getName(), names))
				item.deposit(0);
		return true;
	}
	
	public static boolean depositAll(String name) {
		return depositAll(new String[] {name});
	}
	
	public static boolean depositAllBut(int...ids) {
		boolean quick = true;		
		for(ItemEx item: InventoryEx.getInvItems())
			if (Misc.inIntArr(item.getId(), ids))
				quick = false;					
		if (quick) return depositAll();
		
		for(ItemEx item: InventoryEx.getInvItems())
			if (!Misc.inIntArr(item.getId(), ids))
				item.deposit(0);
		return true;
	}
	
	public static boolean depositAllBut(String[] names) {
		boolean quick = true;		
		for(ItemEx item: InventoryEx.getInvItems())
			if (Misc.inStrArr(item.getName(), names))
				quick = false;					
		if (quick) return depositAll();
		
		for(ItemEx item: InventoryEx.getInvItems())
			if (!Misc.inStrArr(item.getName(), names))
				item.deposit(0);
		return true;
	}
	
	public static boolean depositAllBut(String name) {
		return depositAllBut(new String[] {name});
	}
	
	public static int getCurrentBankTab() {
		if (!bankScreen()) return -1;
		for(int i = 0; i<Constants.BANK_TABS.length; i++)
			if (Widgets.get(Constants.INDEX_BANK, Constants.BANK_TABS[i] - 1).getTextureId() == Constants.BANK_TAB_SELECTED)
				return i;
		return -1;
	}
	
	public static boolean openBankTab(int tab) {
		if (getCurrentBankTab() == tab)
			return true;
		
		WidgetChild target = Widgets.get(Constants.INDEX_BANK, Constants.BANK_TABS[tab]);
		if (target.getChildId() == -1) return false;
		Timer timeout = new Timer(3500);
		while(getCurrentBankTab() != tab) {
			if (timeout.getRemaining() == 0) break;
			target.click(true);
			Time.sleep(Random.nextInt(750, 900));
		}
		
		return getCurrentBankTab() == tab;
	}
	
	public static boolean openBankNPC() {
		Npc banker = Npcs.getNearest(new Filter<Npc>() {

			@Override
			public boolean accept(Npc n) {
				String[] act = n.getActions();
				for(int i = 0; i<act.length; i++)
					if (act[i].contains("Bank"))
						return true;
				return false;
			}
			
		});
		return banker.interact("Bank");
	}
		
}
