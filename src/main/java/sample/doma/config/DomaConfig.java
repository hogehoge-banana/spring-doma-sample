package sample.doma.config;

import org.seasar.doma.SingletonConfig;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.GreedyCacheSqlFileRepository;
import org.seasar.doma.jdbc.NoCacheSqlFileRepository;
import org.seasar.doma.jdbc.SqlFileRepository;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.MysqlDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by chibana on 2017/05/17.
 */
@SingletonConfig
@Component
@Primary
public class DomaConfig  implements Config {

    private static final DomaConfig CONFIG = new DomaConfig();
    private static final String DATASOURCE_NAME = "masterDataSource";

    private Dialect dialect;

    private DataSource dataSource;

    private SqlFileRepository sqlFileRepository;

    private DomaConfig() {
        dialect = new MysqlDialect();
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = new TransactionAwareDataSourceProxy(dataSource);
    }

    @Autowired
    public void setSqlFileRepository(@Value("${doma.sql-file-repository}") String policy) {
        if ("no_cache".equals(policy)) {
            this.sqlFileRepository = new NoCacheSqlFileRepository();
        } else {
            this.sqlFileRepository = new GreedyCacheSqlFileRepository();
        }
    }

    @Override
    public String getDataSourceName() {
        return DATASOURCE_NAME;
    }

    @Override
    public Dialect getDialect() {
        return dialect;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    public static DomaConfig singleton() {
        return CONFIG;
    }
}
