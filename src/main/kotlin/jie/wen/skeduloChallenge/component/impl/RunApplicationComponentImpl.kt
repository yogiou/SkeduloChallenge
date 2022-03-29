package jie.wen.skeduloChallenge.component.impl

import jie.wen.skeduloChallenge.component.RunApplicationComponent
import jie.wen.skeduloChallenge.data.Constants
import jie.wen.skeduloChallenge.data.Constants.USER_DIR
import jie.wen.skeduloChallenge.service.FindBestPerformanceListService
import jie.wen.skeduloChallenge.service.OutputPerformanceJsonFileService
import jie.wen.skeduloChallenge.service.ReadInputJsonFileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.File

@Component
class RunApplicationComponentImpl: RunApplicationComponent {
    @Autowired
    private lateinit var readInputJsonFileService: ReadInputJsonFileService

    @Autowired
    private lateinit var findBestPerformanceListService: FindBestPerformanceListService

    @Autowired
    private lateinit var outputPerformanceJsonFileService: OutputPerformanceJsonFileService

    // main method to run the program
    override fun runApplication(input: String) {
        // get the input file path
        val path = input.takeIf { it.contains(File.separator) }?.let { input.substring(0, input.lastIndexOf(File.separator)) } ?: run { System.getProperty(USER_DIR) }

        // get the input file name
        val inputName = input.takeIf { it.contains(File.separator) }?.let { input.substring(input.lastIndexOf(File.separator) + 1) } ?: run { input }

        // read the input file and convert to json object
        val inputList = readInputJsonFileService.readJsonFile(input)

        // find the optimal performance list
        val optimalPerformanceList = findBestPerformanceListService.findBestPerformanceList(inputList)

        // generate the json string and output to json file
        outputPerformanceJsonFileService.generatePerformanceJsonFile(optimalPerformanceList, path, generateOutputFileName(inputName))
    }

    // generate the output file
    override fun generateOutputFileName(inputName: String): String {
        val lastDotIndex = inputName.lastIndexOf(Constants.DOT)
        val extension = inputName.substring(inputName.lastIndexOf(Constants.DOT))
        val prefix = inputName.substring(0, lastDotIndex + 1)
        return prefix + Constants.OUTPUT_FILE_NAME_SUFFIX + extension
    }
}