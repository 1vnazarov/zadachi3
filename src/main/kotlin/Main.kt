import kotlin.random.Random

fun main() {
    when (getInput("""
        1: Камень-ножницы-бумага
        2: Шифр Виженера
        3: Шифр Порты
        Выберите программу для проверки: """.trimIndent())) {
        "1" -> zadacha1()
        "2"-> zadacha2()
        "3" -> zadacha3()
    }
}

fun getInput(prompt: String): String {
    print(prompt)
    return readln()
}

fun zadacha1() {
    val ai = (0..2).random()
    val rules = listOf("Камень", "Ножницы", "Бумага")
    for (i in rules.indices) println("${i + 1} - ${rules[i]}")
    val player = getInput("Выберите сторону: ").toInt() - 1
    println("Компьютер выбрал ${rules[ai]}")
    if (ai == player) {
        println("Ничья! Сыграйте снова")
        return zadacha1()
    }
    var res = if ((player == 2 && ai == 0) || (player == 0 && ai == 1) || (player == 1 && ai == 2)) "Вы победили. "
    else "Компьютер победил. "
    if ((ai == 2 || player == 2) && (ai == 0 || player == 0)) res += "Бумага обёртывает камень"
    else if ((ai == 0 || player == 0) && (ai == 1 || player == 1)) res += "Камень затупляет или ломает ножницы"
    else if ((ai == 1 || player == 1) && (ai == 2 || player == 2)) res += "Ножницы разрезают бумагу"
    println(res)
}

fun zadacha2() {
    fun generateTypicalTable(alphabet: CharArray): Array<CharArray> {
        val table = Array(alphabet.size) { CharArray(alphabet.size) }
        for (i in alphabet.indices) {
            for (j in alphabet.indices) {
                val shiftedIndex = (i + j) % alphabet.size
                table[i][j] = alphabet[shiftedIndex]
            }
        }
        return table
    }

    fun generateRandomTable(alphabet: CharArray): Array<CharArray> {
        val table = Array(alphabet.size) { CharArray(alphabet.size) }
        val shuffledAlphabet = alphabet.toList().shuffled()

        for (i in alphabet.indices) {
            val shift = Random.nextInt(0, alphabet.size)
            for (j in alphabet.indices) {
                val shiftedIndex = (i + shift + j) % alphabet.size
                table[i][j] = shuffledAlphabet[shiftedIndex]
            }
        }
        return table
    }

    fun printTable(table: Array<CharArray>) {
        for (row in table) {
            for (col in row) {
                print("$col ")
            }
            println()
        }
    }

    fun buildRepeatedKey(message: String, key: String): String {
        var repeatedKey = ""
        var keyIndex = 0

        for (i in message.indices) {
            if (keyIndex == key.length) {
                keyIndex = 0
            }
            repeatedKey += key[keyIndex]
            keyIndex++
        }
        return repeatedKey
    }

    fun encrypt(message: String, key: String, table: Array<CharArray>): String {
        var encryptedMessage = ""

        for (i in message.indices) {
            encryptedMessage += table[table[0].indexOf(message[i])][table[0].indexOf(key[i])]
        }
        return encryptedMessage
    }

    fun decrypt(encryptedMessage: String, key: String, table: Array<CharArray>): String {
        var decryptedMessage = ""

        for (i in encryptedMessage.indices) {
            val keyIndex = table[0].indexOf(key[i])
            for (row in table) {
                if (row[keyIndex] == encryptedMessage[i]) {
                    decryptedMessage += table[0][table.indexOf(row)]
                    break
                }
            }
        }
        return decryptedMessage
    }

    val alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя".toCharArray()
    when (getInput("1 - зашифровать\n2 - расшифровать\nВыберите действие: ")) {
        "1" -> {
            val message = getInput("Введите исходное сообщение: ").lowercase()
            val key = buildRepeatedKey(message, getInput("Введите ключ: ").lowercase())
            val table = if (getInput("Использовать типовую таблицу? (y/n): ").lowercase() == "y") generateTypicalTable(alphabet) else generateRandomTable(alphabet)
            println("Исходное сообщение: $message")
            println("Ключ: $key")
            val encryptedMessage = encrypt(message, key, table)
            println("Зашифрованное сообщение: $encryptedMessage")
            println("Шифровальная таблица:")
            printTable(table)
            val decryptedMessage = decrypt(encryptedMessage, key, table)
            println("Расшифрованное сообщение: $decryptedMessage")
        }
        "2" -> {
            val cipher = getInput("Введите шифр: ").lowercase()
            val key = buildRepeatedKey(cipher, getInput("Введите ключ: ").lowercase())
            val table = if (getInput("Использовать типовую таблицу? (y/n): ").lowercase() == "y") generateTypicalTable(alphabet) else generateRandomTable(alphabet)
            println("Шифр: $cipher")
            println("Ключ: $key")
            println("Шифровальная таблица:")
            printTable(table)
            val decryptedMessage = decrypt(cipher, key, table)
            println("Расшифрованное сообщение: $decryptedMessage")
        }
    }
}

fun zadacha3() {
    val alphabet = "абвгдежзиклмнопрстуфхцчшщъыьэюя"
    fun generateCombinations(): List<Pair<Char, Char>> {
        val combinations = mutableListOf<Pair<Char, Char>>()
        for (i in 0 until alphabet.length) {
            for (j in 0 until alphabet.length) {
                combinations.add(alphabet[i] to alphabet[j])
            }
        }
        return combinations
    }

    fun generateTable(combinations: List<Pair<Char, Char>>): Map<String, String> {
        return combinations.mapIndexed { index, pair ->
            val key = "%03d".format(index + 1)
            val value = "${pair.first}${pair.second}"
            key to value
        }.toMap()
    }

    fun printTable(table: Map<String, String>) {
        var counter = 1
        for ((key, value) in table) {
            print("$value: $key ")
            if (counter % alphabet.length == 0) {
                println()
            }
            counter++
        }
    }

    val combinations = generateCombinations()

    val table = generateTable(combinations)

    printTable(table)
}