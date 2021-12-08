fun main() {

    fun parseInput(input: List<String>): List<Pair<List<Set<Char>>,List<Set<Char>>>> =
        input.map { it.trim().split("|") }.map { p -> Pair(p.first().trim().split(" ").map { it.toCharArray().toSet() }, p.last().trim().split(" ").map { it.toCharArray().toSet() }) }

    fun part1(input: List<Pair<List<Set<Char>>,List<Set<Char>>>>): Int = input.sumOf { it.second.count { p -> p.size in intArrayOf(2, 3, 4, 7) } }

    /**
     * "Optimal solution" for further study, stolen from https://www.reddit.com/r/adventofcode/comments/rbj87a/comment/hnpn4xm/?utm_source=share&utm_medium=web2x&context=3
     */
    fun part2(input: List<Pair<List<Set<Char>>,List<Set<Char>>>>): Int {
        return input.sumOf { (patterns, output) ->
            val mappedDigits = mutableMapOf(
                1 to patterns.first { it.size == 2 },
                4 to patterns.first { it.size == 4 },
                7 to patterns.first { it.size == 3 },
                8 to patterns.first { it.size == 7 },
            )

            with(mappedDigits) {
                put(3, patterns.filter { it.size == 5 }.first { it.intersect(getValue(1)).size == 2 })
                put(2, patterns.filter { it.size == 5 }.first { it.intersect(getValue(4)).size == 2 })
                put(5, patterns.filter { it.size == 5 }.first { it !in values })
                put(6, patterns.filter { it.size == 6 }.first { it.intersect(getValue(1)).size == 1 })
                put(9, patterns.filter { it.size == 6 }.first { it.intersect(getValue(4)).size == 4 })
                put(0, patterns.filter { it.size == 6 }.first { it !in values })
            }

            val mappedPatterns = mappedDigits.entries.associateBy({ it.value }) { it.key }
            output.joinToString("") { mappedPatterns.getValue(it).toString() }.toInt()
        }
    }


    val testInput = parseInput(readInput("Day08_test"))
    check(26 == part1(testInput))
    check(61229 == part2(testInput))

    measureTimeMillisPrint {
        val input = parseInput(readInput("Day08"))
        println("Part 1 : " + part1(input))
        println("Part 2 : " + part2(input))
    }
}
