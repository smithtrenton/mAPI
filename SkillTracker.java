package mAPI;

import org.powerbot.game.api.methods.tab.Skills;

public class SkillTracker {
	private int startxp, curxp, skill,
		startlvl, curlvl;
	
	public SkillTracker(int skill) {
		this.skill = skill;
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
		return Skills.getExperienceToLevel(this.skill, lvl);
	}
	
	public int getExperienceToLevel() {
		return getExperienceToLevel(this.getCurrentLevel() + 1);
	}
	
	public double getPercentToLvl(int lvl) {
		return 100.0D * (getCurrentExp() / (double)getExperienceToLevel(lvl));
	}
	
	public double getPercentToLvl() {
		return getPercentToLvl(getCurrentLevel() + 1);
	}
	
	public void setup() {
		this.startlvl = Skills.getLevel(skill);
		this.curlvl = this.startlvl;
		this.startxp = Skills.getExperience(skill);
		this.curxp = this.startxp;
	}
	
	public void update() {
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
