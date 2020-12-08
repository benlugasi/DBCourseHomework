package HW2;

import HW2.business.ReturnValue;
import HW2.business.Student;
import HW2.business.Supervisor;
import HW2.business.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

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

        s = new Test();
        s.setId(2);
        s.setSemester(3);
        s.setRoom(233);
        s.setDay(2);
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
        Supervisor a = new Supervisor();
        a.setId(5);
        a.setName("Roei" + 5);
        a.setSalary(50);
        ReturnValue ret = Solution.addSupervisor(a);
        assertEquals(ReturnValue.OK, ret);

        ret = Solution.supervisorOverseeTest(5, 2, 3);
        assertEquals(ReturnValue.OK, ret);

        float average = Solution.averageTestCost();
        assertEquals(average, 30, 0.1);

    }
    @org.junit.Test
    public void supervisorOverseeTest() {

        ReturnValue res;
        for (int i = 1; i < 10; i++) {
            Test s = new Test();
            s.setId(i);
            s.setSemester(1);
            s.setRoom(233);
            s.setDay(1);
            s.setTime(1);
            s.setCreditPoints(3);
            res = Solution.addTest(s);
            assertEquals(ReturnValue.OK, res);
        }

        for (int i = 1; i < 10; i++) {
            Student s = new Student();
            s.setId(i);
            s.setFaculty("CS");
            s.setName("Moshe"+ i);
            s.setCreditPoints(3);
            res = Solution.addStudent(s);
            assertEquals(ReturnValue.OK, res);
        }

        for (int i = 1; i < 5; i++) {
            Supervisor a = new Supervisor();
            a.setId(i);
            a.setName("Roei" + i);
            a.setSalary(i * 5);
            ReturnValue ret = Solution.addSupervisor(a);
            assertEquals(ReturnValue.OK, ret);

        }
        Solution.studentAttendTest(1, 1, 1);
        Solution.studentAttendTest(1, 2, 1);
        Solution.studentAttendTest(1, 3, 1);
        Solution.studentAttendTest(2, 1, 1);
        Solution.studentAttendTest(2, 2, 1);
        Solution.studentAttendTest(3, 2, 1);
        Solution.studentAttendTest(4, 2, 1);
        Solution.studentAttendTest(5, 2, 1);
        Solution.studentAttendTest(6, 1, 1);
        Solution.studentAttendTest(6, 2, 1);

        Solution.supervisorOverseeTest(1, 1, 1);
        Solution.supervisorOverseeTest(1, 2, 1);
        Solution.supervisorOverseeTest(1, 3, 1);

        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(6);
        expected.add(2);
        expected.add(1);

        ArrayList<Integer> oversee = Solution.supervisorOverseeStudent();
        assertArrayEquals(expected.toArray(), oversee.toArray());

    }
    @org.junit.Test
    public void halfwayThereTest() {

        ReturnValue res;

        for (int i = 1; i < 10; i++) {
            Student s = new Student();
            s.setId(i);
            s.setFaculty("CS");
            s.setName("Moshe"+ i);
            s.setCreditPoints(11 * i);
            res = Solution.addStudent(s);
            assertEquals(ReturnValue.OK, res);
        }

        assertFalse(Solution.studentHalfWayThere(1));
        assertFalse(Solution.studentHalfWayThere(2));
        assertFalse(Solution.studentHalfWayThere(3));
        assertFalse(Solution.studentHalfWayThere(4));
        assertFalse(Solution.studentHalfWayThere(5));
        assertTrue(Solution.studentHalfWayThere(6));
        assertTrue(Solution.studentHalfWayThere(7));
        assertTrue(Solution.studentHalfWayThere(8));
        assertTrue(Solution.studentHalfWayThere(9));

        Student s = new Student();
        s.setId(10);
        s.setFaculty("CS");
        s.setName("Mosh");
        s.setCreditPoints(60);
        res = Solution.addStudent(s);
        assertEquals(ReturnValue.OK, res);

        assertTrue(Solution.studentHalfWayThere(10));

    }
    @org.junit.Test
    public void mostPopularTest() {

        Integer zero = 0;
        assertEquals(zero, Solution.getMostPopularTest("CS"));

        ReturnValue res;
        for (int i = 1; i < 10; i++) {
            Test s = new Test();
            s.setId(i);
            s.setSemester(1);
            s.setRoom(233);
            s.setDay(1);
            s.setTime(1);
            s.setCreditPoints(3);
            res = Solution.addTest(s);
            assertEquals(ReturnValue.OK, res);
        }

        assertEquals(zero, Solution.getMostPopularTest("CS"));

        for (int i = 1; i < 7; i++) {
            Student s = new Student();
            s.setId(i);
            s.setFaculty("CS");
            s.setName("Moshe"+ i);
            s.setCreditPoints(3);
            res = Solution.addStudent(s);
            assertEquals(ReturnValue.OK, res);
        }

        for (int i = 7; i < 12; i++) {
            Student s = new Student();
            s.setId(i);
            s.setFaculty("EE");
            s.setName("Moshe"+ i);
            s.setCreditPoints(3);
            res = Solution.addStudent(s);
            assertEquals(ReturnValue.OK, res);
        }

        assertEquals(zero, Solution.getMostPopularTest("CS"));

        Solution.studentAttendTest(1, 2, 1);
        Solution.studentAttendTest(2, 1, 1);
        Solution.studentAttendTest(3, 1, 1);
        Solution.studentAttendTest(4, 2, 1);
        Solution.studentAttendTest(5, 2, 1);
        Solution.studentAttendTest(6, 2, 1);
        Solution.studentAttendTest(7, 1, 1);
        Solution.studentAttendTest(8, 1, 1);
        Solution.studentAttendTest(9, 1, 1);
        Solution.studentAttendTest(10, 1, 1);
        Solution.studentAttendTest(11, 1, 1);

        Integer expected = 2;
        assertEquals(expected, Solution.getMostPopularTest("CS"));
        expected = 1;
        assertEquals(expected, Solution.getMostPopularTest("EE"));

    }
}

