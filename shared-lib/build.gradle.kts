plugins {
    id("org.springframework.boot")
    java
}

dependencies {
    implementation("org.springframework.amqp:spring-rabbit")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")

    compileOnly ("org.projectlombok:lombok:1.18.40")
    annotationProcessor ("org.projectlombok:lombok:1.18.40")
    implementation("org.springframework.boot:spring-boot-starter-web")
}