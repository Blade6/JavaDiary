package bean;

import java.util.EventObject;

public class EnrollmentEvent extends EventObject {
	
	private String studentToEnroll;
	
	private int enrollmentCap;
	
	public EnrollmentEvent(Object source, String studentToEnroll,
		int enrollmentCap) {
		super(source);
		this.studentToEnroll = studentToEnroll;
		this.enrollmentCap = enrollmentCap;
	}
	
	public String getStudentToEnroll() {
		return studentToEnroll;
	}
	
	public long getEnrollmentCap() {
		return enrollmentCap;
	}
}
