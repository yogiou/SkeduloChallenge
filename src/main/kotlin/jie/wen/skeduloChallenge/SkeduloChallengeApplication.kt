package jie.wen.skeduloChallenge

import jie.wen.skeduloChallenge.component.RunApplicationComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SkeduloChallengeApplication: CommandLineRunner {
	@Autowired
	private lateinit var runApplicationComponent: RunApplicationComponent

	override fun run(args: Array<String>) {
		args.takeIf { args.size == 1 }?.run {
			runApplicationComponent.runApplication(args[0])
		} ?: run {
			println("Error: Please input json file path")
		}
	}
}

fun main(args: Array<String>) {
	runApplication<SkeduloChallengeApplication>(*args)
}




