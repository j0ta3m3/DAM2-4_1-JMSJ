//Main
fun main() {
// 1.1 Creamos un modulo de 15 alumnos
    var modulo1 = Modulo(15)
    // 1.2 matriculamos 10 alumnos :

    var alumno1 = Alumno("01", "pepe", "gomez", "gomez")
    var alumno2 = Alumno("46", "pedro", "aa", "bb")
    var alumno3 = Alumno("02", "Ana", "fernandez", "ruiz")
    var alumno4 = Alumno("03", "Rafa", "sanchez", "ruiz")
    var alumno5 = Alumno("04", "Miguel", "sanchez", "ruiz")
    var alumno6 = Alumno("05", "Manu", "sanchez", "ruiz")
    var alumno7 = Alumno("06", "Albert", "sanchez", "ruiz")
    var alumno8 = Alumno("07", "Fer", "sanchez", "ruiz")
    var alumno9 = Alumno("08", "Israel", "sanchez", "ruiz")
    var alumno10 = Alumno("09", "Javi", "sanchez", "ruiz")

    modulo1.MatricularAlumno(alumno1)
    modulo1.MatricularAlumno(alumno2)
    modulo1.MatricularAlumno(alumno3)
    modulo1.MatricularAlumno(alumno4)
    modulo1.MatricularAlumno(alumno5)
    modulo1.MatricularAlumno(alumno6)
    modulo1.MatricularAlumno(alumno7)
    modulo1.MatricularAlumno(alumno8)
    modulo1.MatricularAlumno(alumno9)
    modulo1.MatricularAlumno(alumno10)


//Probando metodos
    modulo1.establecerNota("46", "0", 1.0f)
    modulo1.establecerNota("46", "1", 6.0f)
    modulo1.establecerNota("46", "2", 7.0f)
    modulo1.establecerNota("01", "0", 4.0f)
    modulo1.establecerNota("01", "1", 7.0f)
    modulo1.establecerNota("01", "2", 6.0f)

    modulo1.calcularEvaluacionFinal("46")

    //  println(modulo1.listaDeAlumnos.toList())


    println("${modulo1.notas[0].toList()}")
    println("${modulo1.notas[1].toList()}")
    println("${modulo1.notas[2].toList()}")
    println("${modulo1.notas[3].toList()}")



    println("${modulo1.notaMasAlta("0")}")
    println("${modulo1.numeroAprobados("1")}")

    println("${modulo1.notaMedia("0")}")

    println("${modulo1.hayAlumnosConDiez("0")}")

    println(modulo1.primeraNotaNoAprobada("0"))

    //  modulo1.bajaAlumno("01")
    //  println("${modulo1.listaDeAlumnos.toList()}")

    // println("${modulo1.listaNotas("0").toList()}")

    println("${modulo1.listaNotasOrdenados("0")}")
}


//Clases :
class Modulo(var numAlumnos: Int) {

    var listaDeAlumnos = arrayOfNulls<Alumno>(numAlumnos)
    var notas = Array(4) { FloatArray(numAlumnos) { -1.0f } }
    var numAlumnosMatriculados: Int = 0

    companion object {
        const val EV_PRIMER = "0"
        const val EV_SEGUN = "1"
        const val EV_TERCE = "2"
        const val EV_FINAL = "3"
    }

    override fun toString(): String {
        return "$"
    }

    // 12 Poder matricular alumnos siempre que no se llegue al máximo de alumnos.
    fun MatricularAlumno(alumno: Alumno): Boolean {
        if (numAlumnosMatriculados <= numAlumnos) {
            var posicion = listaDeAlumnos.indexOfFirst { it == null }
            listaDeAlumnos[posicion] = alumno
        }
        numAlumnosMatriculados++
        return true

    }

    // 13 Dar de baja un alumno
    fun bajaAlumno(idAlumno: String): Boolean {

        listaDeAlumnos[idAlumno.toInt()] = null
        return true
    }


    fun establecerNota(idAlumno: String, evaluacion: String, nota: Float): Boolean {
        if (evaluacion in Modulo.EV_PRIMER..Modulo.EV_FINAL) {
            var posicion = listaDeAlumnos.indexOfFirst { it?.ID == idAlumno }
            if (posicion != -1) {
                notas[evaluacion.toInt()][posicion] = nota
            }

            return true
        }
        return true
    }

    // 2 Calcula la nota final de los módulos como la media de las 3 Evaluaciones.

    fun calcularEvaluacionFinal(idAlumno: String) {
        var notasDe1Alumno = listaDeAlumnos.indexOfFirst { it?.ID == idAlumno }
        var media = 0.0f
        var EV1 = notas[0][notasDe1Alumno]
        var EV2 = notas[1][notasDe1Alumno]
        var EV3 = notas[2][notasDe1Alumno]

        media = (EV1 + EV2 + EV3) / 3

        notas[3][notasDe1Alumno] = media


    }

    // 3 Obtener lista de alumnos, nota con las notas por evaluación, por defecto final.
    fun listaNotas(evaluacion: String): Pair<List<Alumno?>, List<Float>> {

        var evaluacion = evaluacion.toInt()

        var pair = Pair(listaDeAlumnos.filter { it != null }, notas[evaluacion].filter { it != -1.0f })
        return pair
    }

    // 11 Obtener una lista con los alumnos ordenados ascendiente según su nota por evaluación, por defecto final.
    // (Creo que no está bien del todo , no sabía como ordenar la lista de alumnos al ser de String solo he ordenado la lista de Float)

    fun listaNotasOrdenados(evaluacion: String): Pair<List<Alumno?>, List<Float>> {

        var alumnos = listaDeAlumnos.filter { it != null }
        var notas = notas[evaluacion.toInt()].filter { it != -1.0f }.sorted()

        var lista = Pair(alumnos, notas)
        return lista
    }

    // 4 Para un módulo, calcular cuantos alumnos han aprobado por evaluación, por defecto final.
    fun numeroAprobados(evaluacion: String): Int {

        var evaluacion = notas[evaluacion.toInt()]
        var aprobados = evaluacion.count { it >= 5.0f }
        return aprobados

    }

    // 5 Para un módulo, calcular la nota más baja por evaluación, por defecto final.
    fun notaMasBaja(evaluacion: String): Float? {

        var notabaja = notas[evaluacion.toInt()].filter { it > 0f }.minByOrNull { it }

        return notabaja
    }

    // 6  Para un módulo, calcular la nota más alta por evaluación, por defecto final.
    fun notaMasAlta(evaluacion: String): Float? {
        var notaalta = notas[evaluacion.toInt()].maxByOrNull { it }

        return notaalta
    }

    //7 Para un módulo, calcular la nota media entre todos los alumnos por evaluación, por defecto final.
    fun notaMedia(evaluacion: String): Float {
        var media = notas[evaluacion.toInt()].filter { it > -1.0f }.average().toFloat()
        return media
    }

    // 8 Para un módulo, calcular si alguno tiene un 10 por evaluación, por defecto final.
    fun hayAlumnosConDiez(evaluacion: String): Boolean {
        var evaluacion = notas[evaluacion.toInt()]
        if (evaluacion.contains(10f)) {
            return true
        } else
            return false
    }

    // 9 Para un módulo, calcular si hay alumnos aprobados por evaluación, por defecto final.
    fun hayAlumnosAprobados(evaluacion: String): Boolean {
        var aluapro = notas[evaluacion.toInt()].find { it >= 5.0F }
        if (aluapro == null) {
            return false
        }
        return true
    }

    // 10 Para un módulo, calcular la primera nota que no ha superado el 5 por evaluación, por defecto final.
    fun primeraNotaNoAprobada(evaluacion: String): Float? {

        var evaluacion = notas[evaluacion.toInt()]
        var nota = evaluacion.find { it <= 5.0f }
        return nota

    }

}

data class Alumno(var ID: String, var Nombre: String, var Apellido1: String, var Apellido2: String) {


}




