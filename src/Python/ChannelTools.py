import os
import subprocess
from pydub import AudioSegment 

def getNumberOfChannels(trackPath):
    trackSegment = AudioSegment.from_file(trackPath, duration=2)
    return trackSegment.channels

def extractCenter(audioTrackPath):
    rootDirectory = audioTrackPath[:audioTrackPath.rindex('\\')]
    print(rootDirectory)
    fileName = audioTrackPath[audioTrackPath.rindex('\\')+1:audioTrackPath.rindex(".")]
    #print(rootDirectory+'\\'+fileName+'front_center'+'.flac')
    os.chdir('C:/Users/james')
    subprocess.call(['ffmpeg', '-i',audioTrackPath,'-filter_complex','channelsplit=channel_layout='+
        str(getNumberOfChannels(audioTrackPath)-1)+'.1:channels=FC[FC]', '-map', '[FC]',
        rootDirectory+'\\'+fileName+'front_center'+'.flac'])
    
    return AudioSegment.from_file(rootDirectory+'\\'+fileName+'front_center'+'.flac')

def trimAudio(audioTrackPath,start,stop):
    segment = AudioSegment.from_file(audioTrackPath,start_second=start,duration=stop-start)
    segment.export(audioTrackPath[:audioTrackPath.rindex(".")]+'trimmed'+".flac", format="flac")
    return audioTrackPath[:audioTrackPath.rindex(".")]+'trimmed'+".flac"


#ffmpeg -i "Deepwater Horizon (2016)_track2_[eng]_DELAY 0ms.thd" 
# -filter_complex "channelsplit=channel_layout=7.1:channels=FC[FC]" -map "[FC]" front_center.flac