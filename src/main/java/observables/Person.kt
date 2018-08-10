package observables

import io.reactivex.*
import javafx.application.Application.launch
import org.reactivestreams.Publisher


data class Person(val firstName: String, val lastName: String, val age: Int = 0) {
    override fun toString(): String = "$firstName $lastName, $age"
}

val fnames = arrayOf("Albert", "Niels", "Prem", "John", "Keith", "Rob", "Rusty", "Elvis", "Tadeusz", "Richi")
val lnames = arrayOf("Einstein", "Bohr", "Jakwert", "Zorn", "Jarret", "Zombie", "Newman", "Presley", "Kowalski", "Monday")

fun makeRandomPerson() : Person =
        Person(fnames[(Math.random() * fnames.size).toInt()], lnames[(Math.random() * lnames.size).toInt()], (Math.random()*120).toInt())



fun getRandomPerson() : Single<Person> = Single.just(makeRandomPerson())

fun getRandomPeople() : Flowable<Person> =
        Flowable.create(
                {
                  emitter ->
                    while(!emitter.isCancelled) {
                        val p = makeRandomPerson()
                        //if (p.age>18)
                            emitter.onNext(p)
                        //else
                        //    if (!emitter.isCancelled) emitter.onError(Throwable("$p is under 18"))
                } },

                BackpressureStrategy.BUFFER )

fun getPeopleFromTheList() : Observable<Person> =
        Observable.fromIterable( generateSequence { makeRandomPerson() }.asIterable() )

fun getPersonWithLastName(lname: String) : Maybe<Person> =
        Maybe.create {
            e->
                val p = makeRandomPerson()
                if (p.lastName.equals(lname, true)) e.onSuccess(p)
                else e.onError(Throwable("no luck"))
        }

fun getAllPeopleWithLastname(lname: String) : Observable<List<Person>> =
    Observable.just( fnames.map { f->Person(f,lname) } )




fun playWithPeople() {
    //getRandomPerson().subscribe { p -> println(p) }

    getRandomPeople()
            //.onErrorReturn { Person("error", "error") }
            //.onErrorReturnItem( Person("empty", "empty") )
            .filter { p->p.age>=18 }
            .map { p -> if (p.lastName.equals("jakwert",true)) p.copy(firstName = "*PREM*") else p }
            .take(10)
            .doOnSubscribe { println("--- Hello ---") }
    //        .subscribe( { p->println(p) }, { err->println("ERROR ${err.localizedMessage}") }, { println("--") } )

    getPeopleFromTheList()
            .filter{ p->p.firstName.equals("Prem") }
    //          .subscribe { p->println(p) }


    getPersonWithLastName("Zorn")
            .onErrorComplete { true }
    //        .subscribe( {p->println(p)}, {t->println(t.message)} )

    Observable.error<Person>(Throwable("that's an error")).subscribe ( {}, { t->println(t.message) }, {} )
    Observable.empty<Person>().subscribe ( {}, {p->println(p)}, {println("empty.onComplete")} )
    Observable.never<Person>().subscribe ( {}, {p->println(p)}, {println("empty.onComplete")} )

    //Observable.fromCallable { println("waiting for 3000ms.."); Thread.sleep(3000); "done" }.subscribe{v->println(v)}
    Observable.defer { Observable.just("bonanza!") }.subscribe{v->println(v)}

    Observable.merge(
            getAllPeopleWithLastname("Jakwert"),
            getAllPeopleWithLastname("Zorn"))
                .flatMap { list-> Observable.fromIterable(list) }
                .subscribe { p->println(p) }
}


fun playWithCoroutines() {
    //val job = launch {}
}


fun main(args: Array<String>) {

    playWithPeople()

    //playWithCoroutines()

}
