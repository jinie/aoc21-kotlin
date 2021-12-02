fun main() {
    fun part1(input: List<Int>): Int {
        var res = 0
        for(i in 0 until input.size-1){
            if (input[i+1] > input[i])
                res++
        }
        return res
    }

    fun part2(input: List<Int>): Int {
        var res = 0
        for(i in 0..(input.size-4)){
            val w1 = input.slice(i..i+2).sum()
            val w2 = input.slice(i+1..i+3).sum()
            if(w2 > w1)
                res++
        }
        return res
    }

    val input = readInput("Day01").map{ it.trim().toInt() }
    println("Part 1 : "+part1(input))
    println("Part 2 : "+part2(input))
}
