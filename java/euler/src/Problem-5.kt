/**
 * 2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.
 * What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?
 */

fun main(args: Array<String>) {

    val smallPositiveNumber = IntProgression.fromClosedRange(20, 240000000, 20)
            .first { i -> listOf(3, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19).all { i % it == 0 } }

    println(smallPositiveNumber)

}
