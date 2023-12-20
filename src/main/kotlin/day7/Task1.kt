package adventofcode2023.day7

import java.io.File

class Task1(private val filePath: String = "src/main/kotlin/day7/input.txt") {
    private val cards = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')

    private val handRanks = listOf(
        "High card",
        "One pair",
        "Two pairs",
        "Three of a kind",
        "Full house",
        "Four of a kind",
        "Five of a kind"
    )

    fun solve(): Int {
        val lines = File(filePath).readLines()
        val hands = mutableListOf<Pair<Hand, Int>>()

        for (line in lines) {
            val (hand, bid) = line.split(" ")
            hands.add(Pair(Hand(hand), bid.toInt()))
        }

        val sortedHands = hands.sortedBy { it.first }

        val totalWin = sortedHands.foldIndexed(0) { index, acc, pair ->
            val currentWin = pair.second * (index + 1)

            acc + currentWin
        }

        return totalWin
    }

    inner class Hand(private val hand: String) : Comparable<Hand> {
        override fun toString(): String {
            return hand
        }

        override fun compareTo(other: Hand): Int {
            val hand1Type = getHandType(hand)
            val hand2Type = getHandType(other.hand)

            return when {
                handRanks.indexOf(hand1Type) == handRanks.indexOf(hand2Type) -> {
                    var i = 0

                    while (i < hand.length) {
                        if (cards.indexOf(hand[i]) > cards.indexOf(other.hand[i])) {
                            return 1
                        } else if (cards.indexOf(hand[i]) < cards.indexOf(other.hand[i])) {
                            return -1
                        }

                        i++
                    }

                    return 0
                }

                handRanks.indexOf(hand1Type) > handRanks.indexOf(hand2Type) -> 1
                handRanks.indexOf(hand1Type) < handRanks.indexOf(hand2Type) -> -1
                else -> 0
            }
        }
    }

    private fun getHandType(hand: String): String {
        val handMap = mutableMapOf<Char, Int>()

        for (card in hand) {
            when {
                handMap.containsKey(card) -> {
                    handMap[card] = handMap[card]!! + 1
                }

                else -> {
                    handMap[card] = 1
                }
            }
        }

        val handValues = handMap.values.filter { it > 0 }.sortedDescending()

        val handType = when (handValues) {
            listOf(1, 1, 1, 1, 1) -> handRanks[0]
            listOf(2, 1, 1, 1) -> handRanks[1]
            listOf(2, 2, 1) -> handRanks[2]
            listOf(3, 1, 1) -> handRanks[3]
            listOf(3, 2) -> handRanks[4]
            listOf(4, 1) -> handRanks[5]
            listOf(5) -> handRanks[6]
            else -> "Unknown"
        }

        return handType
    }
}

fun main() {
    val test = Task1("src/main/kotlin/day7/input2.txt")
    println("task1 test: ${test.solve()}") // 6440

    val task1 = Task1()
    println("task 1: ${task1.solve()}") // 251121738
}
