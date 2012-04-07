package mAPI;

import java.util.ArrayList;

import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Npcs;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.interactive.Npc;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import mAPI.Constants;
import mAPI.Misc;

public class Bank {
	
	public boolean bankScreen() {
		return Widgets.get(Constants.INDEX_BANK).validate();
	}
	
	public boolean depositBoxScreen() {
		return Widgets.get(Constants.INDEX_DEPOSIT_BOX).validate();
	}
	
	public void closeBank() {
		WidgetChild btn = Widgets.get(Constants.INDEX_BANK, Constants.BANK_CLOSE);
		if (btn.validate())
			btn.click(true);
	}
	
	public boolean closeBankWait() {
		Timer timeout = new Timer(3000);
		while (bankScreen()) {
			if (timeout.getRemaining() <= 0)
				return false;
			closeBank();
			Time.sleep(Random.nextInt(800, 1000));
		}
		return !bankScreen();
	}
	
	public WidgetChild[] getBankArray() {
		return Widgets.get(Constants.INDEX_BANK, Constants.BANK_ITEM_ARRAY).getChildren();
	}
	
	public BankItem[] getBankItems() {
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
	
	public int getBankCount() {
		return getBankItems().length;
	}
	
	public BankItem getBankItem(final Filter<BankItem> filter) {
		for(BankItem b:getBankItems())
			if (filter.accept(b))
				return b;
		return null;
	}
	
	public BankItem getBankItem(final String name) {
		return getBankItem(new Filter<BankItem>() {
			@Override
			public boolean accept(BankItem b) {
				return (b.getName().equals(name));
			}			
		});
	}
	
	public BankItem getBankItem(final int id) {
		return getBankItem(new Filter<BankItem>() {
			@Override
			public boolean accept(BankItem b) {
				return (b.getID() == id);
			}			
		});
	}
	
	public boolean itemInBank(final Filter<BankItem> filter) {
		return (getBankItem(filter) != null);
	}
	
	public boolean itemInBank(final String name) {
		return itemInBank(new Filter<BankItem>() {
			@Override
			public boolean accept(BankItem b) {
				return (b.getName().equals(name));
			}			
		});
	}
	
	public boolean itemInBank(final int id) {
		return itemInBank(new Filter<BankItem>() {
			@Override
			public boolean accept(BankItem b) {
				return (b.getID() == id);
			}			
		});
	}
	
	public boolean withdrawItem(final Filter<BankItem> filter, int amount) {
		if(!bankScreen() || !itemInBank(filter) || amount < -1)
			return false;
		
		return getBankItem(filter).withdraw(amount);
	}
	
	public boolean withdrawItem(final String name, final int amount) {
		return withdrawItem(new Filter<BankItem>() {
			@Override
			public boolean accept(BankItem b) {
				return (b.getName().equals(name));
			}			
		}, amount);
	}
	
	public boolean withdrawItem(final int id, final int amount) {
		return withdrawItem(new Filter<BankItem>() {
			@Override
			public boolean accept(BankItem b) {
				return (b.getID() == id);
			}			
		}, amount);
	}
	
	public BankInvItem[] getBankInvItems() {
		if (!bankScreen()) return null;
		
		ArrayList<BankInvItem> list = new ArrayList<BankInvItem>();
		
		for(int i = 0; i < 28; i++) {
			BankInvItem item = new BankInvItem(i);
			if (item.exists())
				list.add(item);
		}
		
		return (BankInvItem[])list.toArray();
	}
	
	public int getBankInvCount() {
		return getBankInvItems().length;
	}
	
	public BankInvItem getBankInvItem(final Filter<BankInvItem> filter) {
		for(BankInvItem b:getBankInvItems())
			if (filter.accept(b))
				return b;
		return null;
	}
	
	public BankInvItem getBankInvItem(final int id) {
		return getBankInvItem(new Filter<BankInvItem>() {
			@Override
			public boolean accept(BankInvItem b) {
				return (b.getID() == id);
			}});
	}
	
	public BankInvItem getBankInvItem(final String name) {
		return getBankInvItem(new Filter<BankInvItem>() {
			@Override
			public boolean accept(BankInvItem b) {
				return (b.getName() == name);
			}});
	}
	
	public boolean itemInBankInv(final Filter<BankInvItem> filter) {
		return getBankInvItem(filter).exists();
	}
	
	public boolean itemInBankInv(final int id) {
		return getBankInvItem(id).exists();
	}
	
	public boolean itemInBankInv(final String name) {
		return getBankInvItem(name).exists();
	}
	
	public boolean depositItem(final Filter<BankInvItem> filter, int amount) {
		return getBankInvItem(filter).deposit(amount);
	}
	
	public boolean depositItem(final int id, int amount) {
		return getBankInvItem(id).deposit(amount);
	}
	
	public boolean depositItem(final String name, int amount) {
		return getBankInvItem(name).deposit(amount);
	}
	
	public boolean quickDeposit(final int button) {
		if(depositBoxScreen())
			return Widgets.get(Constants.INDEX_DEPOSIT_BOX, Constants.DEPOSIT_BOX_DEPOSIT_OFFSET + button).click(true);
		return Widgets.get(Constants.INDEX_BANK, Constants.BANK_DEPOSIT_OFFSET  + button).click(true);
	}
	
	public boolean depositSummon() {
		return quickDeposit(Constants.BANK_DEPOSIT_SUMMONING);
	}
	
	public boolean depositEquipped() {
		return quickDeposit(Constants.BANK_DEPOSIT_EQUIPPED);
	}
	
	public boolean depositCoins() {
		return quickDeposit(Constants.BANK_DEPOSIT_PURSE);
	}
	
	public boolean depositAll() {
		return quickDeposit(Constants.BANK_DEPOSIT_ALL);
	}
	
	public boolean depositAll(int...ids) {
		boolean quick = true;		
		for(BankInvItem item: getBankInvItems())
			if (!Misc.inIntArr(item.getID(), ids))
				quick = false;					
		if (quick) return depositAll();
		
		for(BankInvItem item: getBankInvItems())
			if (Misc.inIntArr(item.getID(), ids))
				item.deposit(0);
		return true;
	}
	
	public boolean depositAll(String[] names) {
		boolean quick = true;		
		for(BankInvItem item: getBankInvItems())
			if (!Misc.inStrArr(item.getName(), names))
				quick = false;					
		if (quick) return depositAll();
		
		for(BankInvItem item: getBankInvItems())
			if (Misc.inStrArr(item.getName(), names))
				item.deposit(0);
		return true;
	}
	
	public boolean depositAllBut(int...ids) {
		boolean quick = true;		
		for(BankInvItem item: getBankInvItems())
			if (Misc.inIntArr(item.getID(), ids))
				quick = false;					
		if (quick) return depositAll();
		
		for(BankInvItem item: getBankInvItems())
			if (!Misc.inIntArr(item.getID(), ids))
				item.deposit(0);
		return true;
	}
	
	public boolean depositAllBut(String[] names) {
		boolean quick = true;		
		for(BankInvItem item: getBankInvItems())
			if (Misc.inStrArr(item.getName(), names))
				quick = false;					
		if (quick) return depositAll();
		
		for(BankInvItem item: getBankInvItems())
			if (!Misc.inStrArr(item.getName(), names))
				item.deposit(0);
		return true;
	}
	
	public boolean depositAllbut(String name) {
		return depositAllBut(new String[] {name});
	}
	
	public int getCurrentBankTab() {
		if (!bankScreen()) return -1;
		for(int i = 0; i<Constants.BANK_TABS.length; i++)
			if (Widgets.get(Constants.INDEX_BANK, Constants.BANK_TABS[i] - 1).getTextureId() == Constants.BANK_TAB_SELECTED)
				return i;
		return -1;
	}
	
	public boolean openBankTab(int tab) {
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
