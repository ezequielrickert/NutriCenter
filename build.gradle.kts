plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.hsqldb/hsqldb
    implementation("org.hsqldb:hsqldb:2.7.2")
    // https://mvnrepository.com/artifact/com.sparkjava/spark-core
    implementation("com.sparkjava:spark-kotlin:1.0.0-alpha")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // JPA
    implementation("javax.persistence:javax.persistence-api:2.2")
    // Hibernate Core
    implementation("org.hibernate:hibernate-core:5.4.27.Final")
    // Hibernate EntityManager
    implementation("org.hibernate:hibernate-entitymanager:5.4.27.Final")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.google.code.gson:gson:2.9.0")

    // Mercado Pago
    implementation("com.mercadopago:sdk-java:2.1.24")

    implementation("com.squareup.okhttp3:okhttp:4.9.3")
}

group = "org.example"
version = "1.0-SNAPSHOT"

tasks.test {
    useJUnitPlatform()
}