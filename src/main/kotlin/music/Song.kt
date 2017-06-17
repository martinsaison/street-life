package music

import javax.sound.midi.*

class Song(val tempo: Float) {
    val tracks = mutableListOf<Track>()
    fun add(track: Track) {
        tracks.add(track)
    }

    fun play() {
        val sequence = Sequence(Sequence.PPQ, 24)
        tracks.forEach {
            val track = it
            val midiTrack = sequence.createTrack()
            midiTrack.add(MidiEvent(ShortMessage(ShortMessage.PROGRAM_CHANGE + track.channel, track.instrument.pitch, 0), 0))
            val midiEvents = it.chunk.getNotes().map {
                listOf(
                        MidiEvent(ShortMessage(ShortMessage.NOTE_ON, track.channel, it.pitch, track.volume), (it.timeStart() * 24 / 4 + 1).toLong()),
                        MidiEvent(ShortMessage(ShortMessage.NOTE_OFF, track.channel, it.pitch, 0), (it.timeEnd() * 24 / 4 + 1).toLong())
                )
            }.flatMap { it }.sortedBy { it.tick }
            midiEvents.forEach {
                midiTrack.add(it)
            }
        }

        sequence.tracks.map { it.ticks() }
        val sequencer = MidiSystem.getSequencer()
        sequencer.open()
        sequencer.sequence = sequence

        class EndListener : MetaEventListener {
            override fun meta(meta: MetaMessage) {
                if (meta.type == 0x2F) {
                    sequencer.stop();
                    sequencer.close();
                }
            }
        }
        sequencer.addMetaEventListener(EndListener())
        sequencer.tempoInBPM = tempo
        sequencer.start()
    }
}