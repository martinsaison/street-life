Street-Life
===========


So I was listening to [this song](https://youtu.be/cnNyxy7XPfs) and
wanted to play a bit of it on my keyboard (the computer's one, not the piano). 
Many Java music libraries are available (jMusic, jFugue). First I tried with jMusic but
it seems to be a bit unstable. I tried to figure out an easy way to write the 
parts of the song and decided to write a POC in Kotlin.

The [main.kt](./src/main/kotlin/main.kt) contains the 5 first bars of the song. It's far from perfect (and
relies on the MIDI sequencer) but you can easily understand the source and how
it generates the music.

The smallest unit is a Chunk class that contains many sequences of keys.
The magic of Kotlin allows some basic operations:

- `chunk + 12` will transpose the chunk to the upper octave
- `chunk * 4` will repeat the chunk 4 times 
- `chunk1 then chunk2` will play the chunks in a row
- `chunk1 or chunk2` will mix the chunks

