package streetlife.music

class Silence(count: Int) : Chunk(listOf(ClickSequence(1, "".padEnd(count, ' '))))