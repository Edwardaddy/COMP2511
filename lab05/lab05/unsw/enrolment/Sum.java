package unsw.enrolment;
import java.util.ArrayList;

public class Sum implements Marking{
	@Override
	public int getMark(ArrayList<Grade> grades) {
		int result = 0;
		for (Grade grade : grades) {
			result += grade.getMark();
		}

		return result;
	}
}