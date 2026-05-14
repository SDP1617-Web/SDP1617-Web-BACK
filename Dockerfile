# Java 17 실행 환경 이미지 사용
FROM eclipse-temurin:17-jre

#컨테이너 내부 작업 디렉토리 설정
WORKDIR /app

#빌드된 jar 파일을 컨테이너 내부로 복사
COPY build/libs/*.jar app.jar

EXPOSE 8080

#컨테이너 실행 시 Srping Boot 애플리케이션 실행
ENTRYPOINT ["java","-jar","app.jar"]