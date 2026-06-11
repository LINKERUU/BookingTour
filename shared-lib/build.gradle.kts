plugins {
    id("org.springframework.boot")
    java
}

dependencies {
    implementation("org.springframework.amqp:spring-rabbit")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
}