fun main() {
    fun fold(grid: Set<Pair<Int,Int>>, folds: List<Pair<String,Int>>): Set<Pair<Int,Int>>{
        return folds.fold(grid){ pt, i ->
            val (axis, pos) = i
            when (axis) {
                "y" ->
                    pt
                    .filter { it.second > pos }
                    .map { it.first to 2 * pos - it.second }
                    .toSet() + pt.filter { it.second < pos }
                else -> pt
                    .filter { it.first > pos }
                    .map { 2 * pos - it.first to it.second }
                    .toSet() + pt.filter { it.first < pos }
            }
        }
    }

    fun draw(input: Set<Pair<Int,Int>>){
        val out = mutableListOf<String>()
        for(y in 0 .. input.maxOf { it.second }){
            out.add("")
            for(x in 0 .. input.maxOf { it.first }){
                if(Pair(x,y) in input){
                    out[y]+="#"
                }else{
                    out[y]+=" "
                }
            }
        }
        out.forEach{
            println(it)
        }
    }

    measureTimeMillisPrint {
        val input = readInput("Day13").map { it.trim() }
        val input1 = input.asSequence().filterNot { s -> s.contains("=") }.filter { it.isNotEmpty() }.map { it.split(",")}.map { p -> Pair(p.first().toInt(), p.last().toInt()) }.toSet()
        val input2  = input.filter { s -> s.contains("=") }.map { it.split("=")}.map { p -> p.first().split(" ").last() to p.last().toInt() }
        println("Part 1 : " + fold(input1, input2.take(1)).size)
        draw(fold(input1, input2))
    }

}
