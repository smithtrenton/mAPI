package mAPI;

import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class Item extends WidgetChild {
	
	public Item(int parent, int array, int index) {
		super(Widgets.get(parent), Widgets.get(parent, array), index);
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
	
	public boolean exists() {
		return getID() != -1;
	}

}
