grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

//grails.tomcat.jvmArgs= ["-Xms512m",  "-Xmx768m", "-XX:PermSize=512m", "-XX:MaxPermSize=1024m"]

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:512, debug:true, maxPerm:512]
//]

grails.project.dependency.resolution = {
	// inherit Grails' default dependencies
	inherits("global") {
		// specify dependency exclusions here; for example, uncomment this to disable ehcache:
		// excludes 'ehcache'
	}
	log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
	checksums true // Whether to verify checksums on resolve
	legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

	repositories {
		inherits true // Whether to inherit repository definitions from plugins

		grailsPlugins()
		grailsHome()
		grailsCentral()

		mavenLocal()
		mavenCentral()

		// uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
		//mavenRepo "http://snapshots.repository.codehaus.org"
		//mavenRepo "http://repository.codehaus.org"
		//mavenRepo "http://download.java.net/maven/2/"
		//mavenRepo "http://repository.jboss.com/maven2/"
	}

	dependencies {
		// specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.

		// runtime 'mysql:mysql-connector-java:5.1.22'
		runtime 'mysql:mysql-connector-java:5.1.20'
	}

	plugins {
		runtime ":hibernate:$grailsVersion"
//		runtime ":jquery:1.8.3"
//		runtime ":resources:1.2.1"

		compile ":yui-war-minify:1.5"

		build ":tomcat:$grailsVersion"

		runtime ":database-migration:1.3.2"
		compile ":quartz2:2.1.6.2"


		compile ':cache:1.1.1'
	}
}


