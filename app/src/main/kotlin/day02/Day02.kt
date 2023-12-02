package day02

import common.InputRepo
import common.readSessionCookie
import common.solve
import util.splitIntoPair


fun main(args: Array<String>) {
    val day = 2
    val input = InputRepo(args.readSessionCookie()).get(day = day)

    solve(day, input, ::solveDay02Part1, ::solveDay02Part2)
}

fun solveDay02Part1(input: List<String>): Int {
    val games = input.map { parseGameRecord(it) }
    println(games)

    var sum = 0
    for ((id, pulls) in games) {
        val invalidPull = pulls.find { map ->
            map.getOrDefault(Color.RED, 0) > 12 ||
                    map.getOrDefault(Color.GREEN, 0) > 13 ||
                    map.getOrDefault(Color.BLUE, 0) > 14
        }
        if (invalidPull != null) {
            println(invalidPull)
            println(id)
        } else {
            sum += id
        }
    }
    return sum
}

fun solveDay02Part2(input: List<String>): Int {
    val games = input.map { parseGameRecord(it) }
    println(games)

    val sum = games.sumOf {
        val pulls = it.pulls
        val redPull = pulls.maxBy { map ->
            map.getOrDefault(Color.RED, 0)
        }.getOrDefault(Color.RED, 0)
        val greenPull = pulls.maxBy { map ->
            map.getOrDefault(Color.GREEN, 0)
        }.getOrDefault(Color.GREEN, 0)
        val bluePull = pulls.maxBy { map ->
            map.getOrDefault(Color.BLUE, 0)
        }.getOrDefault(Color.BLUE, 0)
        redPull * greenPull * bluePull
    }
    return sum
}

enum class Color {
    BLUE, RED, GREEN,
}

private fun parseGameRecord(line: String): GameRecord {
    val moveRegex = """Game (\d+): (.*)$""".toRegex()
    return moveRegex.matchEntire(line)
        ?.destructured
        ?.let { (id, pulls) ->
            GameRecord(id.toInt(), pulls.split(';').map { s ->
                s.split(',').map {
                    it.trim().splitIntoPair(" ")
                        .let { pair -> Pair(Color.valueOf(pair.second.uppercase()), pair.first.toInt()) }
                }.toMap()
            })
        }
        ?: throw IllegalArgumentException("Bad input '$line'")
}


data class GameRecord(val id: Int, val pulls: List<Map<Color, Int>>)

