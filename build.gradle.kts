plugins {
    java
    jacoco
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
}

jacoco {
    toolVersion = "0.8.5"
}

group = "com.sparta"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            // 'element'가 없으면 프로젝트의 전체 파일을 합친 값을 기준으로 한다.
            limit {
                // 'counter'를 지정하지 않으면 default는 'INSTRUCTION'
                // 'value'를 지정하지 않으면 default는 'COVEREDRATIO'
                minimum = "0.30".toBigDecimal()
            }
        }

        rule {
            // 룰을 간단히 켜고 끌 수 있다.
            enabled = true

            // 룰을 체크할 단위는 클래스 단위
            element = "CLASS"

            // 브랜치 커버리지를 최소한 90% 만족시켜야 한다.
            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = "0.90".toBigDecimal()
            }

            // 라인 커버리지를 최소한 80% 만족시켜야 한다.
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.80".toBigDecimal()
            }

            // 빈 줄을 제외한 코드의 라인수를 최대 200라인으로 제한한다.
            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "200".toBigDecimal()
            }
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    //jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    //JWT
    compileOnly("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")


    //security
    implementation("org.springframework.boot:spring-boot-starter-security")

    //mail
    implementation("org.springframework.boot:spring-boot-starter-mail")

    //context
    implementation("org.springframework:spring-context-support")

    //thymeleaf
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    //s3
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.623")

    //validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    //lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    //web
    implementation("org.springframework.boot:spring-boot-starter-web")

    //mysql
    runtimeOnly("com.mysql:mysql-connector-j")

    //h2
    runtimeOnly("com.h2database:h2")

    //jasypt
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

    //swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    //redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    //test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testCompileOnly("io.jsonwebtoken:jjwt-api:0.11.5")
}

tasks.withType<Test> {
    useJUnitPlatform()
}