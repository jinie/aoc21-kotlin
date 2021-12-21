class Day21(input: List<String>) {
    private var dice = 1
    private val players = parseInput(input)
    private fun roll(): Int = dice++ % 100
    private var orgStart: Pair<Int, Int>? = null

    data class Player(val id: Int, var pos: Int, var score: Int = 0)

    private fun turn() {
        for (p in players) {
            p.pos += (0 until 3).sumOf { roll() }
            p.pos = p.pos % 10
            p.score += p.pos + 1
            if (p.score >= 1000)
                break
        }
    }

    private fun parseInput(input: List<String>): List<Player> {
        val inp = input.map {
            """Player (\d+) starting position: (\d+)""".toRegex()
                .matchEntire(it)!!.destructured.let { r -> Player(r.component1().toInt(), r.component2().toInt() - 1) }
        }
        orgStart = Pair(inp.first().pos+1, inp.last().pos+1)
        return inp
    }

    fun part1(): Int {
        while (players.maxOf { it.score } < 1000)
            turn()
        return players.minByOrNull { it.score }!!.score * (dice - 1)
    }

    /**
     * Part 2 From https://github.com/ephemient/aoc2021/blob/main/kt/src/commonMain/kotlin/com/github/ephemient/aoc2021/Day21.kt
     */

    fun part2(): Long = Score(orgStart!!.first, orgStart!!.second, 0, 0).let { (first, second) -> maxOf(first, second) }

    private object Score {
        private val dice = sequence {
            for (i in 1..3) for (j in 1..3) for (k in 1..3) yield(i + j + k)
        }.groupingBy { it }.eachCount()
        private val x = LongArray(44100)
        private val y = LongArray(44100)

        operator fun invoke(player1: Int, player2: Int, score1: Int, score2: Int): Pair<Long, Long> {
            val i = player1 + 10 * player2 + 100 * score1 + 2100 * score2 - 11
            if (x[i] == 0L && y[i] == 0L) {
                var x1 = 0L
                var y1 = 0L
                for ((d, n) in dice) {
                    val play = (player1 + d - 1) % 10 + 1
                    if (score1 + play < 21) {
                        val (x2, y2) = this(player2, play, score2, score1 + play)
                        x1 += n * y2
                        y1 += n * x2
                    } else {
                        x1 += n
                    }
                }
                x[i] = x1
                y[i] = y1
            }
            return x[i] to y[i]
        }
    }
}


fun main() {
    val td = Day21(readInput("Day21_test"))
    check(td.part1() == 739785)

    measureTimeMillisPrint {
        val d = Day21(readInput("Day21"))
        println(d.part1())
        println(d.part2())
    }
}