package ge.credo.testautomation.data.mappers;

import ge.credo.testautomation.data.models.TestResult;
import org.apache.ibatis.annotations.*;


public interface TestResultMapper {

    @Update("""
            CREATE TABLE IF NOT EXISTS test_results (
                id             INTEGER PRIMARY KEY AUTOINCREMENT,
                test_name      TEXT    NOT NULL UNIQUE,
                status         TEXT    NOT NULL,
                execution_time DATETIME NOT NULL
            )
            """)
    void createTable();

    @Insert("""
            INSERT INTO test_results (test_name, status, execution_time)
            VALUES (#{testName}, #{status}, #{executionTime})
            """)
    void insert(TestResult testResult);

    @Update("""
            UPDATE test_results
            SET status         = #{status},
                execution_time = #{executionTime}
            WHERE test_name = #{testName}
            """)
    int update(TestResult testResult);

    @Select("""
            SELECT id, test_name, status, execution_time
            FROM test_results
            WHERE test_name = #{testName}
            """)
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "testName", column = "test_name"),
            @Result(property = "status", column = "status"),
            @Result(property = "executionTime", column = "execution_time")
    })
    TestResult findByTestName(@Param("testName") String testName);
}

