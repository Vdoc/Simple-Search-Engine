package search

import java.io.File

fun main(args: Array<String>) {

    var peoples: List<String> =
        prepareTheData(args[1])
//        prepareTheData("F:\\IdeaProjects\\Simple Search Engine\\Simple Search Engine\\task\\src\\search\\text.txt")
    var isExit: Boolean = false
    var menu = ""

    while (!isExit) {
        printMenu()
        menu = readAnswer()

        when (menu) {
            "0" -> isExit = true
            "1" -> findAPerson(peoples)
            "2" -> printAllPeople(peoples)
            else -> print("\nIncorrect option! Try again.\n")
        }
    }
    println("\nBye!")
}

class Matrix {
    var x: Int = 0
    var y: Int = 0
    val m: Array<IntArray>

    constructor(
        x: Int,
        y: Int
    ) {
        this.x = x
        this.y = y
        m = Array(x) { IntArray(y) }
        for (i in 0 until x) {
            for (j in 0 until y)
                m[i][j] = 0
        }
    }
}

fun prepareTheData(fileName: String): List<String> = File(fileName).readLines()

fun printMenu() {
    print("=== Menu ===\n" +
          "1. Find a person\n" +
          "2. Print all people\n" +
          "0. Exit\n")
}

fun readAnswer(): String {
    return readLine()!!
    println()
}

fun printAllPeople(peoples: List<String>) {
    println("\n=== List of people ===")
    for (i in 0 until peoples.size) println(peoples[i])
    println()
}

fun findAPerson(peoples: List<String>) {
    println("\nSelect a matching strategy: ALL, ANY, NONE")
    val answer = readAnswer()
    println("\nEnter a name or email to search all suitable people.")
    val query = readAnswer().split(" ")

    var result = ""
    var res = mutableSetOf<Int>()
    var res2 = mutableListOf<Int>()
    val matrix: Matrix = Matrix(query.size, peoples.size)

    for (i in 0 until query.size) {
        findOne(peoples, query[i], matrix.m[i])
    }

    when (answer.toUpperCase()) {
        "ALL" ->  res = findAll(matrix)
        "ANY" -> res = findAny(matrix)
        "NONE" -> res = findNone(matrix)
        else -> {
            print("\nIncorrect option! Try again.\n")
            return
        }
    }

    res2 = res.toMutableList()
    res2.sort()

    if (res2.size == 0) {
        print("No matching people found.\n")
        return
    }

    result = "\n" + res2.size + " persons found: \n"

    for (i in 0 until res2.size) result += "" + peoples[res2.get(i)] + "\n"

    println()
    println(result.trim())
    println()
}

fun findOne(peoples: List<String>, word: String, result: IntArray) {
    val n = peoples.size
    for (j in 0 until n) {
        val person = peoples[j].split(" ")
        val size = person.size
        for (k in 0..size-1) {
            if (word[0] == '@') {
                if (person[k].split('@')[1].contains(word.drop(1), ignoreCase = true)) {
                    result[j] = 1
                    break
                }
            }
            if (person[k].contains(word, ignoreCase = true)) {
                result[j] = 1
                break
            }
//            if (q[0] == '@')
        }
    }
}

fun findAll(matrix: Matrix): MutableSet<Int> {
    val res = mutableSetOf<Int>()
    var counter = 0
    for (i in 0 until matrix.y){
        for (j in 0 until matrix.x) {
            if (matrix.m[j][i] == 1) {
                counter++
            }
        }
        if (counter == matrix.x) res.add(i)
        counter = 0
    }
    return res
}

fun findAny(matrix: Matrix): MutableSet<Int> {
    val res = mutableSetOf<Int>()
    for (i in 0 until matrix.y){
        for (j in 0 until matrix.x) {
            if (matrix.m[j][i] == 1) {
                res.add(i)
            }
        }
    }
    return res
}

fun findNone(matrix: Matrix): MutableSet<Int> {
    val res = mutableSetOf<Int>()
    var counter = 0
    for (i in 0 until matrix.y){
        for (j in 0 until matrix.x) {
            if (matrix.m[j][i] == 1) {
                counter++
            }
        }
        if (counter == 0) res.add(i)
        counter = 0
    }
    return res
}


