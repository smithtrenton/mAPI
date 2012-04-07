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
