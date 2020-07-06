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