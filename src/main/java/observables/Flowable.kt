package observables

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers

class flowable : Runnable {

    override fun run() {
        while (true) {
            getRandomPeople(10)
                    .flatMap { list -> Observable.fromIterable(list) }
                    .map { p ->  if (p.lastName.equals("Jakwert", true))
                            { p.firstName.toUpperCase() + " --- " + p.lastName.toUpperCase() }
                            else { p } }
                    .subscribeOn(Schedulers.io())
                    .subscribe(System.out::println)

            Thread.sleep(1000)
        }
    }

    fun getRandomPeople(n:Int) : Observable<List<Person>> {
        return object : Observable<List<Person>>() {
            override fun subscribeActual(observer: Observer<in List<Person>>?) {
                val ret = ArrayList<Person>(n)
                for (i in 0..n-1) {
                    ret += makeRandomPerson()
                }
                observer?.onNext(ret)
            }
        }
    }



}