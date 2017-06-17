package music

class Note(val start: Double, val length: Double, val pitch: Int, val sound: String) {
    fun timeStart(): Double {
        return timeStartLength().first
    }

    fun timeEnd(): Double {
        val se = timeStartLength()
        return se.first + se.second
    }

    fun timeStartLength(): Pair<Double, Double> {
        return when (sound[0]) {
            '.' -> Pair(start, length / 8)
            '\'' -> Pair(start + length * 7 / 8, length / 8)
            else -> Pair(start, length)
        }
    }
}