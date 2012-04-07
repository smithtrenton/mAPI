package mAPI;

import java.awt.Point;
import java.awt.Rectangle;

import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import mAPI.Constants;

public class BankItem extends Item {
	
	public BankItem(int index) {
		super(Constants.INDEX_BANK, Constants.BANK_ITEM_ARRAY, index);
	}
	
	public Rectangle getBankBox() {
		return Widgets.get(Constants.INDEX_BANK, Constants.BANK_ITEM_ARRAY).getBoundingRectangle();
	}
	
	public int getGlobalX() {
		return this.getParent().getAbsoluteX() + this.getRelativeX();
	}
	
	public int getGlobalY() {
		return this.getParent().getAbsoluteY() + this.getRelativeY();
	}
	
	@Override
	public int getAbsoluteX() {
		if (this.getBankBox().contains(new Point(this.getGlobalX(), this.getGlobalY())))
			return (int) this.getGlobalX();
		return -1;
	}
	
	@Override
	public int getAbsoluteY() {
		if (this.getBankBox().contains(new Point(this.getGlobalX(), this.getGlobalY())))
			return (int) this.getGlobalY();
		return -1;
	}
	
	@Override
	public Point getAbsoluteLocation() {
		return new Point(this.getAbsoluteX(), this.getAbsoluteY());
	}
	
	@Override
	public boolean isOnScreen() {
		return this.getBankBox().contains(this.getAbsoluteLocation());
	}
	
	public boolean scrollTo() {
		if (this.isOnScreen())
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
