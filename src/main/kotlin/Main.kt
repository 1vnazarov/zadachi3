fun main() {
    print(
        """
        1: камень-ножницы-бумага.
        Выберите программу для проверки: """.trimIndent()
    )
    when (readln()) {
        "1" -> zadacha1()
    }
}

fun zadacha1() {
    val ai = (0..2).random()
    val rules = listOf("Камень", "Ножницы", "Бумага")
    for (i in rules.indices) println("${i + 1} - ${rules[i]}")
    print("Выберите сторону: ")
    val player = readln().toInt() - 1
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