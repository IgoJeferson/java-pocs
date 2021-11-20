package com.github.springplugins;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.plugin.core.Plugin;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;
import org.springframework.stereotype.Component;

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
