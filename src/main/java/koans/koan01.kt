

class Koan01 : Runnable {

    fun start(): String = "ok"

    //val startText : String by observables.Person("Albert", "Einstein")
    val endText : String by lazy { "this is the end" }


    override fun run() {
        println (endText)
    }


}
