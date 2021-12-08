fun main() {
    data class Digit(val outputLine: List<String>, val inputLine: List<String>)

    fun parseInput(input: List<String>): List<Digit> =
        input.map { it.trim().split("|") }.map { p -> Digit(p.first().trim().split(" "), p.last().trim().split(" ")) }

    fun part1(input: List<Digit>): Int = input.sumOf { it.inputLine.count { p -> p.length in intArrayOf(2, 3, 4, 7) } }

    /**
     * Own solution
     */

    fun part2(input: List<Digit>): Int {

        val sum = input.sumOf { pattern ->
            var one = setOf<Char>()
            var four = setOf<Char>()
            var seven = setOf<Char>()
            var eight = setOf<Char>()

            pattern.outputLine.forEach {
                when (it.length) {
                    3 -> seven = it.toSet()
                    4 -> four = it.toSet()
                    2 -> one = it.toSet()
                    7 -> eight = it.toSet()
                }
            }

            var sub = mutableMapOf(seven to "7", four to "4", one to "1", eight to "8")

            for (it in pattern.outputLine.filter { it.length == 5 }.map { it.toSet() }) { // Length 5 = 2,3 or 5
                when{
                    (it.minus(one).size == 3) -> sub[it] = "3"
                    (it.minus(four).size == 2) -> sub[it] = "5"
                    else -> sub[it] = "2"
                }
            }
            for (it in pattern.outputLine.filter { it.length == 6 }.map { it.toSet() }) { // Length 6 = 0,6 or 9
                when{
                    (it.minus(four).size == 2) -> sub[it] = "9"
                    ((eight.minus(it)).intersect(one).isNotEmpty()) -> sub[it] = "6"
                    else -> sub[it] = "0"
                }
            }

            var nrStr = ""
            for (l in pattern.inputLine) {
                nrStr += sub[l.toSet()]!!
            }
            nrStr.toInt()
        }
        return sum
    }
    check(26 == part1(parseInput(readInput("Day08_test"))))

    measureTimeMillisPrint {
        val input = parseInput(readInput("Day08"))
        println("Part 1 : " + part1(input))
        println("Part 2 : " + part2(input))
    }
}
