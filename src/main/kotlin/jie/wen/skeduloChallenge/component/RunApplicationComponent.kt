package jie.wen.skeduloChallenge.component

interface RunApplicationComponent {
    fun runApplication(input: String)
    fun generateOutputFileName(inputName: String): String
}