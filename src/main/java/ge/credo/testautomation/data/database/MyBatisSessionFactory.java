package ge.credo.testautomation.data.database;

import ge.credo.testautomation.data.ApiConstants;
import ge.credo.testautomation.data.mappers.TestResultMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class MyBatisSessionFactory {

    private static final SqlSessionFactory SESSION_FACTORY = buildFactory();

    private static SqlSessionFactory buildFactory() {
        PooledDataSource dataSource = new PooledDataSource(
                "org.sqlite.JDBC",
                ApiConstants.Database.DB_URL,
                null,
                null
        );

        Environment environment = new Environment(
                "production",
                new JdbcTransactionFactory(),
                dataSource
        );

        Configuration configuration = new Configuration(environment);
        configuration.addMapper(TestResultMapper.class);

        return new SqlSessionFactoryBuilder().build(configuration);
    }

    public static SqlSession openSession() {
        return SESSION_FACTORY.openSession(true);
    }
}

