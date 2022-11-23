import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.menegment.dao.connection.JDBC;

import java.sql.Statement;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateTables {
    @Test
    @Order(1)
    public void createTable() {
        Statement stmt = null;
        try {
            JDBC.connect();
            stmt = JDBC.connection.createStatement();


            String personsTable = "CREATE TABLE Persons " +
                    "(person_id INTEGER PRIMARY KEY NOT NULL, " +
                    " name_person CHAR(20)," +
                    " lastname  CHAR(20)," +
                    " surname  CHAR(20))";
            stmt.executeUpdate(personsTable);


            String usersTable = "CREATE TABLE Users " +
                    "(user_id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                    " login CHAR(20), " +
                    " password_ CHAR(20)," +
                    " role_user INTEGER)";
            stmt.executeUpdate(usersTable);

            String workersTable = "CREATE TABLE Workers " +
                    "(worker_id  INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                    " name_worker  CHAR(20)," +
                    " sur_name  CHAR(20)," +
                    " passport  CHAR(20) UNIQUE KEY ," +
                    " id_user_worker INTEGER," +
                    " last_name  CHAR(20))";
            stmt.executeUpdate(workersTable);


            String resumesTable = "CREATE TABLE Resumes" +
                    "(resume_id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT , " +
                    " specialization  CHAR(20)," +
                    " experience  CHAR(20)," +
                    " education  CHAR(20))";
            stmt.executeUpdate(resumesTable);

            String ordererTable = "CREATE TABLE Departments" +
                    "(department_id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                    " name_department CHAR(20) UNIQUE KEY ," +
                    " id_user INTEGER," +
                    " address  CHAR(20))";
            stmt.executeUpdate(ordererTable);


            String vacanciesTable = "CREATE TABLE Vacancies" +
                    "(vacancies_id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                    " name_vacancies  CHAR(20)," +
                    " id_department INTEGER," +
                    " info  CHAR(100))";
            stmt.executeUpdate(vacanciesTable);

            String claimsTable = "CREATE TABLE Claims" +
                    "(claim_id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                    " name_claim  CHAR(20),"+
                    " id_worker INTEGER,"+
                    " status_claim INTEGER,"+
                    " info  CHAR(100))";
            stmt.executeUpdate(claimsTable);

            String fk_person_user = "ALTER TABLE persons ADD CONSTRAINT fk_person_user  foreign key (person_id) references users(user_id) On DELETE CASCADE on UPDATE CASCADE";
            String fk_worker_user = "ALTER TABLE workers ADD CONSTRAINT  fk_worker_user  foreign key (id_user_worker) references users(user_id) On DELETE CASCADE on UPDATE CASCADE";
            String fk_departments_user = "ALTER TABLE departments ADD CONSTRAINT fk_departments_user foreign key (id_user) references users(user_id) On DELETE CASCADE on UPDATE CASCADE";
            String fk_claim_worker = "ALTER TABLE claims ADD CONSTRAINT fk_claim_worker  foreign key (id_worker) references workers(worker_id) On DELETE CASCADE on UPDATE CASCADE";
            stmt.executeUpdate(fk_person_user);
            stmt.executeUpdate(fk_worker_user);
            stmt.executeUpdate(fk_departments_user);
            stmt.executeUpdate(fk_claim_worker);
            String fk_resume = "ALTER TABLE resumes ADD CONSTRAINT fk_resume foreign key (resume_id) references workers(worker_id)  On DELETE CASCADE on UPDATE CASCADE ";
            stmt.executeUpdate(fk_resume);
            String fk_vacancies_departments = "ALTER TABLE vacancies ADD CONSTRAINT fk_vacancies_departments foreign key (id_department) references departments(department_id) On DELETE CASCADE on UPDATE CASCADE ";
            stmt.executeUpdate(fk_vacancies_departments);

            JDBC.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
