package mAPI;

import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.Item;

public class ItemEx extends Item {
	
	public ItemEx(int parent, int array, int index) {
		super(Widgets.get(parent, array).getChildren()[index]);
	}
	
	public boolean bankAction(String prefix, int amount) {
		if (!this.exists())
			return false;
		switch(amount) {
		case -1:
			return this.getWidgetChild().interact(prefix + "-All but one");
		case 0:
			return this.getWidgetChild().interact(prefix + "-All");
		case 1:
			return this.getWidgetChild().click(true);
		default:
			String[] actions = this.getWidgetChild().getActions();
			for(String str:actions)
				if (str.equals(prefix + "-" + amount))
					return this.getWidgetChild().interact(prefix + "-" + amount);
			Timer timeout = new Timer(3500);
			//TO-DO: Solid method for detection of box!!!
			while (!Widgets.get(Constants.BANK_AMOUNT_INDEX, Constants.BANK_AMOUNT_VISIBLE).isVisible()) {
				if (timeout.getRemaining() == 0) return false;
				this.getWidgetChild().interact(prefix + "-X");
				Time.sleep(Random.nextInt(1200, 1600));
			}					
			Keyboard.sendText("" + amount, true);
			return true;
		}
	}
	
	public boolean deposit(int amount) {
		return bankAction("Deposit", amount);
	}
	
	public boolean withdraw(int amount) {
		return bankAction("Withdraw", amount);
	}
	
	public boolean exists() {
		return this.getWidgetChild().validate() && getId() != -1;
	}

}
