package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ClientDemo {
    public static void main(String[] args) {
        // Create three department objects
        Department dept1 = new Department("Computer Science", "New York", "Dr. John");
        Department dept2 = new Department("Electrical Engineering", "Los Angeles", "Dr. Smith");
        Department dept3 = new Department("Mechanical Engineering", "Chicago", "Dr. Brown");

        // Insert the departments into the database
        insertDepartment(dept1);
        insertDepartment(dept2);
        insertDepartment(dept3);

        System.out.println("Three departments inserted successfully.");
    }

    // Insert Department
    public static void insertDepartment(Department department) {
        SessionFactory factory = new Configuration()
                                    .configure("hibernate.cfg.xml")
                                    .addAnnotatedClass(Department.class)
                                    .buildSessionFactory();
        Session session = factory.openSession(); // Use openSession() instead of getCurrentSession()

        try {
            session.beginTransaction();
            session.save(department);
            session.getTransaction().commit();
            System.out.println("Department inserted: " + department);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }


    // Delete Department by ID using HQL with Positional Parameters
    public static void deleteDepartmentById(int deptId) {
        SessionFactory factory = new Configuration()
                                    .configure("hibernate.cfg.xml")
                                    .addAnnotatedClass(Department.class)
                                    .buildSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.beginTransaction();

            // HQL delete with positional parameter
            String hql = "DELETE FROM Department WHERE deptId = ?1";
            Query query = session.createQuery(hql);
            query.setParameter(1, deptId);
            int result = query.executeUpdate();

            session.getTransaction().commit();
            System.out.println("Number of departments deleted: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }
}
