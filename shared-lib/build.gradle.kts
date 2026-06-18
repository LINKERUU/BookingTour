plugins {
    id("org.springframework.boot")
    `java-library`
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.amqp:spring-rabbit")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    api("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    compileOnly ("org.projectlombok:lombok:1.18.40")
    annotationProcessor ("org.projectlombok:lombok:1.18.40")

    api("org.springframework.boot:spring-boot-starter-security")
}