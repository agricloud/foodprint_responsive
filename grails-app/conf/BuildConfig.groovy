
grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)


grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
grails.project.war.file = "target/${appName}.war"
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.war.resources = { stagingDir ->
    if(new File("${stagingDir}/development").exists())
        delete(includeEmptyDirs: true) { fileset dir: "${stagingDir}/development" }

    delete(includeEmptyDirs: true) { fileset dir: "${stagingDir}/art sample" }
    delete(includeEmptyDirs: true) { fileset dir: "${stagingDir}/xmlSample" }
}


grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 512, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 512, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 512, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 512, minMemory: 64, debug: false, maxPerm: 256]
]
grails.project.dependency.resolver = "maven" // or ivy



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
        mavenLocal()
        grailsCentral()
        mavenCentral()
        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
        // runtime 'mysql:mysql-connector-java:5.1.24'
        // compile 'org.apache.ant:ant:1.7.1'    //you can also use runtime
        // compile 'org.apache.ant:ant-launcher:1.7.1'

        runtime 'net.java.dev.jets3t:jets3t:0.9.4' //runtime 'net.java.dev.jets3t:jets3t:0.9.0'
        compile 'org.imgscalr:imgscalr-lib:4.2'
    }

    plugins {

        // plugins for the build system only
        build ':tomcat:7.0.70' //build ':tomcat:7.0.55.3'

        // plugins for the compile step
        compile "org.grails.plugins:scaffolding:2.1.2" //compile ":scaffolding:2.1.0"
        compile "org.grails.plugins:cache:1.1.8" //compile ':cache:1.1.1'
        compile "org.grails.plugins:asset-pipeline:2.8.0" //compile "org.grails.plugins:asset-pipeline:2.6.10"

        // plugins needed at runtime but not for compilation
        compile "org.grails.plugins:mysql-connectorj:5.1.22.1"
        runtime ":hibernate4:4.3.8.1" //runtime ':hibernate4:4.3.5.2'
        compile "org.grails.plugins:database-migration:1.4.1" //runtime ':database-migration:1.4.0'


        compile "org.grails.plugins:jquery:1.11.1"

        // add plugin
        //compile ':spring-security-core:latest.release'
        compile "org.grails.plugins:spring-security-core:2.0.0" //compile ':spring-security-core:2.0-RC5'
        compile "org.grails.plugins:cors:1.1.8" //runtime ":cors:1.1.0"
        compile "org.grails.plugins:bcrypt:1.0"

        // Adds REST client capabilities to your Grails application.
        compile "org.grails.plugins:rest:0.8"

        // Grails REST Client Builder Plugin
        compile "org.grails.plugins:rest-client-builder:2.1.1" //compile ':rest-client-builder:1.0.3'

        // compile ":twitter-bootstrap:3.0.0"

        compile "org.grails.plugins:jasper:1.11.0" //compile ":jasper:1.8.0"


        // Grails Ant Plugin
        // 執行 Ant 任務
        compile "org.grails.plugins:grails-ant:0.1.3"

        //allows your Grails application to schedule jobs
        compile "org.grails.plugins:quartz:1.0.2" //compile ":quartz:1.0-RC13"

        test ":code-coverage:1.2.7" //ps.2.0.3-3 not work
        compile "org.grails.plugins:codenarc:0.25.2" //compile ":codenarc:0.24.1"
    }
}
