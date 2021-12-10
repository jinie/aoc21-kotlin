fun main() {
    var openingBraces = listOf('(', '[', '{', '<')
    var bracePairs = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')

    fun checkLine(input: String): Long {
        val illegalChars = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
        var stack = mutableListOf<Char>()
        var err = 0L
        for (c in input) {
            if (c in openingBraces) {
                stack.add(c)
            } else {
                val cl = stack.removeLast()
                if (bracePairs[c] != cl) {
                    err += illegalChars[c]!!
                }
            }
        }
        return err
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { checkLine(it) }
    }

    fun part2(input: List<String>): Long {
        val multipliers = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)
        val lineScores = mutableListOf<Long>()
        for (l in input.filterNot { checkLine(it) > 0 }) {
            var stack = mutableListOf<Char>()
            for (c in l) {
                if (c in openingBraces)
                    stack.add(c)
                else {
                    if (stack.last() == bracePairs[c])
                        stack.removeLast()
                }
            }
            var sum: Long = 0
            lineScores.add(stack.reversed().map { multipliers[it]!!.toLong() }.reduce { acc, i -> ((acc * 5) + i) })
        }
        return lineScores.sorted()[lineScores.size / 2]
    }

    val testInput = readInput("Day10_test").map { it.trim() }
    check(part1(testInput) == 26397L)

    measureTimeMillisPrint {
        val input = readInput("Day10").map { it.trim() }
        println("Part 1 : " + part1(input))
        println("Part 2 : " + part2(input))
    }

}
