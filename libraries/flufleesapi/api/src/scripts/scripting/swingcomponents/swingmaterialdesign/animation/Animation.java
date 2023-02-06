package scripts.scripting.swingcomponents.swingmaterialdesign.animation;

public abstract class Animation {
	protected int duration = 999;
	
	public int getDuration() {
		return this.duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
}
