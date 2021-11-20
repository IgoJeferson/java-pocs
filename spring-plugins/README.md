# Spring Plugin

Spring Plugin’s fundamental idea is the strategy pattern which is quite common and this plugin approach in combination with Spring Boot solves it quickly and easily.

Here you have the ability to dynamically select beans based on an advertised capability. 
What its really convenient because now you can conditionally involve certain objects based on certain use cases

```java
@SpringBootApplication
@EnablePluginRegistries(WriterPlugin.class)
public class SpringPluginsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPluginsApplication.class, args);
	}

	// Strategy Pattern
	@Bean
	ApplicationRunner runner (PluginRegistry<WriterPlugin, String> plugins){
		return args -> {

			for (var format : "csv,txt".split(","))
				plugins.getPluginFor(format).get().write("Hello, Spring plugin!");
		};
	}

}

@Component
class CsvWriter implements WriterPlugin{

	@Override
	public void write(String message) {
		System.out.println("writing CSV: " + message);
	}

	@Override
	public boolean supports(String s) {
		return s.equalsIgnoreCase("csv");
	}
}

@Component
class TxtWriter implements WriterPlugin{

	@Override
	public void write(String message) {
		System.out.println("writing TXT: " + message);
	}

	@Override
	public boolean supports(String s) {
		return s.equalsIgnoreCase("txt");
	}
}

interface WriterPlugin extends Plugin<String> {
void write(String message);
}
```

```
.   ____          _            __ _ _
/\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
\\/  ___)| |_)| | | | | || (_| |  ) ) ) )
'  |____| .__|_| |_|_| |_\__, | / / / /
=========|_|==============|___/=/_/_/_/
:: Spring Boot ::                (v2.6.0)

2021-11-20 19:14:25.080  INFO 831 --- [           main] c.g.s.SpringPluginsApplication           : Starting SpringPluginsApplication using Java 11.0.11 on DESKTOP-QON7B6G with PID 831 (/home/igocoelho/Projects/java-pocs/spring-plugins/target/classes started by igocoelho in /home/igocoelho/Projects/java-pocs/spring-plugins)
2021-11-20 19:14:25.082  INFO 831 --- [           main] c.g.s.SpringPluginsApplication           : No active profile set, falling back to default profiles: default
2021-11-20 19:14:25.475  INFO 831 --- [           main] c.g.s.SpringPluginsApplication           : Started SpringPluginsApplication in 0.706 seconds (JVM running for 1.048)
writing CSV: Hello, Spring plugin!
writing TXT: Hello, Spring plugin!

Process finished with exit code 0
```
