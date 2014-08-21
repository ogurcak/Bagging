dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
	readOnly = false
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:mysql://127.0.0.1:3306/bagging?useUnicode=yes&characterEncoding=UTF-8"
            username = "grails"
            password = "grails"
            properties {
                validationQuery = "select 1"
                testWhileIdle = true
                timeBetweenEvictionRunsMillis = 2 * 60 * 60 * 1000
            }
        }
    }
	
	
}
