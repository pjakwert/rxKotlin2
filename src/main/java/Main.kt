import io.reactivex.*


fun main(args: Array<String>) {

    //Flowable.just("Hi").subscribe { s-> println(s) }

    //Flowable.fromArray("one", "two", "three", "four", "five").subscribe { s -> println(s) }

    val n=321364533
    println("$n is prime: ${isPrime(n)}")


    getNextNumber()
            //.timeInterval()
            //.buffer(10)
            //.subscribeOn(Schedulers.computation())
            //.observeOn(Schedulers.single())
            .filter { n->isPrime(n) }
            .map { n -> "this is prime number: $n" }
            //.subscribe{ n -> println("$n") }

    println("first!")

    Observable.create( { e:ObservableEmitter<String> -> e.onNext("Yoohooo"); e.onComplete() } ).doOnNext { n->println(n) }

    //Thread.sleep(1000)


    var chs : CharSequence? = null
    println("IsNullOrBlank: ${chs.isNullOrBlank()}")

}

fun isPrime(num: Int) : Boolean {
    var i = 2
    while (i <= num / 2) {
        if (num % i == 0) return false
        ++i
    }
    return true
}

fun getNextNumber() : Flowable<Int> {
    return Flowable.create( {emitter -> var i =0; while(true) {emitter.onNext( i++ )} }, BackpressureStrategy.BUFFER )
}

fun getRandomNumber() : Flowable<Int> {
    return Flowable.create ( {emitter -> while(true) {emitter.onNext( (Math.random()*100).toInt())} }, BackpressureStrategy.BUFFER )
}

fun getRandomDividedByFive() : Maybe<Int> {
    return Maybe.create { emitter ->    }
}
