# 백기선님 스프링 부트 강의 실습

## 스프링 어플리케이션 실행방법
- 스태틱 메서드?
   1) SpringApplication.run(AppApplication.class, args); 이렇게 쓰면 커스터마이징 불가함.
- 인스턴스화
    1) SpringApplication app = new SpringApplication(AppApplication.class); // 인스턴스로 만들어서
    2) app.run(args);  // 실행!

## 실행배너 교체방법
1) src > main > resources 디렉토리에 banner.txt 파일 생성/작성 후 앱 실행하면 로고가 변경됨.
    * 다른 위치에 놓고 싶으면 spring.properties파일에 spring.banner.location 속성을 classpath: 기준으로 작성하면 됨.
2) 배너를 코드로 작성 가능
    * app.setBanner(new Banner() {
       @Override
       public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
           out.println("=============================");
           out.println("JW SPRINGBOOT");
           out.println("=============================");
       }
   });
3) 빌더를 사용 할수도? 
    * new SpringApplicationBuilder()
                      .sources(AppApplication.class)
                      .run(args);
4) 배너를 끌때 - > setBannerMode(Banner.Mode.OFF);

## 리스너 등록방법
- 리스너 파일 생성(AppSampleListener.java)
    * implements ApplicationListener<???> => ???에 어떤 유형의 이벤트인지 명시해줘야한다.
        ex) public class AppSampleListener implements ApplicationListener<ApplicationStartedEvent> {
1) 어플리케이션 컨텍스트 동작하기 전에 발생하는 이벤트(ex : ApplicationStartingEvent)
    * 빈으로 등록(@Component)할 필요 없다.
    * 메인 메서드에서 등록해줘야 한다.
       +  app.addListeners(new AppSampleListener());
2) 어플리케이션 컨텍스트 동작한 후에 발생하는 이벤트(ex : ApplicationStartedEvent)
    * 빈으로 등록(@Component)해야 한다.

## 애플리케이션 타입 설정
1) app.setWebApplicationType(???);
    * ??? = WebApplicationType.REACTIVE, NONE, SERVLET 3개 있음.
    * 만약 클래스패스에 MVC가 있으면 무조건 SERVLET으로 동작.
    * MVC 없이 웹플럭스? 만 있으면 REACTIVE로 동작.
    * 둘다 있으면? 서블릿으로 동장
    * 둘다 없으면 NONE
    * 둘다 있는데, REACTIVE로 동작시키고 싶다면 위 처럼 설정해주는 것.
    
## 애플리케이션 아규먼트
1) run/debug configuration 창에서 vm 아규먼트(-D...)와 프로그램 아규먼트(--...)가 있다.
    * 프로그램 아규먼트만 애플리케이션 아규먼트로 받아서 쓸 수 있다. (ArgumentUtil.java)
2) 콘솔명령어도 마찬가지
    * java -jar target/..jar -Dfoo --bar 
    * foo는 없고 bar만 있음
3) 애플리케이션 실행 후 뭔가 실행하고 싶을때 (AppRunner.java) - implements ApplicationRunner(추천), implements CommandLineRunner 사용 가능
    * 둘다 jvm 아규먼트는 무시함.
    * ApplicationRunner가 좀더 세련된 api 활용 가능
    * @order(숫자) 어노테이션을 활용해 여러개의 러너중 우선순위 설정 가능

## 외부설정
* 어플리케이션에서 사용하는 여러가지 설정값을 어플리케이션의 밖 또는 안에 정의할수 있는 기능
* 보통 설정파일은 application.propertis -> 이건 스프링부트 규약, 컨벤션임.
* key = value 형태로 저장 (jw.name=jeong-woon)
* 기본적인 가져다 쓰는 방법은 AppProperties.java 파일에 @Value 어노테이션을 이용한 방법과 같음.
* 위의 방식은 우선순위로 따지면 15번째 정도 됨.
* test/resources 폴더 생성후 프로젝트 모듈세팅에서 해당 폴더를 test resources 로 지정해주면 테스트시 그 디렉토리의 설정 읽을수 있는 것 같음
* test에 만약 application.properties로 똑같은 이름의 설정 파일이 있는 경우
    - 패키징 한다고 했을때 main 아래 있는것들 먼저 패키징하고, 그 다음에 test 밑에 있는것 패키징 하는데 이때 같은 이름이라 덮어씌워짐
    - 만약 두 파일이 서로 다르다면 에러가 날수 있다.
    - 근데 맨날 두 파일의 변수를 맞춰줄수는 없는데?
        - 이때는 test/resources 아래 application.properties 파일을 지워버리자.
        - 만약 테스트에서 쓸 프로퍼티가 필요하면 우선순위 2, 3번을 활용해서 각 변수 혹은 파일단위로 따로 관리해주자.
* ide에서 앱 실행시는 main 아래것만 컴파일하고, test 실행시에는 test 아래것만 컴파일 한다. 위의 이슈는 패키징할때 발생하는 것임.
   
    ### 프로퍼티 우선순위 (우선순위에)
    1. 유저 홈 디렉토리에 있는 spring-boot-dev-tools.properties
    2. 테스트에 있는 @TestPropertySource
        * @TestPropertySource(properties="jw.name=kjw") 직접 값 설정 가능
        * @TestPropertySource(properties={"jw.name=kjw", "..."}) 값 여러개 설정 가능
        * @TestPropertySource(location=classpath:/) 너무 변수가 많다면 파일 단위로 관리(test.properties 등의 이름으로 가능)
    3. @SpringBootTest 애노테이션의 properties 애트리뷰트
        * @SpringBootTest(properties="jw.name=kjw")
    4. 커맨드 라인 아규먼트
        * java -jar target/???.jar --jw.name=kjw
    5. SPRING_APPLICATION_JSON (환경 변수 또는 시스템 프로티) 에 들어있는 프로퍼티
    6. ServletConfig 파라미터
    7. ServletContext 파라미터
    8. java:comp/env JNDI 애트리뷰트
    9. System.getProperties() 자바 시스템 프로퍼티
    10. OS 환경 변수
    11. RandomValuePropertySource
    12. JAR 밖에 있는 특정 프로파일용 application properties
    13. JAR 안에 있는 특정 프로파일용 application properties
    14. JAR 밖에 있는 application properties
    15. JAR 안에 있는 application properties
    16. @PropertySource
    17. 기본 프로퍼티 (SpringApplication.setDefaultProperties)
    
    ### application.properties 파일 자체의 위치 인식 우선 순위 (높은게 낮은걸 덮어 씁니다.)
    1. file:./config/       프로젝트 루트에 config 폴더 만들고 그 아래
    2. file:./              프로젝트 루트에
    3. classpath:/config/   리소스 밑에 config 폴더 만들고 그 아래
    4. classpath:/          원래 위치
    
* 프로퍼티(외부설정)이 어~~~엄청 많을때 하나의 빈으로 등록하는 방법이 있음(JwProperties.java)
    1. @ConfigurationProperties("???") -> 이 클래스가 어떤 프로퍼티를 관리하는지 키 값을 주면된다. (프로퍼티에 jw.~~ 하면 jw가 키값임.)
        * 어노테이션 프로세서가 없다고 뜨는데, 자동완성이 메타정보를 기반으로 생기는건데, 메타정보를 생성해주는 의존성 추가
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-configuration-processor</artifactId>
                <optional>true</optional>
            </dependency>
            이렇게 해주면 @ConfigurationProperties 붙어있는 애들은 메타정보 생성되서 application.properties에서 자동완성 사용 가능
  
    2. 빈으로 생성해주기
        * 원래는 메인클래스(AppApplication.java)에 @EnableConfigurationProperties(JwProperties.class)명시해줬는데 자동으로 기본 등록됨 우리가 해야할 일은
           JwProperties에 @Component 추가하는것.
        * 이렇게 해주면 기존에 @Value로 프로퍼티의 값을 가져오던 것을 아래와 같이 바꿀수 있음
           @Autowired
           private JwProperties jwProperties
           ...
           jwProperties.getName()
           
           되도록이면 위와 같이 쓰는게 좋다
    
    3. 프로퍼티 값 검증(JwProperties.class)
        * @Validated 어노테이션 추가 (하이버네이트 벨리데이터, JSR-303구현체임)
        * @Not empty, @Size(min=1, max=30) 등등 사용 가능

## 프로파일
    1. 