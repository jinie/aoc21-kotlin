fun main() {
    var openingBraces = listOf('(', '[', '{','<')
    var bracePairs = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')

    fun checkLine(input: String): Int {
        val illegalChars = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
        var stack = mutableListOf<Char>()
        var err = 0
        for(c in input){
            if(c in openingBraces){
                stack.add(c)
            }else {
                val cl = stack.removeLast()
                if(bracePairs[c] != cl){
                   err += illegalChars[c]!!
                }
            }
        }
        return err
    }

    fun part1(input: List<String>): Int{
        return input.sumOf { checkLine(it) }
    }

    fun part2(input: List<String>): Long {
        val multipliers = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)
        val lineScores = mutableListOf<Long>()
        for(l in input.filterNot { checkLine(it) > 0 }){
            var missing = mutableListOf<Char>()
            var stack = mutableListOf<Char>()
            for(c in l) {
                if(c in openingBraces)
                    stack.add(c)
                else{
                    if(stack.last() == bracePairs[c])
                        stack.removeLast()
                }
            }
            missing.addAll(stack)
            var sum:Long = 0
            for(mc in missing.reversed()){
                sum = (sum*5)+multipliers[mc]!!
            }
            if(sum > 0)
                lineScores.add(sum)
        }
        return lineScores.sorted()[lineScores.size/2]
    }

    val testInput = readInput("Day10_test").map { it.trim() }
    check(part1(testInput) == 26397)

    measureTimeMillisPrint {
        val input = readInput("Day10").map { it.trim() }
        println("Part 1 : " + part1(input))
        check(part2(listOf("[({(<(())[]>[[{[]{<()<>>","[(()[<>])]({[<{<<[]>>(","(((({<>}<{<{<>}{[]{[]{}","{<[[]]>}<{[{[{[]{()[[[]","<{([{{}}[<[[[<>{}]]]>[]]"))==288957L)
        println("Part 2 : " + part2(input))
    }

}
