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
* 스프링 프레임워크에서 지원하는 기능, 특정한 프로파일에서만 애플리케이션의 동작을 달리하거나, 빈을 다르게 등록하고 싶을때 씀.
1. com > springboot > app > config 디렉토리 생성
2. BaseConfiguration, TestConfiguration 파일 생성
3. @Profile("prod"), @Profile("test")로 지정.
    - 각 파일은 프로파일이 prod, test 일때만 작동함. 이 아래로 설정된 빈들 또한 각 프로파일이 일치 할때만 등록됨.
4. ConfigRunner 만들고 hello 빈 가져오면 에러남. 왜? 이런 타입의 빈이 없으니까.
5. Application.properties 파일에 spring.profiles.active=prod 설정해줌. 그다음 실행시 에러 안남.
    - spring.profiles.active=prod 이 부분이 프로파일 지정하는 부분
6. 결국 spring.profiles.active 이 설정도 Application.properties 안에 있다는 것은 이것도 프로퍼티임.
    - 프로퍼티의 우선순위의 영향을 다 받음
    - java -jar target/....jar --spring.profiles.active=prod 로 실행하면 프로퍼티 파일 안에서 설정한것 을 덮어쓸 수 있다.
7. 프로파일용 프로퍼티 추가
    - spring.profiles.active에서 정해진 값에 따라서 다른 프로퍼티 파일을 참조할 수 있음.
    - 기본 Application.properties 파일보다 프로파일용 프로퍼티가 우선순위 높음. 덮어씀.
    - application-{profile}.properties
8. spring.profiles.include
    - 이 설정이 읽히면 여기 해당하는 프로파일을 활성화 하라는 뜻임.
    

## 로깅
1. 로깅 퍼사드 vs 로거
    * 로깅 퍼사드는 사실 로그를 찍는 애가 아님(commons-logging, slf4j)
    * jul, log4j2, logback 이런게 로거임.
    * 로깅 퍼사드는 로거 api를 추상화 해둔 인터페이스 임.
    * 로깅 퍼사드를 통해서 로거를 쓰면 장점은 로거를 쉽게 바꿔낄수 있게 해준다는 것.
    * 스프링은 커먼스 로깅 씀. 코어 개발할때 slf4j 없었음.
    * 우리가 만든 앱에서 로그는 누가찍는거냐? logback이 찍는다는게 정답임.
        - 커먼스 로깅을 쓰든, slf4j든 써도 상관없음. 어차피 slf4j 통해서 logback이 찍는다. 
        - logback이 slf4j의 구현체이다.
    * debug로 찍으면 전부다 디버그로 나오는건 아니고, embeded container, 하이버네이트, 스프링 부트까지 일부 핵심 라이브러리로 찍어줌
    * 전부다 debug 하고 싶으면 --trace로 해주면 됨.
    * 로그 컬러 출력 프로퍼티 : spring.output.ansi.enabled=always 하면 컬러출력됨.
    * 로그 파일 출력은 logging.file 또는 logging.path 설정으로 가능함.
    * 로그 레벨 조정: logging.level.패지키 = 로그 레벨
        - logging.level.com.springboot.app = DEBUG
        - 로거를 쓸 파일에서
            private Logger logger = LoggerFactory.getLogger(ConfigRunner.class);
            ...
            logger.debug("============================");
            logger.debug("bean : " + hello);
            logger.debug("============================");
            이런 형태로 쓴다.
        - 일반 패키지 뿐만 아니라 org.springframework 이런식으로 스프링이 찍는 로거도 조절 가능.
2. 커스텀 로그 파일 추가
    - 클래스패스에 아래 파일 중 하나 추가
    * Logback: logback-spring.xml(추천)/logback.xml은 너무빨리 로딩되서 커스터마이징이 불가능
    * Log4J2: log4j2-spring.xml
    * JUL (비추): logging.properties
3. log4j2로 로거 변경
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <!-- 여기서 logback 제거해주고 -->
        <exclusions>
            <exclusion>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-logging</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    
    <!-- 여기서 log4j2 넣어준다. -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-log4j2</artifactId>
    </dependency>

## 테스트
1. 가장 먼저 의존성 확인
    * <!-- 테스트를 위한 의존성, 스코프 test! -->
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-test</artifactId>
          <scope>test</scope>
      </dependency>
     * 위의 의존성을 추가하면 뭐가 들어올까?
        - junit, jsonpath(assertion 할수 있는거?), 모키토(목업?), 스프링 테스트, 셀레니움 들어옴.
        - 테스트 파일 생성 => 컨트롤러 에서 ctrl + shift + t
        - 테스트 파일에 
            @SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
                1) webEnvironment = MOCK(기본): mock servlet environment. 내장 톰캣 구동 안 함.
                    * 서블릿 컨테이너를 테스트 용으로 띄우지 않고, 목업을 해서 실험은 할 수 있다.
                    * 목업된 서블릿에 인터랙션을 위해서는 MockMvc라는 클라이언트를 꼭! 사용해야한다.(SampleTestControllerTest.java) 
                    @AutoConfigureMockMvc, @Autowired MockMvc mockMvc; > 이 방법이 가장 쉬움. 
                2) webEnvironment = RANDOM_PORT(추천), DEFINED_PORT: 내장 톰캣 사용 함.
                    * 레스트 템플릿, 테스트용 레스트 템플릿, 테스트용 웹 클라이언트를 써야함.
                    * 레스트 템플릿 -> @Autowired private TestRestTemplate testRestTemplate 주입받음.
                    * 아무 어노테이션 추가 안함.
                    * WebTestClient -> 스프링 웹플럭스 의존성 추가해야함.
                      비동기 요청 가능.
                      
                3) webEnvironment = NONE: 서블릿 환경 제공 안 함.
            @RunWith(SpringRunner.class) 추가
            
            - 컨트롤러만 테스트 하고 싶다? 서비스를 아예 목킹시켜서 임의로 조작 가능
            @MockBean
            private SampleTestService mockSampleService 
            when(mockSampleService.getName()).thenReturn("jw");
            
            @MockBean은 ApplicationContext에 들어있는 빈을 Mock으로 만든 객체로 교체 함.
            모든 @Test 마다 자동으로 리셋.
            
            슬라이스 테스트
            사실, @SpringBootTest는 통합 테스트임. @SpringBootApplication 찾아서 모든 객체 다등록함. 이게 싫다?
            레이어 별로 잘라서 테스트하고 싶을 때 @SpringBootTest을 아래 어노테이션으로 교체
            @JsonTest
            
            @Autowired JacksonTester<도메인객체> 받아서 테스트 
            
            @WebMvcTest(테스트할 컨트롤러 클래스) -> 웹 관련애들만 빈으로 등록됨. 서비스 이런거 등록안됨. 목킹해줘야함. + MockMvc
            @WebFluxTest
            @DataJpaTest
## 스프링MVC
1. src>test>java>com>springboot>app>user 패키지 생성
2. UserControllerTest 생성
3. src>main>java>com>springboot>app>user 패키지 아래 UserController 파일 생성
4. 코드를 완성하고 실행하면 테스트 성공!
이렇게 아무 설정 없이 스프링 웹 mvc 개발을 시작할수 있는데,
이는 스프링 부트가 제공해주는 기본설정떄문이다. 
spring-boot-autoconfigure 라는 모듈에 spring-factories에 보면 webMvcAutoConfiguration 클래스가 존재.
이 자동설정 파일이 적용되었기 때문에 아무런 설정 없이 mvc 사용 가능.
5. 스프링 웹 MVC 기능 확장
    config 패키지 생성(정리차원?)
    WebConfig 클래스 생성 파일에서 확인

## HttpMessageConverters
* 스프링 프레임워크에서 제공하는 인터페이스, mvc의 일부분임.
* HTTP 요청 본문으로 들어오는것을 객체로 변경, 객체를 HTTP 응답 본문으로 변경할때 사용.
* @RequestBody(요청본문), @ResponseBody(응답본문)와 사용함.
* 요청, 응답에 따라서 사용되는 메세지 컨버터가 달리진다.
    - json 요청에 json 본문이 들어오면 제이슨메세지컨버터 사용해서 User 객체로 바꿈.
    - 요청에는 contentType이라는 헤더가 있음.
    - 객체를 리턴할때도 객체 자체를 리스폰스에 담을수 없다. Http는 다 문자임.
    - 기본적으로 컴포지션 타입의 경우(자바빈처럼 이런저런 프로퍼티, 게터세터 가지는걸 말하는듯) 제이슨 컨버터가 사용됨.
    - 그렇지 않고 문자열, int의 경우(toString 가능한 경우?) 스트링메세지컨터버 사용됨.
    - 만약 컨트롤러가 @RestController 일 경우 @ResponseBody 어노테이션 생략 가능.
    - 그냥 컨트롤러를 쓸 경우 @ResponseBody를 꼭 사용해야 메세지 컨버터가 적용된다.
    - 그냥 컨트롤러를 쓰면서 @ResponseBody를 쓰지 않으면. 뷰네임리졸버, 빈네임뷰리졸버를 사용해서 이 이름에 해당하는 뷰를 찾으려고 시도함.
* 메세지 컨버터를 사용할려면 컴포지션 객체를 활용해봐야함.(User),

## ContentNegotiatingViewResolver
* ViewResolver중의 하나
* 들어오는 요청의 accept 헤더에 따라, 다시 말해 브라우저 또는 클라이언트가 어떤 타입의 응답을 원한다고 서버에 알려주는 것에 따라 응답이 달라짐.
* 어떤 요청이 들어오면 그 요청에 응답을 만들수 있는 모든 뷰를 찾고, 최종적으로 억셉트 헤더랑 뷰의 타입을 비교해서 선택을 함. 클라이언트가 이 뷰를 원했을꺼야!
* 가장 좋은 정보는 accept 헤더임. 그치만 경우에 따라서 이 헤더를 제공하지 않는 요청들도 많다. 매개변수 format 사용함.(/path?format=xml) 이런것도 사용함.
* 요청은 json으로 보내고 xml로 받는 테스트(createUser_XML) 확인 => 컨트롤러(핸들러)코드를 바꾸지 않았는데, 응답 본문이 xml로 변경됨.
* 만약 org.springframework.web.HttpMediaTypeNotAcceptableException 에러가 발생한다면, 처리할 컨버터가 없다는 의미 
    - HttpMessageConvertersAutoConfiguration 파일에서 JacksonHttpMessageConvertersConfiguration 파일을 보면 @ConditionalOnClass({XmlMapper.class}) xml 매퍼가 있을때만 빈으로 등록해주는 설정 있음.
    - 따라서 아래 의존성 추가(xml 매퍼 추가)
    - <dependency>
          <groupId>com.fasterxml.jackson.dataformat</groupId>
          <artifactId>jackson-dataformat-xml</artifactId>
          <version>2.9.6</version>
      </dependency>

## 정적 리소스
* 동적으로 생성하지 않은, 이미 생성된 리소스를 그대로 반환
* 정적 리소스 맵핑 “/**”=> 아래 기본 리소스위치를 가리킴
* 기본 리소스 위치
    1) classpath:/static
    2) classpath:/public
    3) classpath:/resources/
    4) classpath:/META-INF/resources
    - 위 네개의 위치는 기본으로 맵핑 되어 있기때문에 별도의 패스를 적어주지 않는다. /static, /public 와 같은 경로 적지 않음.
        + 예) “/hello.html” => /static/hello.html
    - 요청을 처리하는 녀석은 ResourceHttpRequestHandler가 한다. 재미있는것은 헤더정보에 last-modified라는 정보를 보고 304를 줄 때가 있음.
    리소스 자체를 다시 보내주지는 않을때도 있음.
    - 만약 파일을 수정하면 요청 헤더에 if-modified-since라는 헤더가 있고, 응답정보에 last-modified 가 있는데, 응답에 마지막 수정시간이 요청헤더에 마지막 수정시간보다 최근이면 200 즉 리소스를 다시 보내고 아니면 304 캐쉬정책을 씀.
* 정적리소스 맵핑 패턴 변경
    - application.properties에 spring.mvc.static-path-pattern=/static/** 형태로 바꿔주면 된다.
    - 이렇게 설정하면 static로 요청을 해야함. (/static/hello.html)
* 정적리소스 위치 변경
    - application.properties에 spring.mvc.static-location 프로퍼티 사용.
    - 이 프로퍼티는 위의 기본 경로 4가지를 전부 못쓰게 되니 권장하지 않음.
    - 추천하는 방법은 WebConfig 파일안에 아래 형태로 재정의 하는것. 위 4가지 기본 경로 사용가능 거기에 추가하는 것. 
        + @Override
          public void addResourceHandlers(ResourceHandlerRegistry registry) {
              registry.addResourceHandler("/m/**") //디렉토리 추가
                      .addResourceLocations("classpath:/m/")  // 반드시! '/' 로 끝나야함! 이렇게 안하면 맵핑이 잘 안됨!
                      .setCachePeriod(20);    // 여기서 추가하는 핸들러는 캐시 정책이 안들어가기때문에 직접 설정해줘야함.
          }
          
## 웹 jar
    - 클라이언트에서 사용하는 자바스크립트 라이브러리들(제이쿼리, 부트스트랩, 리액트, 뷰, 앵귤러)을 jar 파일로 추가 할 수 있다.
    - 템플릿을 사용해서 동적 리소스 생성 또는 정적 리소스에서 css, js 참조 가능
    - 메이븐 저장소에 있기 때문에 검색해서 적용 가능
    - pom.xml 파일에 의존성 추가 후 hello.html 에 스크립트 추가하면 사용 가능.
    - 만약 버전이 변경되면 각 소스파일마다 변경?? > 애초에 버전생략 할 수 있는 방법이 있음
        * 먼저 의존성 추가하면 자동 설정해줌
        * 자동 설정해주는것은 스프링 프레임워크 리소스 체이닝과 관련있음.
        * 리소스 핸들러와 리소스 트랜스포머를 체이닝하는 기능이 있음. 버전을 생략할수 있게 해주는 기술의 근간

## 인덱스 페이지와 파비콘
* 인덱스 페이지는 이 애플리케이션 루트로 요청했을때 보여주는 페이지
* 정적 페이지를 보여주는 방법, 동적 페이지를 보여주는 방법 두가지가 있음.
* 정적 페이지를 보여주는 방법은 정적리소스 기본 4가지 위치중 아무곳이나. index.html을 만들면 된다.
* 동적 페이지를 보여주는 방법은 정적리소스 기본 4가지 위치중 아무곳이나. index.템플릿
* 둘다 없으면 에러페이지 보여줌.
 
* 파비콘도 리소스 디렉토리 아래 아무데나 넣으면 됨.
* 근데 안바뀜?? 아마 캐쉬되서 다시 리소스 안주는거 같음. 직접 파비콘 요청하고 새로고침 하면 나옴.