package dto;

public class ScheduleDTO {
	private int schedule_seq;
	private String schedule_starttime;
	private String schedule_endtime;
	private String location_id;
	private String schedule_plan;
	private String schedule_budget;
	private String schedule_ref;
	
	public int getSchedule_seq() {
		return schedule_seq;
	}
	public void setSchedule_seq(int schedule_seq) {
		this.schedule_seq = schedule_seq;
	}
	public String getSchedule_starttime() {
		return schedule_starttime;
	}
	public void setSchedule_starttime(String schedule_starttime) {
		this.schedule_starttime = schedule_starttime;
	}
	public String getSchedule_endtime() {
		return schedule_endtime;
	}
	public void setSchedule_endtime(String schedule_endtime) {
		this.schedule_endtime = schedule_endtime;
	}
	public String getLocation_id() {
		return location_id;
	}
	public void setLocation_id(String location_id) {
		this.location_id = location_id;
	}
	public String getSchedule_plan() {
		return schedule_plan;
	}
	public void setSchedule_plan(String schedule_plan) {
		this.schedule_plan = schedule_plan;
	}
	public String getSchedule_budget() {
		return schedule_budget;
	}
	public void setSchedule_budget(String schedule_budget) {
		this.schedule_budget = schedule_budget;
	}
	public String getSchedule_ref() {
		return schedule_ref;
	}
	public void setSchedule_ref(String schedule_ref) {
		this.schedule_ref = schedule_ref;
	}
}