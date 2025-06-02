package ge.tbc.testautomation.utils;

import ge.tbc.testautomation.mappers.BookingCaseMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class DataBaseConfig {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        initializeSqlSessionFactory();
    }

    private static void initializeSqlSessionFactory() {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error initializing MyBatis SqlSessionFactory", e);
        }
    }

    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession(true);
    }

    public static BookingCaseMapper getBookingCaseMapper() {
        SqlSession session = sqlSessionFactory.openSession(true);
        return session.getMapper(BookingCaseMapper.class);
    }

    public static <T> T executeWithMapper(MapperOperation<BookingCaseMapper, T> operation) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            BookingCaseMapper mapper = session.getMapper(BookingCaseMapper.class);
            return operation.execute(mapper);
        }
    }

    @FunctionalInterface
    public interface MapperOperation<M, R> {
        R execute(M mapper);
    }
}