package adventofcode2023.day9

import java.io.File

class Task2(private val filePath: String = "src/main/kotlin/day9/input.txt") {
    fun solve(): Int {
        val lines = File(filePath).readLines()
        val initialSequences = lines.map { it.split(" ").map { it.toInt() } }
        val prevNumbers = mutableListOf<Int>()

        initialSequences.forEach { seq ->
            val res = getPrevNumber(seq)
            prevNumbers.add(res)
        }

        return prevNumbers.sum()
    }

    private fun getPrevNumber(sequence: List<Int>): Int {
        val history = mutableListOf(sequence)
        var currentSeq = sequence

        while (!allZeroes(currentSeq)) {
            val newSeq = mutableListOf<Int>()

            for (i in 1..<currentSeq.size) {
                newSeq.add(currentSeq[i] - currentSeq[i - 1])
            }

            history.add(newSeq)
            currentSeq = newSeq
        }

        var predictedPrevNum = 0

        for (i in history.size - 1 downTo 0) {
            val seq = history[i]
            predictedPrevNum = seq.first() - predictedPrevNum
        }

        return predictedPrevNum
    }

    private fun allZeroes(sequence: List<Int>): Boolean = sequence.all { it == 0 }
}

fun main() {
    val test = Task2("src/main/kotlin/day9/input2.txt")
    println("task2 test: ${test.solve()}") // 2

    val task2 = Task2()
    println("task 2: ${task2.solve()}") // 948
}