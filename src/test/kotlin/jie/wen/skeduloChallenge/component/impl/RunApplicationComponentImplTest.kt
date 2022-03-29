package jie.wen.skeduloChallenge.component.impl

import jie.wen.skeduloChallenge.component.RunApplicationComponent
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class RunApplicationComponentImplTest {
    @Autowired
    private lateinit var runApplicationComponent: RunApplicationComponent

    @Test
    fun generateOutputFileName() {
        val input = "performances.json"
        val actual = runApplicationComponent.generateOutputFileName(input)
        assertEquals("performances.optimal.json", actual)
    }
}