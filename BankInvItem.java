package mAPI;

import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;

public class BankInvItem extends Item {
	
	public BankInvItem(int index) {
		super(Constants.INDEX_BANKINV, Constants.BANKINV_ITEM_ARRAY, index);
	}
	
	public boolean deposit(int amount) {
		if (!this.validate())
			return false;
		switch(amount) {
		case -1:
			return this.interact("Deposit-All but one");
		case 0:
			return this.interact("Deposit-All");
		case 1:
			return this.click(true);
		default:
			String[] actions = this.getActions();
			for(String str:actions)
				if (str.equals("Deposit-" + amount))
					return this.interact("Deposit-" + amount);
			Timer timeout = new Timer(3500);
			//TO-DO: Solid method for detection of box!!!
			while (!Widgets.get(Constants.BANK_AMOUNT_INDEX, Constants.BANK_AMOUNT_VISIBLE).isVisible()) {
				if (timeout.getRemaining() == 0) return false;
				this.interact("Deposit-X");
				Time.sleep(Random.nextInt(1200, 1600));
			}					
			Keyboard.sendText("" + amount, true);
			return true;
		}
	}

}
