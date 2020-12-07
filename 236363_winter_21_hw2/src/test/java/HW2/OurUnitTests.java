package HW2;

import HW2.business.ReturnValue;
import HW2.business.Student;
import HW2.business.Supervisor;
import HW2.business.Test;

import static org.junit.Assert.assertEquals;

public class OurUnitTests extends AbstractTest {
    @org.junit.Test
    public void AverageTest() {

        ReturnValue res;
        Test s = new Test();
        s.setId(1);
        s.setSemester(1);
        s.setRoom(233);
        s.setDay(1);
        s.setTime(1);
        s.setCreditPoints(3);

        res = Solution.addTest(s);
        assertEquals(ReturnValue.OK, res);
        for (int i = 1; i < 4; i++) {
            Supervisor a = new Supervisor();
            a.setId(i);
            a.setName("Roei" + i);
            a.setSalary(i * 5);
            ReturnValue ret = Solution.addSupervisor(a);
            assertEquals(ReturnValue.OK, ret);

            ret = Solution.supervisorOverseeTest(i, 1, 1);
            assertEquals(ReturnValue.OK, ret);
        }

        float average = Solution.averageTestCost();
        assertEquals(average, 10, 0.1);

    }
}

