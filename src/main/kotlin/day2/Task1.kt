package adventofcode2023.day2

import java.io.File
import java.io.InputStream

class Task1(private val filePath: String = "src/main/kotlin/day2/input.txt") {
    fun solve(): Int {
        val inputStream: InputStream = File(filePath).inputStream()
        val gameIdRegex = "Game (\\d+)".toRegex();
        val gameSetsRegex = "Game \\d+: (.*)".toRegex()

        val validGameSetIds = mutableListOf<Int>()

        inputStream.bufferedReader().forEachLine { line ->
            val gameId = gameIdRegex.find(line)?.groupValues?.get(1)
            val gameMaps: List<Map<String, Int>> =
                gameSetsRegex.find(line)?.groupValues?.get(1)?.split(";")?.map { set ->
                    val map = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)

                    set.split(",").forEach { colorSet ->
                        val (count, color) = colorSet.trim().split(" ")
                        map[color] = map[color]!! + count.toInt()
                    }

                    map
                } ?: emptyList()

            val isValidGame = gameMaps.all { gameMap ->
                gameMap["red"]!! <= 12 && gameMap["green"]!! <= 13 && gameMap["blue"]!! <= 14
            }

            if (isValidGame) {
                validGameSetIds.add(gameId!!.toInt())
            }
        }

        return validGameSetIds.sum()
    }
}

fun main() {
    val task1 = Task1()
    println("task1: ${task1.solve()}") // 2545
}