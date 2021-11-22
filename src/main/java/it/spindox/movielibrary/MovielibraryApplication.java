package it.spindox.movielibrary;

import com.google.common.collect.Sets;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.UnknownHostException;

@EnableSwagger2
@SpringBootApplication
public class MovielibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovielibraryApplication.class, args);

	}

	@Bean
	public Docket swaggerConfiguration(){
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("it.spindox.movielibrary"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(this.apiInfo())
				.useDefaultResponseMessages(false)
				.protocols(Sets.newHashSet("HTTP"));
	}

	private ApiInfo apiInfo() {
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
		apiInfoBuilder.title("MOVIE LIBRARY");
		apiInfoBuilder.description("REST API for a Movie Library");
		apiInfoBuilder.contact(new Contact("Antonio Liuzzi", "","antonio.liuzzi@spindox.it"));
		apiInfoBuilder.version("1.0.0");
		apiInfoBuilder.license("GNU GENERAL PUBLIC LICENSE, Version 3");
		apiInfoBuilder.licenseUrl("https://www.gnu.org/licenses/gpl-3.0.en.html");
		return apiInfoBuilder.build();
	}

}


