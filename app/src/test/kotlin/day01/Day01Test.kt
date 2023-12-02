package day01

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class Day01Test : FreeSpec({

    val sampleInput: List<String> = """
    1abc2
    pqr3stu8vwx
    a1b2c3d4e5f
    treb7uchet
    """.trimIndent().split("\n")

    val sampleInput2: List<String> = """
    two1nine
    eightwothree
    abcone2threexyz
    xtwone3four
    4nineeightseven2
    zoneight234
    7pqrstsixteen
    """.trimIndent().split("\n")

    val sampleInput3: List<String> = """
    twone
    """.trimIndent().split("\n")

    val sampleSolutionPart1 = 142
    val sampleSolutionPart2 = 281
    val sampleSolutionPart3 = 21

    "Solving day 1" - {
        "part 1 for the sample input should return the correct output" {
            solveDay01Part1(sampleInput) shouldBe sampleSolutionPart1
        }

        "part 2 for the sample input should return the correct output" {
            solveDay01Part2(sampleInput2) shouldBe sampleSolutionPart2
        }

        "part 2 for the sample input 3 should return the correct output" {
            solveDay01Part2(sampleInput3) shouldBe 21
        }
    }
})
