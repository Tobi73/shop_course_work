package ShopAnalytics;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories("ShopAnalytics.repository")
public class App {
    public final static String ROOT_PATH = "shop-management";


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public Docket newsApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(ROOT_PATH)
                .apiInfo(apiInfo())
                .select()
                .paths(regex("/"+this.ROOT_PATH+"/.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("School Diary manager REST API")
                .description("Manager for working with online school diary")
                .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
                .contact("Zaytsev Andrey")
                .version("1.0")
                .build();
    }
}
