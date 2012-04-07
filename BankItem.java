package mAPI;

import java.awt.Point;

import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.widget.WidgetChild;
import mAPI.Constants;

public class BankItem extends WidgetChild {
	
	public BankItem(int index) {
		super(Widgets.get(Constants.INDEX_BANK), Widgets.get(Constants.INDEX_BANK, Constants.BANK_ITEM_ARRAY), index);
	}
	
	public int getID() {
		return this.getChildId();
	}
	
	public int getCount() {
		return this.getChildStackSize();
	}
	
	public String getName() {
		return this.getChildName();
	}
	
	public int getX() {
		return this.getParent().getAbsoluteX() + this.getRelativeX();
	}
	
	public int getY() {
		return this.getParent().getAbsoluteY() + this.getRelativeY();
	}
	
	@Override
	public Point getAbsoluteLocation() {
		return new Point(this.getX(), this.getY());
	}
	
	public boolean slotFilled() {
		return getID() != -1;
	}
	
	public boolean scrollTo() {
		if (isOnScreen())
			return true;
		return Widgets.scroll(Widgets.get(Constants.INDEX_BANK, Constants.BANK_SCROLLBAR), this);
	}
	
	public boolean withdraw(int amount) {
		if (this.scrollTo()) 
			switch(amount) {
			case -1:
				return this.interact("Withdraw-All but one");
			case 0:
				return this.interact("Withdraw-All");
			case 1:
				return this.click(true);
			default:
				String[] actions = this.getActions();
				for(String str:actions)
					if (str.equals("Withdraw-" + amount))
						return this.interact("Withdraw-" + amount);
				Timer timeout = new Timer(3500);
				//TO-DO: Solid method for detection of box!!!
				while (!Widgets.get(Constants.BANK_AMOUNT_INDEX, Constants.BANK_AMOUNT_VISIBLE).isVisible()) {
					if (timeout.getRemaining() == 0) return false;
					this.interact("Withdraw-X");
					Time.sleep(Random.nextInt(1200, 1600));
				}					
				Keyboard.sendText("" + amount, true);
				return true;
			}
		return false;
	}
	
}
