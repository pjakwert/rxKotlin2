

class KoanStdlib : Runnable {

    override fun run() {
        // koan 0
        val list = (1..13)
        println(list.joinToString("->", "[", "]"))

        // koan
        println( list.any{ v-> v%2 == 0 } )

        // koan
        val month = "(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)"
        val pattern = """\d{2}\ ${month}\ \d{4}"""
        val date = "13 JAN 2018"
        println(date.matches(pattern.toRegex()))

        // koan
        fun eval(expr: Expr): Int =
                when (expr) {
                    is Num -> expr.value // automatic casting!!!
                    is Sum -> eval(expr.left) + eval(expr.right) // same here!!
                    else -> throw IllegalArgumentException("Unknown expression")
                }

        // koan
        fun compare(date1: MyDate, date2: MyDate) = date1 < date2
    }


    interface Expr
    class Num(val value: Int) : Expr
    class Sum(val left: Expr, val right: Expr) : Expr


    data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
        override fun compareTo(other:MyDate): Int = when {
            year != other.year -> year - other.year
            month != other.month -> month - other.month
            else -> dayOfMonth - other.dayOfMonth
        }
    }
}

