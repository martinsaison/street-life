package streetlife.music

import java.util.regex.Pattern

open class Chunk(
    private val lines: List<ClickSequence> = emptyList(),
    private val mappings: Map<String, Int> = emptyMap()
) {

    operator fun plus(semitones: Int): Chunk {
        return Chunk(
            lines.map { ClickSequence(if (it.pitch < 0) it.pitch else it.pitch + semitones, it.sequence) },
            mappings
        )
    }

    operator fun times(value: Int): Chunk {
        var chunk = Chunk(lines, mappings)
        (1..<value).forEach { _ -> chunk = chunk then Chunk(lines, mappings) }
        return chunk
    }

    open fun or(clickSequence: ClickSequence): Chunk {
        return this or Chunk(listOf(clickSequence), mappings)
    }

    open fun or(pitch: String, sequence: String): Chunk {
        return or(ClickSequence(strToPitch(pitch), sequence))
    }

    fun display() {
        lines.forEach {
            println(pitchToStr(it.pitch).padEnd(10, ' ') + ':' + it.sequence)
        }
    }

    private infix fun or(second: Chunk): Chunk {
        val size = maxOf(
            lines.maxOfOrNull { it.sequence.length } ?: 0,
            second.lines.maxOfOrNull { it.sequence.length } ?: 0
        )
        val pitches = lines.map { it.pitch }.toSet().plus(
            second.lines.map { it.pitch }
        )
        val newLines: List<ClickSequence> = pitches.map { p ->
            val firstSequence =
                (lines.filter { it.pitch == p }.map { it.sequence }.firstOrNull() ?: "").padEnd(size, ' ')
            val secondSequence =
                (second.lines.filter { it.pitch == p }.map { it.sequence }.firstOrNull() ?: "").padEnd(size, ' ')
            ClickSequence(p, combineSequences(firstSequence, secondSequence))
        }.sortedByDescending { it.pitch }
        return Chunk(newLines, mappings)
    }

    private fun combineSequences(firstSequence: String, secondSequence: String): String {
        return firstSequence.mapIndexed { index, f ->
            val s = secondSequence[index]
            if ((f != '-' && f != ' ') || s == ' ') f
            else s
        }.joinToString("")
    }

    infix fun then(second: Chunk): Chunk {
        val firstSize = lines.maxOfOrNull { it.sequence.length } ?: 0
        val secondSize = second.lines.maxOfOrNull { it.sequence.length } ?: 0
        val pitches = lines.map { it.pitch }.toSet().plus(
            second.lines.map { it.pitch }
        )
        val newLines: List<ClickSequence> = pitches.map { p ->
            val firstSequence =
                (lines.filter { it.pitch == p }.map { it.sequence }.firstOrNull() ?: "").padEnd(firstSize, ' ')
            val secondSequence =
                (second.lines.filter { it.pitch == p }.map { it.sequence }.firstOrNull() ?: "").padEnd(secondSize, ' ')
            ClickSequence(p, firstSequence + secondSequence)
        }.sortedByDescending { it.pitch }
        return Chunk(newLines, mappings + second.mappings)
    }

    fun getNotes(): List<Note> {
        return lines.filter { it.pitch >= 0 }.map {
            val pitch = it.pitch
            val p = Pattern.compile("""([a-zA-Z.']-*| +)""")
            val m = p.matcher(it.sequence)
            val notes = mutableListOf<Note>()
            while (m.find()) {
                if (!m.group().startsWith(' '))
                    notes.add(Note(m.start() * 1.0, (m.end() - m.start()) * 1.0, pitch, m.group()))
            }
            notes
        }.flatten()
    }

    private fun map(key: String, pitch: Int): Chunk {
        val newMappings = mappings + Pair(key, pitch)
        return Chunk(lines, newMappings)
    }

    fun map(key: String, instrument: Drum): Chunk {
        return map(key, instrument.pitch)
    }

    private fun strToPitch(pitch: String): Int {
        if (mappings.contains(pitch))
            return mappings[pitch] ?: 0
        val p = pitch.trim()
        if (p.length < 3) return -20
        val note = when (p[0].lowercase()) {
            "c" -> 0
            "d" -> 2
            "e" -> 4
            "f" -> 5
            "g" -> 7
            "a" -> 9
            "b" -> 11
            else -> 0
        }
        val alteration = when (p[1].lowercase()) {
            "b", "f" -> -1
            "#", "s" -> 1
            else -> 0
        }
        val octave = p.substring(2).toInt()
        return note + alteration + (octave + 1) * 12
    }

    private fun pitchToStr(pitch: Int): String {
        if (pitch < 12) return "   "
        val octave = pitch / 12 - 1
        val semitone = pitch % 12
        val note = arrayOf("C ", "C#", "D ", "D#", "E ", "F ", "F#", "G ", "G#", "A ", "A#", "B ")[semitone]
        return "$note$octave"
    }

}