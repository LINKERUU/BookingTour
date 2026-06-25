plugins {
    id("org.springframework.boot")
    java
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation ("org.springframework.boot:spring-boot-starter-validation")

    compileOnly ("org.projectlombok:lombok:1.18.40")
    annotationProcessor ("org.projectlombok:lombok:1.18.40")
}