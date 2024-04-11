package aphler.flydrop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan(basePackages = {"aphler.flydrop.mapper"})
@SpringBootApplication
@EnableScheduling
public class FlyDropApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlyDropApplication.class, args);
	}

}
