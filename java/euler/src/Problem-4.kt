/**
 * A palindromic number reads the same both ways.
 * The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.
 * Find the largest palindrome made from the product of two 3-digit numbers.
 */

fun main(args: Array<String>) {

    val largestPalindrome = IntRange(1, 999)
            .flatMap { i -> IntRange(1, 999).filter { i < it }.map { i * it } }
            .map { it.toString() }
            .filter { it == it.reversed() }
            .map { it.toInt() }
            .max()

    println(largestPalindrome)

}
