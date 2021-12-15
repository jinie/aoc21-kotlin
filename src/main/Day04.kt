class BingoNumber(var n: Int, var marked: Boolean = false) {
    fun mark() {
        marked = true
    }
}

class Row(val numbers: List<BingoNumber>) {
    val sumUnmarked: Int get() = numbers.filter { !it.marked }.sumOf { it.n }
    fun isFull() = numbers.all { it.marked }

    fun mark(number: Int) {
        numbers.filter { it.n == number }.forEach { it.mark() }
    }
}

class Board(private val rows: List<Row>, var winner: Boolean = false) {
    fun mark(number: Int) {
        rows.forEach { it.mark(number) }
    }

    private val sumUnmarked: Int get() = rows.sumOf { it.sumUnmarked }

    fun score(number: Int): Int {
        rows.firstFilled()?.let { return (sumUnmarked) * number }
        rows.toColumns().firstFilled()?.let { return (sumUnmarked) * number }
        return 0
    }

}

fun List<Board>.mark(number: Int) = this.forEach { it.mark(number) }

fun List<Row>.toColumns(): List<Row> {
    val columns = mutableListOf<Row>()
    if (this.isEmpty()) return columns

    for (i in 0 until 5) {
        val tiles = mutableListOf<BingoNumber>()
        for (j in 0 until 5) {
            tiles.add(this[j].numbers[i])
        }
        columns.add(Row(tiles))
    }
    return columns
}

fun List<Row>.firstFilled() = this.firstOrNull { it.isFull() }

fun main() {

    fun part1(input: List<Int>, boards: List<Board>): Int? {
        for (i in input) {
            //val winningScore = boards.map { it.score(i) }.firstOrNull { it > 0 }
            //winningScore?.let { return it }
            for (b in boards) {
                b.mark(i)
                if (b.score(i) > 0) {
                    return b.score(i)
                }
            }
        }
        return null
    }

    fun part2(input: List<Int>, boards: List<Board>): Int {
        for (number in input) {
            boards.mark(number)
            boards.filter { !it.winner }.forEach {
                val score = it.score(number)
                if (score > 0) {
                    it.winner = true
                    if (boards.all { board -> board.winner }) {
                        return score
                    }
                }
            }
        }
        return 0
    }

    measureTimeMillisPrint {
        val input = readInput("Day04").filter { it.trimIndent().isNotBlank() }
        val numbers = input[0].split(",").map { it.toInt() }
        val boards =
            input.asSequence().drop(1).map { line -> line.split(" ").filter { it.isNotBlank() }.map { it.toInt() } }
                .map { line -> Row(line.map { BingoNumber(it) }) }.chunked(5).map { Board(it) }.toList()
        println("Part 1 : " + part1(numbers, boards))
        println("Part 2 : " + part2(numbers, boards))
    }
}
