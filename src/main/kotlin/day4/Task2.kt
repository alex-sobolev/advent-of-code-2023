package adventofcode2023.day4

import java.io.File

class Task2(private val filePath: String = "src/main/kotlin/day4/input.txt") {
    fun solution(): Int {
        val input = File(filePath).readText()
        val lines = input.split("\n")
        val cardWins = getCardMap()
        val queue = ArrayDeque<Int>()
        var totalWins = 0

        for (card in cardWins.keys) {
            totalWins += 1

            if (cardWins[card]!!.cardsWon.isNotEmpty()) {
                queue.addAll(cardWins[card]!!.cardsWon)
            }
        }

        while (queue.isNotEmpty()) {
            val cardId = queue.removeFirst()
            totalWins += 1

            if (cardWins[cardId] != null && cardWins[cardId]!!.cardsWon.isNotEmpty()) {
                queue.addAll(cardWins[cardId]!!.cardsWon)
            }
        }

        return totalWins
    }

    private data class Card(val id: Int, val points: List<Int>, val cardsWon: List<Int>)

    private fun getCardMap(): MutableMap<Int, Card> {
        val input = File(filePath).readText()
        val lines = input.split("\n")
        val cardWins = mutableMapOf<Int, Card>()

        for (line in lines) {
            val (left, right) = line.split(":")
            val cardId = left.trim().split(" ").filter { it.trim() != "" }[1].trim().toInt()
            val cardPoints = mutableListOf<Int>()

            val (winningNums, givenNums) = right.split("|").map { it.trim() }
                .map { it.split(" ").map { it.trim() }.filter { it != "" }.map { it.toInt() }.sorted() }

            for (num in winningNums) {
                if (givenNums.contains(num)) {
                    cardPoints.add(num)
                }
            }

            val cardsWon = if (cardPoints.size == 0) {
                emptyList<Int>()
            } else {
                List(cardPoints.size) { index -> cardId + index + 1 }
            }

            cardWins[cardId] = Card(id = cardId, points = cardPoints, cardsWon = cardsWon)
        }

        return cardWins
    }
}

fun main() {
    val test = Task2("src/main/kotlin/day4/input2.txt")
    println("task2 test: ${test.solution()}") // 30

    val task1 = Task2()
    println("task 1: ${task1.solution()}") // 13261850
}
