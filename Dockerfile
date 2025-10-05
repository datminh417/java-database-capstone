# GIAI ĐOẠN 1: BUILD STAGE (Biên dịch ứng dụng)
# TIÊU CHÍ 1: Sử dụng Multi-stage build
FROM eclipse-temurin:17-jdk-focal AS builder

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép các file cấu hình build (Maven/Gradle)
# Thay đổi tên file build nếu cần (ví dụ: build.gradle)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Build ứng dụng
RUN ./mvnw clean install -DskipTests

# GIAI ĐOẠN 2: RUNTIME STAGE (Chạy ứng dụng)
# Sử dụng base image JRE nhẹ hơn để giảm kích thước image cuối cùng
FROM eclipse-temurin:17-jre-focal AS final

# Thiết lập tham số môi trường
ARG JAR_FILE=/app/target/*.jar

# Sao chép file JAR đã build từ giai đoạn 'builder'
COPY --from=builder ${JAR_FILE} app.jar

# TIÊU CHÍ 2: Định nghĩa Exposed Port
EXPOSE 8080 

# TIÊU CHÍ 2: Định nghĩa Entrypoint (Lệnh chạy ứng dụng)
ENTRYPOINT ["java", "-jar", "/app.jar"]
