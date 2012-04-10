package mAPI;

import java.awt.Point;
import java.awt.Rectangle;

import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import mAPI.Constants;

public class BankItem extends ItemEx {
	
	public BankItem(int index) {
		super(Constants.INDEX_BANK, Constants.BANK_ITEM_ARRAY, index);
	}
	
	public Rectangle getBankBox() {
		return Widgets.get(Constants.INDEX_BANK, Constants.BANK_ITEM_ARRAY).getBoundingRectangle();
	}
	
	public int getGlobalX() {
		return this.getWidgetChild().getParent().getAbsoluteX() + this.getWidgetChild().getRelativeX();
	}
	
	public int getGlobalY() {
		return this.getWidgetChild().getParent().getAbsoluteY() + this.getWidgetChild().getRelativeY();
	}
	
	public int getAbsoluteX() {
		if (this.getBankBox().contains(new Point(this.getGlobalX(), this.getGlobalY())))
			return (int) this.getGlobalX();
		return -1;
	}
	
	public int getAbsoluteY() {
		if (this.getBankBox().contains(new Point(this.getGlobalX(), this.getGlobalY())))
			return (int) this.getGlobalY();
		return -1;
	}
	
	public Point getAbsoluteLocation() {
		return new Point(this.getAbsoluteX(), this.getAbsoluteY());
	}
	
	public boolean isOnScreen() {
		return this.getBankBox().contains(this.getAbsoluteLocation());
	}
	
	public boolean scrollTo() {
		if (this.isOnScreen())
			return true;
		return Widgets.scroll(Widgets.get(Constants.INDEX_BANK, Constants.BANK_SCROLLBAR), this.getWidgetChild());
	}
	
	public boolean withdraw(int amount) {
		if (this.scrollTo()) 
			switch(amount) {
			case -1:
				return this.getWidgetChild().interact("Withdraw-All but one");
			case 0:
				return this.getWidgetChild().interact("Withdraw-All");
			case 1:
				return this.getWidgetChild().click(true);
			default:
				String[] actions = this.getWidgetChild().getActions();
				for(String str:actions)
					if (str.equals("Withdraw-" + amount))
						return this.getWidgetChild().interact("Withdraw-" + amount);
				Timer timeout = new Timer(3500);
				//TO-DO: Solid method for detection of box!!!
				while (!Widgets.get(Constants.BANK_AMOUNT_INDEX, Constants.BANK_AMOUNT_VISIBLE).isVisible()) {
					if (timeout.getRemaining() == 0) return false;
					this.getWidgetChild().interact("Withdraw-X");
					Time.sleep(Random.nextInt(1200, 1600));
				}					
				Keyboard.sendText("" + amount, true);
				return true;
			}
		return false;
	}
	
}
