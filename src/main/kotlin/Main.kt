import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import kotlin.math.pow

var sourceBase: Int = 0
var targetBase: Int = 0

fun main() {
    var result: String = ""
    var decision: MutableList<String>
    var conversionNumber: String
    do {
        print("Enter two numbers in format: {source base} {target base} (To quit type /exit) > ")
        decision = readLine()!!.split(" ").map { it }.toMutableList()
        if (decision[0].replace("[", "").replace("]", "") != "/exit") {
            sourceBase = decision[0].toInt()
            targetBase = decision[1].toInt()
            do {
                print("Enter number in base $sourceBase to convert to base $targetBase (To go back type /back) > ")
                conversionNumber = readLine()!!
                if (conversionNumber != "/back") {
                    if (conversionNumber.contains(".")) {
                        var count: Int = 0
                        var baseInt = ""
                        var fraction = ""
                        while (conversionNumber[count] != '.') {
                            baseInt += conversionNumber[count]
                            count += 1
                        }
                        for (count in count + 1 until conversionNumber.length) {
                            fraction += conversionNumber[count]
                        }
                        if (sourceBase != 10 && fraction != "0") {
                            fraction = fractionConversionToDecimal(fraction.uppercase())
                            fraction = fractionConvertion(fraction.toBigDecimal())
                        } else if (sourceBase == 10 && fraction != "0") {
                            fraction = fractionConvertion(("0.$fraction").toBigDecimal())
                        } else {
                            fraction = "00000"
                        }
                        if (sourceBase != 10 && baseInt != "0") {
                            baseInt = conversionToDecimal(baseInt.uppercase().reversed())
                            baseInt = numberConversionDecimal(baseInt)
                        } else if (sourceBase == 10 && baseInt != "0") {
                            baseInt = numberConversionDecimal(baseInt)
                        } else {
                            baseInt = "0"
                        }
                        result = "$baseInt.$fraction"

                    } else {
                        if (sourceBase != 10) conversionNumber = conversionToDecimal(conversionNumber.uppercase().reversed())
                        result = numberConversionDecimal(conversionNumber)
                    }
                    println("Conversion result: $result")
                }
            } while (conversionNumber != "/back")
        }
    } while (decision[0] != "/exit")
}

fun numberConversionDecimal(input: String): String = when {
    targetBase == 2 -> toBinary(input.toBigInteger())
    targetBase > 2 -> targetBaseConvertion(input.toBigInteger())
    else -> ""
}

fun toBinary(inputNumber: BigInteger): String {
    var input: BigInteger = inputNumber
    var res = mutableListOf<BigInteger>()
    while (input > BigInteger.ZERO) {
        if (input == BigInteger.ONE) {
            res.add(BigInteger.ONE)
            break
        } else {
            res.add(input % BigInteger.TWO)
            input /= BigInteger.TWO
        }
    }
    res = res.reversed() as MutableList<BigInteger>
    return res.joinToString("")
}

fun conversionToDecimal(input: String): String {
    var temp: BigDecimal = BigDecimal.ZERO
    for (i in input.indices) {
        if (input[i] in 'A'..'Z') {
            temp += (reverseHexLetters(input[i]).toDouble() * sourceBase.toDouble().pow(i.toDouble())).toBigDecimal()
        } else {
            temp += (input[i].toString().toDouble() * sourceBase.toDouble().pow(i.toDouble())).toBigDecimal()
        }
    }
    return temp.toBigInteger().toString()
}

fun targetBaseConvertion(inputNumber: BigInteger): String {
    var input = inputNumber
    var res = mutableListOf<String>()
    while (input > BigInteger.ZERO) {
        if (input < targetBase.toBigInteger()) {
            if (input >= BigInteger.TEN) {
                res.add(hexLetter(input.toString()))
            } else {
                res.add(input.toString())
            }
            break
        } else {
            if (input % targetBase.toBigInteger() >= BigInteger.TEN) {
                res.add(hexLetter((input % targetBase.toBigInteger()).toString()))
            } else {
                res.add((input % targetBase.toBigInteger()).toString())
            }
            input /= targetBase.toBigInteger()
        }
    }
    res = res.reversed() as MutableList<String>
    return res.joinToString("")
}

fun fractionConvertion(inputNumber: BigDecimal): String {
    var input = inputNumber
    var tempOne: BigDecimal
    var fractionInt: BigDecimal = BigDecimal.ZERO
    var count = 0
    var res = mutableListOf<String>()
    while (count < 5) {
        var temp = input.setScale(5, RoundingMode.CEILING) * targetBase.toBigDecimal()
        tempOne = temp
        if (temp >= BigDecimal.ONE) {
            fractionInt = temp % BigDecimal.ONE
            temp -= fractionInt
            println(temp)
        } else {
            temp = BigDecimal.ZERO
        }
        if (temp >= BigDecimal.TEN) {
            res.add(hexLetter(temp.toBigInteger().toString()))
        } else {
            res.add(temp.toBigInteger().toString())
            fractionInt = tempOne % BigDecimal.ONE
        }
        input = fractionInt
        count++
    }
    println(res.joinToString(""))
    return res.joinToString("")
}

fun fractionConversionToDecimal(input: String): String {
    var temp: BigDecimal = BigDecimal.ZERO
    var tempOne: MutableList<String> = mutableListOf()
    for (i in input.indices) {
        if (input[i] in 'A'..'Z') {
            temp = ((reverseHexLetters(input[i]).toDouble() * sourceBase.toDouble().pow(-(i.toDouble() + 1)))).toBigDecimal()
        } else {
            temp = ((input[i].toString().toDouble() * sourceBase.toDouble().pow(-(i.toDouble() + 1)))).toBigDecimal()
        }
        tempOne.add("${temp.setScale(5, RoundingMode.HALF_DOWN)}")
    }
    temp = BigDecimal.ZERO
    for (i in tempOne.indices) {
        temp += tempOne[i].toBigDecimal()
    }
    return temp.toString()
}

fun reverseHexLetters(input: Char): String {
    var letter: String = ""
    when (input) {
        'A' -> {
            letter = "10"
        }
        'B' -> {
            letter = "11"
        }
        'C' -> {
            letter = "12"
        }
        'D' -> {
            letter = "13"
        }
        'E' -> {
            letter = "14"
        }
        'F' -> {
            letter = "15"
        }
        'G' -> {
            letter = "16"
        }
        'H' -> {
            letter = "17"
        }
        'I' -> {
            letter = "18"
        }
        'J' -> {
            letter = "19"
        }
        'K' -> {
            letter = "20"
        }
        'L' -> {
            letter = "21"
        }
        'M' -> {
            letter = "22"
        }
        'N' -> {
            letter = "23"
        }
        'O' -> {
            letter = "24"
        }
        'P' -> {
            letter = "25"
        }
        'Q' -> {
            letter = "26"
        }
        'R' -> {
            letter = "27"
        }
        'S' -> {
            letter = "28"
        }
        'T' -> {
            letter = "29"
        }
        'U' -> {
            letter = "30"
        }
        'V' -> {
            letter = "31"
        }
        'W' -> {
            letter = "32"
        }
        'X' -> {
            letter = "33"
        }
        'Y' -> {
            letter = "34"
        }
        'Z' -> {
            letter = "35"
        }
    }
    return letter
}

fun hexLetter(input: String): String {
    var letter: String = ""
    when (input) {
        "10" -> {
            letter = "A"
        }
        "11" -> {
            letter = "B"
        }
        "12" -> {
            letter = "C"
        }
        "13" -> {
            letter = "D"
        }
        "14" -> {
            letter = "E"
        }
        "15" -> {
            letter = "F"
        }
        "16" -> {
            letter = "G"
        }
        "17" -> {
            letter = "H"
        }
        "18" -> {
            letter = "I"
        }
        "19" -> {
            letter = "J"
        }
        "20" -> {
            letter = "K"
        }
        "21" -> {
            letter = "L"
        }
        "22" -> {
            letter = "M"
        }
        "23" -> {
            letter = "N"
        }
        "24" -> {
            letter = "O"
        }
        "25" -> {
            letter = "P"
        }
        "26" -> {
            letter = "Q"
        }
        "27" -> {
            letter = "R"
        }
        "28" -> {
            letter = "S"
        }
        "29" -> {
            letter = "T"
        }
        "30" -> {
            letter = "U"
        }
        "31" -> {
            letter = "V"
        }
        "32" -> {
            letter = "W"
        }
        "33" -> {
            letter = "X"
        }
        "34" -> {
            letter = "Y"
        }
        "35" -> {
            letter = "Z"
        }
    }
    return letter
}