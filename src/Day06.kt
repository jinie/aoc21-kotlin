import java.math.BigInteger

fun main() {
    fun part1(ndays: Int, input: List<Int>): BigInteger {
        val fish = generateSequence { 0 }.take(9).map{ it.toBigInteger() }.toMutableList()
        for(i in input){
            fish[i]+=1.toBigInteger()
        }

        for (i in 0 until ndays){
            val tfish = fish.removeFirst()
            fish.add(tfish)
            fish[6] += tfish
        }

        var sum = 0.toBigInteger()
        for(f in fish){
            sum += f
        }

        return sum
    }

    val testInput = "3,4,3,1,2".split(",").map{ it.toInt()}
    check(part1(80, testInput)==5934.toBigInteger())

    val input = readInput("Day06").map{ it ->
        it.trim().split(",").map{
                ival -> ival.toInt()
        }
    }.flatten()
    println("Part 1 : "+part1(80, input))
    println("Part 2 : "+part1(256, input))
}
