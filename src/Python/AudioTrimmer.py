from pydub import AudioSegment 
import ChannelTools

#audio = ChannelTools.extractCenter("E:\Cache\gMKVExtract\Pineapple Express.thd")
audio = AudioSegment.from_file("E:\Cache\gMKVExtract\Pineapple Expressfront_center.flac")
print(audio.frame_rate)