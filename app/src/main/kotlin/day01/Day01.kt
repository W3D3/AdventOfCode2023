package day01

import common.InputRepo
import common.readSessionCookie
import common.solve

fun main(args: Array<String>) {
    val day = 1
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay01Part1, ::solveDay01Part2)
}

val numbers = arrayOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

fun solveDay01Part1(input: List<String>): Int {
    return input.map { s: String ->
        val first = s.find { c -> c.isDigit() }
        val last = s.findLast { c -> c.isDigit() }
        val nr = "$first$last"
        println(nr)
        nr.toInt()
    }.sum()
}

fun solveDay01Part2(input: List<String>): Int {
    return input.map { s: String ->
        // HACK replace this with a proper overlap solution
        val line = s.replace("one", "on1e").replace("two", "tw2o").replace("three", "thre3e")
        val regex = "(\\d|one|two|three|four|five|six|seven|eight|nine)".toRegex()
        val matches = regex.findAll(line).sortedBy { it.range.first }
        val first = matches.first().value
        val last = matches.last().value
        val matchesValues = matches.map { it.value }
        println(matchesValues)
        val firstDigit = if (first.length == 1) first.toInt() else numbers.indexOf(first) + 1
        val lastDigit = if (last.length == 1) last.toInt() else numbers.indexOf(last) + 1
        val nr = "$firstDigit$lastDigit"
        nr.toInt()
    }.sum()
}
