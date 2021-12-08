fun main() {
    data class Digit(val outputLine: List<String>, val inputLine: List<String>)

    fun parseInput(input: List<String>): List<Digit> =
        input.map { it.trim().split("|") }.map { p -> Digit(p.first().trim().split(" "), p.last().trim().split(" ")) }

    fun part1(input: List<Digit>): Int = input.sumOf { it.inputLine.count { p -> p.length in intArrayOf(2, 3, 4, 7) } }

    fun part2(input: List<Digit>): Int {

        val sum = input.sumOf { pattern ->
            var zero = setOf<Char>()
            var one = setOf<Char>()
            var two = setOf<Char>()
            var three = setOf<Char>()
            var four = setOf<Char>()
            var five = setOf<Char>()
            var six = setOf<Char>()
            var seven = setOf<Char>()
            var eight = setOf<Char>()
            var nine = setOf<Char>()

            pattern.outputLine.forEach {
                when (it.length) {
                    3 -> seven = it.toSet()
                    4 -> four = it.toSet()
                    2 -> one = it.toSet()
                    7 -> eight = it.toSet()
                }
            }

            for (it in pattern.outputLine.filter { it.length == 5 }) {
                val v = it.toSet()
                if (v.minus(one).size == 3)
                    three = v
                else if (v.minus(four).size == 2)
                    five = v
                else
                    two = v
                for (it in pattern.outputLine.filter { it.length == 6 }) {
                    val v = it.toSet()
                    if (v.minus(four).size == 2)
                        nine = v
                    else if ((eight.minus(v)).intersect(one).isNotEmpty())
                        six = v
                    else if (v.size == 6)
                        zero = v
                }
            }

            var sub = mutableMapOf<Set<Char>, String>()
            sub[zero] = "0"
            sub[one] = "1"
            sub[two] = "2"
            sub[three] = "3"
            sub[four] = "4"
            sub[five] = "5"
            sub[six] = "6"
            sub[seven] = "7"
            sub[eight] = "8"
            sub[nine] = "9"

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
