package HW2;

import HW2.business.ReturnValue;
import HW2.business.Student;
import HW2.business.Supervisor;
import HW2.business.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class OurUnitTests extends AbstractTest {
    @org.junit.Test
    public void basicAPITest() {
        ReturnValue res;
        Test test = new Test();
        res = Solution.addTest(test);
        assertEquals(ReturnValue.BAD_PARAMS, res);
        test.setId(1);
        test.setSemester(1);
        test.setRoom(233);
        test.setDay(1);
        test.setTime(1);
        test.setCreditPoints(3);
        res = Solution.addTest(test);
        assertEquals(ReturnValue.OK, res);
        res = Solution.addTest(test);
        assertEquals(ReturnValue.ALREADY_EXISTS, res);

        Supervisor sup = new Supervisor();
        res = Solution.addSupervisor(sup);
        assertEquals(ReturnValue.BAD_PARAMS, res);
        sup.setId(1);
        sup.setName("Ben");
        sup.setSalary(5);
        res = Solution.addSupervisor(sup);
        assertEquals(ReturnValue.OK, res);
        res = Solution.addSupervisor(sup);
        assertEquals(ReturnValue.ALREADY_EXISTS, res);

        Student stud = new Student();
        res = Solution.addStudent(stud);
        assertEquals(ReturnValue.BAD_PARAMS, res);
        stud.setId(1);
        stud.setName("Benny");
        stud.setFaculty("CS");
        stud.setCreditPoints(100);
        res = Solution.addStudent(stud);
        assertEquals(ReturnValue.OK, res);
        res = Solution.addStudent(stud);
        assertEquals(ReturnValue.ALREADY_EXISTS, res);

        Test test2 = Solution.getTestProfile(1,1);
        assertEquals(test, test2);
        test2 = Solution.getTestProfile(1,2);
        assertEquals(Test.badTest(), test2);
        Student stud2 = Solution.getStudentProfile(1);
        assertEquals(stud, stud2);
        stud2 = Solution.getStudentProfile(2);
        assertEquals(Student.badStudent(), stud2);
        Supervisor sup2 = Solution.getSupervisorProfile(1);
        assertEquals(sup, sup2);
        sup2 = Solution.getSupervisorProfile(2);
        assertEquals(Supervisor.badSupervisor(), sup2);

        res = Solution.studentAttendTest(1,1,1);
        assertEquals(ReturnValue.OK, res);
        res = Solution.studentAttendTest(1,1,1);
        assertEquals(ReturnValue.ALREADY_EXISTS, res);
        res = Solution.studentAttendTest(2,1,1);
        assertEquals(ReturnValue.NOT_EXISTS, res);
        res = Solution.studentWaiveTest(1,1,1);
        assertEquals(ReturnValue.OK, res);
        res = Solution.studentWaiveTest(1,1,1);
        assertEquals(ReturnValue.NOT_EXISTS, res);
        res = Solution.supervisorOverseeTest(1,1,1);
        assertEquals(ReturnValue.OK, res);
        res = Solution.supervisorOverseeTest(1,1,1);
        assertEquals(ReturnValue.ALREADY_EXISTS, res);
        res = Solution.supervisorOverseeTest(2,1,1);
        assertEquals(ReturnValue.NOT_EXISTS, res);
        res = Solution.supervisorStopsOverseeTest(1,1,1);
        assertEquals(ReturnValue.OK, res);
        res = Solution.supervisorStopsOverseeTest(1,1,1);
        assertEquals(ReturnValue.NOT_EXISTS, res);

        res = Solution.deleteTest(1,1);
        assertEquals(ReturnValue.OK, res);
        res = Solution.deleteTest(1,1);
        assertEquals(ReturnValue.NOT_EXISTS, res);
        res = Solution.deleteStudent(1);
        assertEquals(ReturnValue.OK, res);
        res = Solution.deleteStudent(1);
        assertEquals(ReturnValue.NOT_EXISTS, res);
        res = Solution.deleteSupervisor(1);
        assertEquals(ReturnValue.OK, res);
        res = Solution.deleteSupervisor(1);
        assertEquals(ReturnValue.NOT_EXISTS, res);
    }
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
        s.setSemester(2);
        s.setRoom(222);
        s.setDay(2);
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
        assertEquals(average, 20, 0.1);

    }
//    @org.junit.Ignore
    @org.junit.Test
    public void advancedAverageTest() {
        ReturnValue res;
        int nrTests = 120;
        int nrTestsToWatch = 100;
        int nrSupervisorsPerTest = 10;
        float exp_avg = 0.0F;
        float exp_sum = 0.0F;
        for (int i = 1; i <= nrTests; i++) {
            Test s = new Test();
            s.setId(i);
            s.setSemester(i);
            s.setRoom(233+i);
            s.setDay(1);
            s.setTime(1);
            s.setCreditPoints(3);
            res = Solution.addTest(s);
            assertEquals(ReturnValue.OK, res);
            if(i<=nrTestsToWatch) {
                for (int j = 1; j <= nrSupervisorsPerTest; j++) {
                    Supervisor a = new Supervisor();
                    a.setId(i * nrTests + j);
                    a.setName("Roei" + i * nrTests + j);
                    a.setSalary(i * nrTests * j);
                    ReturnValue ret = Solution.addSupervisor(a);
                    assertEquals(ReturnValue.OK, ret);

                    ret = Solution.supervisorOverseeTest(i * nrTests + j, i, i);
                    assertEquals(ReturnValue.OK, ret);
                    exp_sum += i * nrTests * j;
                }
                exp_avg += exp_sum / nrSupervisorsPerTest;
                exp_sum = 0.0F;
            }
        }
        exp_avg /= nrTests;
        float average = Solution.averageTestCost();
        assertEquals(average, exp_avg, 0.1);
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
    @org.junit.Test
    public void getWageTest() {
        ReturnValue res;
        int nrTests = 10;
        int supervisorSalary = 5;
        int exp_wage = nrTests*supervisorSalary;
        Supervisor a = new Supervisor();
        a.setId(1);
        a.setName("Ben");
        a.setSalary(supervisorSalary);
        ReturnValue ret = Solution.addSupervisor(a);
        assertEquals(ReturnValue.OK, ret);
        for (int i = 1; i <= nrTests; i++) {
            Test s = new Test();
            s.setId(i);
            s.setSemester(i);
            s.setRoom(233+i);
            s.setDay(1);
            s.setTime(1);
            s.setCreditPoints(3);
            res = Solution.addTest(s);
            assertEquals(ReturnValue.OK, res);
            ret = Solution.supervisorOverseeTest(1, i, i);
            assertEquals(ReturnValue.OK, ret);
        }
        int wage = Solution.getWage(1);
        assertEquals(exp_wage,wage);
        wage = Solution.getWage(2);
        assertEquals(-1,wage);
    }
    @org.junit.Test
    public void testsThisSemesterTest() {
        ReturnValue res;
        ArrayList<Integer> exp_semster1 = new ArrayList<Integer>();
        ArrayList<Integer> exp_semster2 = new ArrayList<Integer>();
        ArrayList<Integer> semester1;
        ArrayList<Integer> semester2;
        int nrTests = 15;
        for (int i = nrTests; i >= 1; i--) {
            Test s = new Test();
            s.setId(i);
            s.setSemester(i%2+1);
            s.setRoom(233+i);
            s.setDay(1);
            s.setTime(1);
            s.setCreditPoints(3);
            res = Solution.addTest(s);
            assertEquals(ReturnValue.OK, res);
            if(i%2+1 == 1 && exp_semster1.size() < 5){
                exp_semster1.add(i);
            }
            else if(i%2+1 == 2 && exp_semster2.size() < 5){
                exp_semster2.add(i);
            }
            semester1 = Solution.testsThisSemester(1);
            semester2 = Solution.testsThisSemester(2);
            assertArrayEquals(exp_semster1.toArray(),semester1.toArray());
            assertArrayEquals(exp_semster2.toArray(),semester2.toArray());
        }
        semester1 = Solution.testsThisSemester(1);
        semester2 = Solution.testsThisSemester(2);
        assertArrayEquals(exp_semster1.toArray(),semester1.toArray());
        assertArrayEquals(exp_semster2.toArray(),semester2.toArray());
    }
    @org.junit.Test
    public void studentCreditPointsTest() {
        ReturnValue res;
        int nrTests = 10;
        int studentInitCP = 100;
        int exp_CP = studentInitCP;
        Student a = new Student();
        a.setId(1);
        a.setName("Ben");
        a.setFaculty("CS");
        a.setCreditPoints(studentInitCP);
        ReturnValue ret = Solution.addStudent(a);
        assertEquals(ReturnValue.OK, ret);
        Student b = new Student();
        b.setId(2);
        b.setName("Bianca");
        b.setFaculty("EE");
        b.setCreditPoints(studentInitCP);
        ret = Solution.addStudent(b);
        assertEquals(ReturnValue.OK, ret);
        for (int i = 1; i <= nrTests; i++) {
            Test s = new Test();
            s.setId(i);
            s.setSemester(i);
            s.setRoom(233+i);
            s.setDay(1);
            s.setTime(1);
            s.setCreditPoints(i);
            res = Solution.addTest(s);
            assertEquals(ReturnValue.OK, res);
            ret = Solution.studentAttendTest(1, i, i);
            assertEquals(ReturnValue.OK, ret);
            exp_CP +=i;
        }
        int studentCP = Solution.studentCreditPoints(1);
        assertEquals(exp_CP,studentCP);
        studentCP = Solution.studentCreditPoints(2);
        assertEquals(studentInitCP,studentCP);
        studentCP = Solution.studentCreditPoints(3);
        assertEquals(0,studentCP);
    }
    @org.junit.Test
    public void getConflictingTestsTest() {
        ReturnValue res;
        ArrayList<Integer> exp = new ArrayList<Integer>();
        for (int i = 1; i <= 3; i++) {
            exp.add(i);
        }
        Test s1 = new Test();
        s1.setId(1);
        s1.setSemester(1);
        s1.setRoom(233);
        s1.setDay(1);
        s1.setTime(1);
        s1.setCreditPoints(2);
        res = Solution.addTest(s1);
        assertEquals(ReturnValue.OK, res);
        Test s2 = new Test();
        s2.setId(2);
        s2.setSemester(1);
        s2.setRoom(233);
        s2.setDay(1);
        s2.setTime(1);
        s2.setCreditPoints(2);
        res = Solution.addTest(s2);
        assertEquals(ReturnValue.OK, res);
        Test s3 = new Test();
        s3.setId(3);
        s3.setSemester(1);
        s3.setRoom(233);
        s3.setDay(1);
        s3.setTime(1);
        s3.setCreditPoints(2);
        res = Solution.addTest(s3);
        assertEquals(ReturnValue.OK, res);
        Test s4 = new Test();
        s4.setId(4);
        s4.setSemester(1);
        s4.setRoom(233);
        s4.setDay(1);
        s4.setTime(2);
        s4.setCreditPoints(2);
        res = Solution.addTest(s4);
        assertEquals(ReturnValue.OK, res);

        ArrayList<Integer> act = Solution.getConflictingTests();
        assertArrayEquals(exp.toArray(),act.toArray());
    }

    @org.junit.Test
    public void graduateStudentsTest() {
        ReturnValue res;
        int nrTests = 10;
        int studentInitCP = 100;
        int exp_CP = studentInitCP;
        Student a = new Student();
        a.setId(1);
        a.setName("Ben");
        a.setFaculty("CS");
        a.setCreditPoints(studentInitCP);
        ReturnValue ret = Solution.addStudent(a);
        assertEquals(ReturnValue.OK, ret);
        Student b = new Student();
        b.setId(2);
        b.setName("Bianca");
        b.setFaculty("EE");
        b.setCreditPoints(200);
        ret = Solution.addStudent(b);
        assertEquals(ReturnValue.OK, ret);
        for (int i = 1; i <= nrTests; i++) {
            Test s = new Test();
            s.setId(i);
            s.setSemester(i);
            s.setRoom(233+i);
            s.setDay(1);
            s.setTime(1);
            s.setCreditPoints(i);
            res = Solution.addTest(s);
            assertEquals(ReturnValue.OK, res);
            ret = Solution.studentAttendTest(1, i, i);
            assertEquals(ReturnValue.OK, ret);
            exp_CP +=i;
        }
        ArrayList<Integer> actual = new ArrayList<Integer>();
        actual = Solution.graduateStudents();
        ArrayList<Integer> exp = new ArrayList<Integer>();
        for (int i = 1; i <= 2; i++) {
            exp.add(i);
        }
        assertArrayEquals(exp.toArray(),actual.toArray());
    }

    @org.junit.Test
    public void getCloseStudentsTest() {
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

        ArrayList<Integer> actual = new ArrayList<Integer>();
        actual = Solution.getCloseStudents(1); //Moshe ha gever
        ArrayList<Integer> exp = new ArrayList<Integer>();
        exp.add(6);
        exp.add(2);
        assertArrayEquals(exp.toArray(),actual.toArray());

    }

}

