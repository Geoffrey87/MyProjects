package com.LegisTrack.LegisTrack;

import org.springframework.boot.SpringApplication;

public class TestLegisTrackApplication {

	public static void main(String[] args) {
		SpringApplication.from(LegisTrackApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
