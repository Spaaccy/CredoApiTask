package ge.credo.testautomation.data.database;

import ge.credo.testautomation.data.ApiConstants;
import ge.credo.testautomation.data.factory.MyBatisSessionFactory;
import ge.credo.testautomation.data.mappers.TestResultMapper;
import ge.credo.testautomation.data.models.TestResult;
import org.apache.ibatis.session.SqlSession;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseManager {

    static {
        try (SqlSession session = MyBatisSessionFactory.openSession()) {
            session.getMapper(TestResultMapper.class).createTable();
        }
    }

    public static void saveResult(String testName, boolean passed) {
        String status = passed ? ApiConstants.Database.STATUS_PASSED : ApiConstants.Database.STATUS_FAILED;
        String now = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(ApiConstants.Database.DB_DATETIME_FMT));

        TestResult testResult = TestResult.builder()
                .testName(testName)
                .status(status)
                .executionTime(now)
                .build();

        try (SqlSession session = MyBatisSessionFactory.openSession()) {
            TestResultMapper mapper = session.getMapper(TestResultMapper.class);

            TestResult existing = mapper.findByTestName(testName);
            if (existing != null) {
                mapper.update(testResult);
            } else {
                mapper.insert(testResult);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
