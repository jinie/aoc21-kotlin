fun main() {
    fun part1(input: List<List<Int>>): Int {
        var sum = 0
        for(i in input.indices){
            for(j in input[i].indices){
                var neighbours = mutableListOf<Int>()

                if(j > 0) neighbours.add(input[i][j-1]) // Check left
                if(j < input[i].size-1) neighbours.add(input[i][j+1]) // Check right
                if(i > 0) neighbours.add(input[i-1][j]) // Check up
                if(i < input.size-1) neighbours.add(input[i+1][j])

                if(neighbours.minOf { it } > input[i][j])
                    sum+=input[i][j]+1
            }
        }
        return sum
    }

    fun part2(input: List<List<Int>>): Int {
        return 0
    }

    val testInput = readInput("Day09_test").map { it.trim().toCharArray().map { it.toString().toInt() }}
    check(part1(testInput) == 15)


    measureTimeMillisPrint {
        val input = readInput("Day09").map { it.trim().toCharArray().map { it.toString().toInt() } }
        println("Part 1 : " + part1(input))
        println("Part 2 : " + part2(input))
    }

}
