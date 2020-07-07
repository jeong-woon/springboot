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
    * 보통 설정파일은 application.propertis -> 이건 규약임.
    * key = value 형태
    * 가져다 쓰는 방법은