fun main() {
    fun part1(input: List<String>): Long {
        var gamma = mutableListOf<Char>()
        for (i in 0 until input[0].length) {
            gamma += input.groupingBy { it[i] }.eachCount().entries.maxWithOrNull(compareBy({ it.value },
                { it.key })
            )!!.key
        }
        val epsilon = gamma.map { if (it == '0') '1' else '0' }.joinToString("")
        return epsilon.toLong(2) * gamma.joinToString("").toLong(2)
    }

    /* Part 2 heavily inspired from (aka stolen/borrowed)
        https://github.com/ephemient/aoc2021/blob/main/kt/src/commonMain/kotlin/com/github/ephemient/aoc2021/Day3.kt
     */
    fun part2(input: List<String>): Int {
        val comparator: Comparator<Map.Entry<Char, Int>> = compareBy({ it.value }, { it.key })
        val o2 = input.toMutableList()
        val co2 = input.toMutableList()
        for (i in 0 until (input.minOfOrNull { it.length } ?: 0)) {
            val common = o2.groupingBy { it[i] }.eachCount().entries.maxWithOrNull(comparator)!!.key
            val uncommon = co2.groupingBy { it[i] }.eachCount().entries.minWithOrNull(comparator)!!.key
            o2.retainAll { it[i] == common }
            co2.retainAll { it[i] == uncommon }
        }
        return o2.single().toInt(2) * co2.single().toInt(2)
    }

    measureTimeMillisPrint {
        val input = readInput("Day03").map {
            it.trim()
        }

        println(part1(input))
        println(part2(input))
    }
}
