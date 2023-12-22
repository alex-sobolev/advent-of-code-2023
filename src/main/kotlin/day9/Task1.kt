package adventofcode2023.day91

import java.io.File

class Task1(private val filePath: String = "src/main/kotlin/day9/input.txt") {
    fun solve(): Int {
        val lines = File(filePath).readLines()
        val initialSequences = lines.map { it.split(" ").map { it.toInt() } }
        val nextNumbers = mutableListOf<Int>()

        initialSequences.forEach { seq ->
            val res = getNextNumber(seq)
            nextNumbers.add(res)
        }

        return nextNumbers.sum()
    }

    private fun getNextNumber(sequence: List<Int>): Int {
        val history = mutableListOf<List<Int>>(sequence)
        var currentSeq = sequence

        while (!allZeroes(currentSeq)) {
            val newSeq = mutableListOf<Int>()

            for (i in 1..<currentSeq.size) {
                newSeq.add(currentSeq[i] - currentSeq[i - 1])
            }

            history.add(newSeq)
            currentSeq = newSeq
        }

        var predictionNum: Int = 0

        for (i in history.size - 1 downTo 0) {
            val seq = history[i]
            predictionNum += seq.last()
        }

        return predictionNum
    }

    private fun allZeroes(sequence: List<Int>): Boolean = sequence.all { it == 0 }
}

fun main() {
    val test = Task1("src/main/kotlin/day9/input2.txt")
    println("task1 test: ${test.solve()}") // 114

    val task1 = Task1()
    println("task 1: ${task1.solve()}") // 1938731307
}