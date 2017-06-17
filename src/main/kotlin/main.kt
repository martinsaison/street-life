import music.*

fun violin(): Chunk {
    val chord = Chunk()
            .or("Bb5", "v-- ")
            .or("F_5", "v-- ")
            .or("D_5", "v-- ")
            .or("Bb4", "v-- ")
            .or("F_4", "v-- ")
            .or("C_3", "v-- ")
            .or("C_2", "v-- ")

    return Silence(8) then
            chord then
            chord + 1 then
            chord + 3 then
            chord + 4 then Chunk()
            // first measure
            .or("Ab5", "v--------       ")
            .or("F 5", "v--------       ")
            .or("C 4", "v--------       ")
}

fun trumpet(): Chunk {
    val note = Chunk()
            .or("Bb5", "x   ")
            .or("F 5", "x   ")
            .or("D 5", "x   ")

    return (Silence(8) then note then note + 1 then note + 3 then note + 4) then Chunk()
            .or("Ab5", "v")
            .or("C 6", "v")
            .or("F 6", "v")
}

fun keyboard(): Chunk {
    return Silence(8 + 16) then Chunk()
            .or("Eb5", "  x xx x")
            .or("C 5", "  x xx x")
            .or("Ab4", "  x xx x")
            .or("G 4", "  x xx x") then
            Silence(8) then Chunk()
            .or("F 5", "  x             ")
            .or("Eb5", "     x          ")
            .or("Db5", "  x     x       ")
            .or("C 5", "     x     x    ")
            .or("Bb4", "              x ")
            .or("Ab4", "  x     x  x    ")
            .or("G 4", "     x        x ")
            .or("F 4", "        x       ")
            .or("Eb4", "           x    ")
            .or("Db4", "              x ") then Chunk()

            .or("F 5", "                ")
            .or("Eb5", "                ")
            .or("Db5", "                ")
            .or("C 5", "              x ")
            .or("Bb4", "                ")
            .or("Ab4", "  x        x  x ")
            .or("G 4", "     x          ")
            .or("F 4", "        x       ")
            .or("Eb4", "  x  x     x  x ")
            .or("Db4", "                ")
            .or("C 4", "  x     x  x    ")
            .or("Bb3", "     x          ")
            .or("Ab3", "        x       ") then Chunk()

            .or("F 5", "                ")
            .or("Eb5", "                ")
            .or("Db5", "        x       ")
            .or("C 5", "     x          ")
            .or("Bb4", "  x             ")
            .or("Ab4", "     x  x       ")
            .or("G 4", "                ")
            .or("F 4", "  x  x  x       ")
            .or("Eb4", "                ")
            .or("Db4", "  x             ")
            .or("C 4", "                ")
            .or("Bb3", "                ")
            .or("Ab3", "                ")

}

fun bass(): Chunk {
    val line = Chunk()
            .or("Db4", "                ")
            .or("Db4", "                ")
            .or("Db4", "                ")
            .or("Db4", "                ")
            .or("Db4", "                ")
            .or("F 2", "        x       ")
            .or("E 2", "      x         ")
            .or("Eb2", "     .          ")
            .or("F 1", "x               ")
    val bass = line
            .or("F 1", "              x ") then
            (line + 5)
                    .or("C 2", "              x ") then line
            .or("F 1", "               x") then
            (line + 5)
                    .or("Bb2", "              x ")
                    .or("Ab2", "           x    ")
    return Silence(8 + 16) then bass
}

fun guitar(): Chunk {
    val intro = Silence(8 + 16)
    val muted = Chunk()
            .or("C 5", "    .           ")
            .or("Ab4", " x.. .  .   xx  ")
            .or("G 4", "x   . x       ..")
            .or("F 4", "        .     ..")
            .or("Eb4", "x... .x .   xx  ")
            .or("C 4", " ... .  .   xx  ")
            .or("Bb3", "x     x         ") then (Chunk()

            .or("Bb4", " ... .  .   xx  ")
            .or("F 4", " x.. .  .   xx  ")
            .or("Eb4", "x     x       ..")
            .or("C 4", "        .     ..")
            .or("Ab3", "x... .x .   xx  ")
            .or("Db3", " ... .  .   xx  ")
            .or("C 3", "x     .         "))

    return intro then muted * 2
}

fun guitar2(): Chunk {
    val intro = Silence(8 + 16)
    val part1 = Chunk()
            .or("Ab4", "                ")
            .or("G 4", "                ")
            .or("F 4", "   x    x       ")
            .or("Eb4", "x     x         ")
            .or("C 4", "                ")
            .or("Bb3", "                ")

    val part2 = Chunk()
            .or("Ab4", "                ")
            .or("G 4", "   x            ")
            .or("F 4", "                ")
            .or("Eb4", "                ")
            .or("C 4", "   x    x       ")
            .or("Bb3", "x     x         ")

    return intro then part1 * 2 then part2 then part1
}


fun drum(): Chunk {
    val drum = Chunk()
            .map("BASS", Drum.BASS_DRUM_1)
            .map("ABAS", Drum.ACOUSTIC_BASS_DRUM)
            .map("ESNA", Drum.ELECTRIC_SNARE)
            .map("STIK", Drum.SIDE_STICK)
            .map("ASNA", Drum.ACOUSTIC_SNARE)
            .map("CRAS", Drum.CRASH_CYMBAL_1)
            .map("CRA2", Drum.CRASH_CYMBAL_2)
            .map("OHHA", Drum.OPEN_HI_HAT)
            .map("CHHA", Drum.CLOSED_HI_HAT)

    return drum
            .or("BASS", "   x  x x   x   x       ")
            .or("STIK", "   '                    ")
            .or("ESNA", "    x               x   ")
            .or("ABAS", "        x   x   x       ")
            .or("CRAS", "        x   x   x       ")
            .or("ASNA", "        x   x   x  x    ")
            .or("CRA2", "                      x ") then

            (drum
                    .or("BASS", "o    oo o       ")
                    .or("OHHA", "  o             ")
                    .or("CHHA", "o o o o o o o o ")
                    .or("ESNA", "    o       o   ")
                    .or("ASNA", "    o       o   ")
                    ) * 4


}

fun main(args: Array<String>) {
    val song = Song(108F)
    song.add(Track(violin(), 1, Instrument.STRING_ENSEMBLE_1, 60))
    song.add(Track(trumpet(), 2, Instrument.TRUMPET, 60))
    song.add(Track(keyboard(), 3, Instrument.ELECTRIC_PIANO, 60))
    song.add(Track(bass(), 4, Instrument.FINGERED_BASS, 110))
    song.add(Track(guitar(), 5, Instrument.OGUITAR, 60))
    song.add(Track(guitar2(), 6, Instrument.OGUITAR, 60))
    song.add(Track(drum(), 9, Instrument.DRUM, 110))
    song.play()
}

