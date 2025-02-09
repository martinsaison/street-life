package streetlife.music

import javax.sound.midi.*

class Song(private val tempo: Float) {
    private val tracks = mutableListOf<Track>()
    fun add(track: Track) {
        tracks.add(track)
    }

    fun play() {
        val sequence = Sequence(Sequence.PPQ, 24)
        tracks.forEach { track ->
            val midiTrack = sequence.createTrack()
            midiTrack.add(
                MidiEvent(
                    ShortMessage(
                        ShortMessage.PROGRAM_CHANGE + track.channel, track.instrument.pitch, 0
                    ), 0
                )
            )
            val midiEvents = track.chunk.getNotes().map { note ->
                listOf(
                    MidiEvent(
                        ShortMessage(ShortMessage.NOTE_ON, track.channel, note.pitch, track.volume),
                        (note.timeStart() * 24 / 4 + 1).toLong()
                    ), MidiEvent(
                        ShortMessage(ShortMessage.NOTE_OFF, track.channel, note.pitch, 0),
                        (note.timeEnd() * 24 / 4 + 1).toLong()
                    )
                )
            }.flatten().sortedBy { it.tick }
            midiEvents.forEach {
                midiTrack.add(it)
            }
        }

        sequence.tracks.map { it.ticks() }
        val sequencer = MidiSystem.getSequencer() // How to choose a specific device?
        sequencer.open()
        sequencer.sequence = sequence

        class EndListener : MetaEventListener {
            override fun meta(meta: MetaMessage) {
                if (meta.type == 0x2F) {
                    sequencer.stop()
                    sequencer.close()
                }
            }
        }
        sequencer.addMetaEventListener(EndListener())
        sequencer.tempoInBPM = tempo
        sequencer.start()
    }

    fun display() {
        tracks.forEach { track ->
            println("Track ${track.channel} (${track.instrument.name})")
            track.chunk.display()
        }
    }
}