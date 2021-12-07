import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

inline fun measureTimeMillis(block : () -> Unit): Long {
    val start = System.currentTimeMillis()
    block()
    return System.currentTimeMillis() - start
}

fun transpose(matrix: List<List<Int>>): List<List<Int>> =
    (0 until matrix[0].size).map { column ->
        (0 until matrix.size).map { row ->
            matrix[row][column]
        }
    }
