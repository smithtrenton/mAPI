package mAPI;

import org.powerbot.game.api.methods.tab.Skills;

public class SkillTracker {
	private int startxp, curxp, skill,
		startlvl, curlvl;
	private boolean setup;
	
	public SkillTracker(int skill) {
		this.skill = skill;
		this.setup = false;
	}
	
	public int getStartExp() {
		return startxp;
	}
	
	public int getCurrentExp() {
		return curxp;
	}
	
	public int getGainedExp() {
		return curxp - startxp;
	}
	
	public int getCurrentLevel() {
		return curlvl;
	}
	
	public int getStartLevel() {
		return startlvl;
	}
	
	public int getGainedLevel() {
		return curlvl - startlvl;
	}

	public int getExperienceToLevel(int lvl) {
		return Skills.getExperienceRequired(lvl) - getCurrentExp();
	}
	
	public int getExperienceToNextLevel() {
		return getExperienceToLevel(getCurrentLevel() + 1);
	}
	
	public double getPercentToLvl(int lvl) {
		return 100.0D * ((double)(getCurrentExp() - Skills.getExperienceRequired(lvl-1)) / (Skills.getExperienceRequired(lvl) - Skills.getExperienceRequired(lvl-1)));
	}
	
	public double getPercentToNextLvl() {
		return getPercentToLvl(getCurrentLevel() + 1);
	}
	
	public double getExpPerHour(long msec) {
		return Misc.getRate(getGainedExp(), msec);
	}
	
	public boolean isSetup() {
		return setup;
	}
	
	public void update() {
		if (!setup) {
			this.startxp = Skills.getExperience(skill);
			this.startlvl = Skills.getLevel(skill);	
			setup = true;
		}
		this.curxp = Skills.getExperience(skill);
		this.curlvl = Skills.getLevel(skill);
	}
	
	public String getSkillName() {
		switch(this.skill){
		case Skills.AGILITY :
			return "Agility";
		case Skills.ATTACK :
			return "Attack";
		case Skills.CONSTITUTION :
			return "Constitution";
		case Skills.CONSTRUCTION :
			return "Construction";
		case Skills.COOKING :
			return "Cooking";
		case Skills.CRAFTING :
			return "Crafting";
		case Skills.DEFENSE :
			return "Defense";
		case Skills.DUNGEONEERING :
			return "Dungeoneering";
		case Skills.FARMING :
			return "Farming";
		case Skills.FIREMAKING :
			return "Firemaking";
		case Skills.FISHING :
			return "Fishing";
		case Skills.FLETCHING :
			return "Fletching";
		case Skills.HERBLORE :
			return "Herblore";
		case Skills.HUNTER :
			return "Hunter";
		case Skills.MAGIC :
			return "Magic";
		case Skills.MINING :
			return "Mining";
		case Skills.PRAYER :
			return "Prayer";
		case Skills.RANGE :
			return "Range";
		case Skills.RUNECRAFTING :
			return "Runecrafting";
		case Skills.SLAYER :
			return "Slayer";
		case Skills.SMITHING :
			return "Smithing";
		case Skills.STRENGTH :
			return "Strength";
		case Skills.SUMMONING :
			return "Summoning";
		case Skills.THIEVING :
			return "Thieving";
		case Skills.WOODCUTTING :
			return "Woodcutting";
		default :
			return "";
		}
	}
	
}
